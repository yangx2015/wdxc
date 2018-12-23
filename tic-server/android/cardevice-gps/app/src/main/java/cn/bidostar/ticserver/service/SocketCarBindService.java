package cn.bidostar.ticserver.service;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.RemoteException;
import android.os.SystemClock;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.miramems.carmotion.carMotion;

import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.text.DecimalFormat;
import java.util.List;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

import cn.bidostar.ticserver.AppApplication;
import cn.bidostar.ticserver.dao.LocalFilesModelDao;
import cn.bidostar.ticserver.dao.RequestCommonParamsDtoDao;
import cn.bidostar.ticserver.model.LocalFilesModel;
import cn.bidostar.ticserver.model.RequestCommonParamsDto;
import cn.bidostar.ticserver.utils.AppConsts;
import cn.bidostar.ticserver.utils.AppSharedpreferencesUtils;
import cn.bidostar.ticserver.utils.CarIntents;
import cn.bidostar.ticserver.utils.I;
import cn.bidostar.ticserver.utils.NetworkUtil;
import cn.bidostar.ticserver.utils.ServerApiUtils;
import cn.wang.weicy.device.cardevice.StrongServiceAidlInterface;
import cn.bidostar.ticserver.utils.Utils;

/**
 * Created by admins on 2018/1/21.
 * 向服务器发送socket数据的service服务，并且此服务也监听carbindservice的状态
 */
public class SocketCarBindService extends Service implements carMotion.carMotionEventListener {

    public final static String TAG = "cn.bidostar.ticserver.service.SocketCarBindService";
    private static int timerRun = 10000;//GPS上传时间间隔
    public static boolean UPLOAD_QUEE = false;
    public static SocketCarBindService socketCarBindService = null;
    private Class userPushService = AppPushService.class;
    private LocationManager locationManager;
    public LocationListener locationListener = null;
    public static String APP_IMEI = "";
    public static String APP_SIMCID = "";
    //public static API mApi;
    private static double carSpeed = 120.00; //车辆速度
    public static String CAR_HB_MP4_TASKID = "";//合并视频的任务id
    public static Integer CAR_UP_SPEED_COUNT = 15;//连续超速倒计时  超速之后触发判断是否等于0 等于0让其播报一次
    public static boolean CAR_ON_RUN = false;//车辆是否点火

    public static int WIFIGPS = 1;//1 GPS  2 wifi
    private String provider;
    public static int CAR_UPLOAD_MP4_MODEL = 0;//是否在wifi模式下上传普通视频： 0 不上传普通视频 1 wifi上传普通视频 2 wifi/4G均上传视频
    //线程任务队列，用于处理定时上传GPS或是写入本地缓存等任务
    private ScheduledThreadPoolExecutor taskPool = new ScheduledThreadPoolExecutor(3);
    public static RequestCommonParamsDto pubDto;
    private String pkgName = "com.bidostar.rmt,cn.bidostar.ticserver";
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        I.e(TAG, "onStartCommand");

        return START_STICKY;
    }

    public void initApi(){
        Settings.Global.putInt(this.getContentResolver(),"autosleeptime", 0);
        initCarMotion();
        setWakeupLockWhiteList(pkgName);
        setAppKeepAlive(pkgName);
        setAppRTC(pkgName);
    }

    private boolean isFileExists(String path) {
        File file = new File(path);
        return file.exists();
    }

    private carMotion mCarMotion;
    private void initCarMotion() {
        if (!isFileExists("/sys/bus/platform/drivers/gsensor/read_reg")) {
            return;
        }
        mCarMotion = new carMotion();
        mCarMotion.Init(carMotion.CarMotionPinNum.PIN_NONE, carMotion.CarMotionPinLevel.NONE,
                carMotion.CarMotionPinNum.PIN_NONE, carMotion.CarMotionPinLevel.NONE);

        mCarMotion.Set_Debug_Level(0x0);
        mCarMotion.Control(carMotion.CarMotionAlgorithm.VIOLENT, carMotion.CarMotionSwitchCmd.ENABLE_X);

        /*
        0 {x,y,z} => {x,y,z}
        1 {x,y,z} => {y,‐x,z}
        2 {x,y,z} => {‐x,‐y,z}
        3 {x,y,z} => {‐y,x,z}
        4 {x,y,z} => {‐x,y,‐z}
        5 {x,y,z} => {y,x,‐z}
        6 {x,y,z} => {x,‐y,‐z}
        7 {x,y,z} => {‐y,‐x,‐z}
        8 {x,y,z} => {x,z,‐y}
        9 {x,y,z} => {‐y,z,‐x}
        10 {x,y,z} => {‐x,z,y}
        11 {x,y,z} => {y,z,x}
        12 {x,y,z} => {‐x,‐z,‐y}
        13 {x,y,z} => {‐y,‐z,x}
        14 {x,y,z} => {x,‐z,y}
        15 {x,y,z} => {y,‐z,‐x}
        */
        // mCarMotion.Direction_Set_Parma(13); // T1
        mCarMotion.Direction_Set_Parma(15); // T10

        /*
         设置加减速监测灵敏等级，level 1-6，默认为 2 :
         1. 速度从零加到百公里约58秒内，能够被检测到急加速;
         2. 速度从零加到百公里约29秒内，能够被检测到急加速;
         3. 速度从零加到百公里约19秒内，能够被检测到急加速;
         4. 速度从零加到百公里约14秒内，能够被检测到急加速;
         5. 速度从零加到百公里约11秒内，能够被检测到急加速;
         6. 速度从零加到百公里约9秒内，能够被检测到急加速
         */
        mCarMotion.Violent_Set_Parma(Integer.parseInt(AppSharedpreferencesUtils.get(AppConsts.Violent_Set_Parma_Key,2).toString()));

        registerCarMotionListener();
    }

    /**
     * carMotion库的回调(内部使用/Internal used only)
     * @exclude
     * {@hide}
     */
    @Override
    public void OncarMotionEvent(int eventCode, int value) {
        try{
            if (eventCode == carMotion.CarMotionEVENT.VIOLENT_NOTIFY) {
                RequestCommonParamsDto dto = new RequestCommonParamsDto();

                String event = "";
                switch (value) {
                    case carMotion.CarMotionViolent.VIOLENT_SPEED_DOWN:
                        dto.setEventType(AppConsts.CAR_JDOWN);//刹车
                        event = "speed down";
                        break;

                    case carMotion.CarMotionViolent.VIOLENT_SPEED_UP:
                        event = "speed up";
                        dto.setEventType(AppConsts.CAR_JSPEED);
                        break;

                    case carMotion.CarMotionViolent.VIOLENT_TURN_LEFT:
                        event = "turn left";
                        dto.setEventType(AppConsts.CAR_LORR);//左转
                        break;

                    case carMotion.CarMotionViolent.VIOLENT_TURN_RIGHT:
                        event = "turn right";
                        dto.setEventType(AppConsts.CAR_LORR);//右转
                        break;

                    default:
                        event = "unknown event";
                        break;
                }

                if(dto.getEventType()!=null && !dto.getEventType().equals("")){
                    ServerApiUtils.pushGpsInfo(dto,ServerApiUtils.gpsInfoCallback);
                }
            }
        }catch(Exception e){

        }
    }

    /**
     * 注册汽车移动监听回调
     * Register g-sensor motion listener
     *
     * @return None
     */
    public void registerCarMotionListener() {
        if (mCarMotion == null) {
            initCarMotion();
        }

        if (mCarMotion != null) {
            mCarMotion.RegisteOncarMotionEventListener(this);
        }
    }

    /**
     * 反注册汽车移动监听回调
     * Un-register g-sensor motion listener
     *
     * @return 无
     */
    public void unregisterCarMotionListener() {
        if (mCarMotion != null) {
            mCarMotion.UnRegisteOncarMotionEventListener(this);
        }
    }

    /**
     * When acc power is off, the system will kill not-in-white-list packages what still hold the wakeup lock then enter sleep mode.
     *
     * So if you want to do something before sleeping, for example uploading a photo to your cloud server when acc is off, you need to
     * set your apk's package name to the white list firstly,
     * then acquire a wakeup lock through standard Android API PowerManager.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK, "xxx".
     *
     * After done, release the wakeup lock to allow the system enter sleep mode.
     *
     * You can call this when your apk starts
     *
     *
     * 增加包名到系统抓锁白名单，系统会等待该package释放锁后才进入休眠,否则抓锁的app会被强行kill掉
     * 建议开机后立刻调用该接口，并保证对应app有抓锁行为，当然app本身逻辑要保证wakelock的正常释放，例如网络实在不行不通，超时后也要释放锁，
     * 否则系统无法休眠，会消耗汽车电瓶。
     *
     * @param packageName the white list package name list, use "," as the seperate char, for example com.android.xxx,foo.com.bar
     *                    if null, use current apk's packagename
     *
     */
    public void setWakeupLockWhiteList(String packageName) {
        Intent intent = new Intent();
        intent.setAction(CarIntents.ACTION_SET_PROP);
        intent.putExtra(CarIntents.EXTRA_SET_PROP_KEY, "sys.wakelock.whitelist");
        intent.putExtra(CarIntents.EXTRA_SET_PROP_VAL, packageName);
        sendBroadcast(intent);
    }

    /**
     * 新增的api 让app在休眠时可以不断网
     */
    public void setAppKeepAlive(String packageName){
        Intent intent = new Intent();
        intent.setAction(CarIntents.ACTION_SET_PROP);
        intent.putExtra(CarIntents.EXTRA_SET_PROP_KEY, "persist.sys.app.keepalive");
        intent.putExtra(CarIntents.EXTRA_SET_PROP_VAL, packageName);
        sendBroadcast(intent);
    }

    /**
     * #RTC定时唤醒系统程序
     */
    public void setAppRTC(String packageName){
        Intent intent = new Intent();
        intent.setAction(CarIntents.ACTION_SET_PROP);
        intent.putExtra(CarIntents.EXTRA_SET_PROP_KEY, "sys.rtc.packages");
        intent.putExtra(CarIntents.EXTRA_SET_PROP_VAL, packageName);
        sendBroadcast(intent);
    }
    @SuppressLint("NewApi")
    public void onCreate() {
        super.onCreate();
        if (AppSharedpreferencesUtils.context == null){
            AppSharedpreferencesUtils.init(this);
        }

        socketCarBindService = this;
        initThread();
        initLocal();//初始化定位
        getPubDto();
    }

    public void initThread(){
        String zt = AppSharedpreferencesUtils.get(AppConsts.CAR_GOTO_SLEEP,"00").toString();
        //I.e("init GPS ZT:"+zt);
        //如果设备处于休眠状态，则关闭数据上报功能
        if ("10".equals(zt)){
            AppApplication.getInstance().uploadGps();
            //如果是休眠状态下在线升级了apk，则不会触发sleep，手工触发一次，保证系统逻辑正常运行
            Intent intent = new Intent(CarIntents.ACTION_GOTOSLEEP);
            sendBroadcast(intent);
            return;
        }
        initApi();
        if (taskPool == null){
            taskPool = new ScheduledThreadPoolExecutor(3);
        }
        //延迟加载配置信息，避免内存泄露
        taskPool.schedule(new Runnable() {
            @Override
            public void run() {
                timerRun = (Integer)AppSharedpreferencesUtils.get(AppConsts.UPLOAD_GPS_TIMER,10000);//默认20秒更新上传一次的心跳
                carSpeed = Double.parseDouble(AppSharedpreferencesUtils.get(AppConsts.CAR_SPEED_KEY,120.00).toString());//车辆超速设定，默认80
                CAR_UPLOAD_MP4_MODEL = (Integer)AppSharedpreferencesUtils.get(AppConsts.CAR_UPLOAD_MP4_MODEL,0);
                if(NetworkUtil.isConnected(socketCarBindService) && NetworkUtil.isWifi(socketCarBindService)){
                    WIFIGPS = 2;//设置为wifi链接状态
                };
            }
        }, 500, TimeUnit.MILLISECONDS);

        setUploadQuee(false);
        //循环执行GPS上传事件
        taskPool.scheduleWithFixedDelay(timeRunExc, 5, 5, TimeUnit.SECONDS);
    }

    @Override
    public void onDestroy() {
        //I.e(TAG, "onDestroy");
        stopWithSleep();
        socketCarBindService = null;
        super.onDestroy();
        unregisterCarMotionListener();
    }

    public void stopWithSleep(){
        if (taskPool != null){
            taskPool.shutdownNow();
        }
        taskPool = null;
        try{
            if (locationManager != null){
                locationManager.removeUpdates(locationListener);
                locationManager.removeUpdates(locationListener);
                locationManager = null;

                locationListener = null;
            }
        }catch (Exception e){
            I.e(TAG, "onDestroy exception:"+e.getMessage());
        }
    }

    Runnable timeRunExc = new Runnable() {

        @Override
        public void run() {
            //I.e(TAG, "timeRunExc upload GPS");
            //上传GPS行驶数据
            RequestCommonParamsDto dto = new RequestCommonParamsDto();
            ServerApiUtils.pushGpsInfo(dto, ServerApiUtils.gpsInfoCallback);
        }
    };


    /**
     * 得到设备的IMEI
     *
     * @param
     * @return IMEI号码
     */
    @SuppressLint("MissingPermission")
    public String getDeviceIMEI() {
        TelephonyManager tm = (TelephonyManager)getSystemService(Context.TELEPHONY_SERVICE);
        return tm.getDeviceId();
    }

    /**
     * 在内存紧张的时候，系统回收内存时，会回调OnTrimMemory， 重写onTrimMemory当系统清理内存时从新启动Service1
     */
    @Override
    public void onTrimMemory(int level) {
        boolean closeApi = false;
        switch (level) {
            //TRIM_MEMORY_UI_HIDDEN 表示应用程序的所有UI界面被隐藏了，即用户点击了Home键或者Back键导致应用的UI界面不可见．这时候应该释放一些资源
            case Activity.TRIM_MEMORY_UI_HIDDEN:
                break;
            //TRIM_MEMORY_RUNNING_MODERATE 表示应用程序正常运行，并且不会被杀掉。但是目前手机的内存已经有点低了，系统可能会开始根据LRU缓存规则来去杀死进程了。
            case Activity.TRIM_MEMORY_RUNNING_MODERATE:
                break;
            //TRIM_MEMORY_RUNNING_LOW 表示应用程序正常运行，并且不会被杀掉。但是目前手机的内存已经非常低了，我们应该去释放掉一些不必要的资源以提升系统的性能，同时这也会直接影响到我们应用程序的性能。
            case Activity.TRIM_MEMORY_RUNNING_LOW:
                break;
            //TRIM_MEMORY_RUNNING_CRITICAL 表示应用程序仍然正常运行，但是系统已经根据LRU缓存规则杀掉了大部分缓存的进程了。这个时候我们应当尽可能地去释放任何不必要的资源，不然的话系统可能会继续杀掉所有缓存中的进程，并且开始杀掉一些本来应当保持运行的进程，比如说后台运行的服务。
            case Activity.TRIM_MEMORY_RUNNING_CRITICAL:
                I.e("close gps data thread by TRIM_MEMORY_RUNNING_CRITICAL");
                taskPool.shutdownNow();
                closeApi = true;
                break;
            //当应用程序是缓存的，则会收到以下几种类型的回调：
            //TRIM_MEMORY_BACKGROUND 表示手机目前内存已经很低了，系统准备开始根据LRU缓存来清理进程。这个时候我们的程序在LRU缓存列表的最近位置，是不太可能被清理掉的，但这时去释放掉一些比较容易恢复的资源能够让手机的内存变得比较充足，从而让我们的程序更长时间地保留在缓存当中，这样当用户返回我们的程序时会感觉非常顺畅，而不是经历了一次重新启动的过程。
            case Activity.TRIM_MEMORY_BACKGROUND:
                break;
            //TRIM_MEMORY_MODERATE 表示手机目前内存已经很低了，并且我们的程序处于LRU缓存列表的中间位置，如果手机内存还得不到进一步释放的话，那么我们的程序就有被系统杀掉的风险了。
            case Activity.TRIM_MEMORY_MODERATE:
                I.e("close gps data thread by TRIM_MEMORY_MODERATE");
                taskPool.shutdownNow();
                closeApi = true;
                break;
            //TRIM_MEMORY_COMPLETE 表示手机目前内存已经很低了，并且我们的程序处于LRU缓存列表的最边缘位置，系统会最优先考虑杀掉我们的应用程序，在这个时候应当尽可能地把一切可以释放的东西都进行释放。
            case Activity.TRIM_MEMORY_COMPLETE:
                I.e("close gps data thread by TRIM_MEMORY_COMPLETE");
                taskPool.shutdownNow();
                closeApi = true;
                break;
        }
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }


    public void setGpsNoNetWorkFlag(String flag){
        AppSharedpreferencesUtils.put(AppConsts.CAR_GPS_NO_NETWORK_DATA,flag);
    }

    /**
     * true 需要去遍历  false 不需要去遍历数据
     * @return
     */
    public boolean getGpsNoNetWorkFlag(){
        String flag = (String)AppSharedpreferencesUtils.get(AppConsts.CAR_GPS_NO_NETWORK_DATA,"00");
        if(flag.equals("00")){
            return false;
        }
        return true;
    }

    public static void setTimerRun(int timerS){
        if(timerS<2){
            timerS = 2;
        }
        timerS = timerS*1000;
        timerRun = timerS;
        AppSharedpreferencesUtils.put(AppConsts.UPLOAD_GPS_TIMER,timerRun);
    }

    public String getServerUrlBase(){
        return AppSharedpreferencesUtils.get(AppConsts.CAR_BASE_SERVER_URL,AppConsts.BASE_URL).toString();
    }

    public static void setCarUploadMp4Model(int f){
        CAR_UPLOAD_MP4_MODEL = f;
        I.d(TAG,"设置文件上传模式："+f);
        AppSharedpreferencesUtils.put(AppConsts.CAR_UPLOAD_MP4_MODEL,f);
    }

    public static void setCarSpeed(double speed){
        if(speed<20){
            speed = 20.00;
        }
        carSpeed = speed;
        AppSharedpreferencesUtils.put(AppConsts.CAR_SPEED_KEY,carSpeed);
    }

    public double getCarSpeedSetting(){
        // carSpeed = AppSharedpreferencesUtils.get(AppConsts.CAR_SPEED_KEY,40.00)
        carSpeed = Double.parseDouble(AppSharedpreferencesUtils.get(AppConsts.CAR_SPEED_KEY,120.00).toString());
        return carSpeed;
    }


    public void setUploadQuee(boolean f){
        AppSharedpreferencesUtils.put(AppConsts.CAR_UPLOAD_QUEE,f);
        AppApplication.getInstance().UPLOAD_QUEE = f;
        I.d(TAG,"文件上传设置队列状态"+AppApplication.getInstance().UPLOAD_QUEE);
    }

    public boolean getUploadQuee(){
        AppApplication.getInstance().UPLOAD_QUEE = (boolean)AppSharedpreferencesUtils.get(AppConsts.CAR_UPLOAD_QUEE,false);
        I.d(TAG,"文件上传队列状态"+AppApplication.getInstance().UPLOAD_QUEE);
        return  (boolean)AppSharedpreferencesUtils.get(AppConsts.CAR_UPLOAD_QUEE,false);
    }


    public String getCarGPSFlag(){
        //车辆是否运行的标识
        CAR_ON_RUN = (Boolean) AppSharedpreferencesUtils.get(AppConsts.CAR_ON_RUN_FLAG,false);
        //return CAR_ON_RUN ? AppConsts.CAR_RUN_GPS : AppConsts.CAR_OFF_GPS;
        return CAR_ON_RUN ? "10" : "20";
    }
    public int getCarUploadMp4Model(){
        CAR_UPLOAD_MP4_MODEL = (Integer)AppSharedpreferencesUtils.get(AppConsts.CAR_UPLOAD_MP4_MODEL,1);
        I.d(TAG,"文件上传模式："+CAR_UPLOAD_MP4_MODEL);
        return  (Integer)AppSharedpreferencesUtils.get(AppConsts.CAR_UPLOAD_MP4_MODEL,1);
    }

    /**
     * 清空数据
     */
    public void clearDatabase(){
        LocalFilesModelDao dto = new LocalFilesModelDao();
        List<LocalFilesModel> all = dto.findAll();
        I.e(TAG,"data db:"+JSON.toJSONString(all));
        for (int i = 0;i<all.size();i++){
            dto.deleteLikeFileName(all.get(i).getLocalPath());
        }
    }

    public RequestCommonParamsDto getPubDto(){
        if(pubDto == null){
            pubDto = new RequestCommonParamsDto();
            pubDto.setLatitude(AppSharedpreferencesUtils.get(AppConsts.CAR_LOCAL_LAT,"-1").toString());
            pubDto.setLongitude(AppSharedpreferencesUtils.get(AppConsts.CAR_LOCAL_LNG,"-1").toString());
            pubDto.setGpsjd(AppSharedpreferencesUtils.get(AppConsts.CAR_LOCAL_PRE,"0").toString());
            pubDto.setFxj(AppSharedpreferencesUtils.get(AppConsts.CAR_LOCAL_DIRECTION,"0").toString());
            pubDto.setSpeed(AppSharedpreferencesUtils.get(AppConsts.CAR_LOCAL_SPEED,"0").toString());
        }
        return pubDto;
    }
    /**
     * 上传数据库中的文件，切换到wifi的时候
     */
    public void uploadDatabase(){

        boolean checkNetwork = NetworkUtil.isConnected(AppApplication.getInstance().getContext());

        if(!checkNetwork){
            I.i(TAG,"uploadDatabase Start 没有网络");
            //AppApplication.getInstance().UPLOAD_QUEE = false;
            setUploadQuee(false);
            return ;
        }
        //没有连接wifi网络不上传视频
        boolean isWifi = NetworkUtil.isWifi(AppApplication.getInstance().getContext());
        if (!isWifi){
            return;
        }
        LocalFilesModel localFilesModel = new LocalFilesModel();
        localFilesModel.setFlagUpload("0");
        //后台配置了不上传视频，就不执行操作
        if (getCarUploadMp4Model() == 0) {
            return;
        }

        LocalFilesModelDao dao = new LocalFilesModelDao();
        List<LocalFilesModel> fileList = dao.findListByFlagupload(localFilesModel);

        if(checkNetwork && fileList!=null && !fileList.isEmpty() && fileList.size()>0) {
            I.d(TAG,"准备上传文件...:"+ JSON.toJSONString(fileList));
            LocalFilesModel s = fileList.get(0);
            I.e(TAG,"updateFile Start 有网络，有文件，开始上传文件"+s.getLocalPath());
            boolean b = false;
            /*
            if (AppApplication.getInstance().CAR_UPLOAD_MP4_MODEL == 0) {
                if (!s.getJltype().equals("2")) {
                    b = ServerApiUtils.uploadFileLoop("102", s.getLocalPath(), ServerApiUtils.fileUploadCallback);//直接上传
                }
            } else if(AppApplication.getInstance().CAR_UPLOAD_MP4_MODEL) {
                b = ServerApiUtils.uploadFileLoop("102", s.getLocalPath(), ServerApiUtils.fileUploadCallback);//直接上传
            }*/
            //b = ServerApiUtils.uploadFileLoop("102", s.getLocalPath(), ServerApiUtils.fileUploadLoopCallback);//直接上传
            String deviceId = getDeviceIMEI();
            if(s!=null && (s.getLocalPath()==null || (!s.getLocalPath().isEmpty() && s.getLocalPath().contains("S.ts"))) ){
                dao.delete(s);
                setUploadQuee(false);
            }else {

                if(deviceId==null||deviceId.equals("")||deviceId.equals("null") || s==null || s.getLocalPath().isEmpty()){
                    setUploadQuee(false);
                }else {
                    b = ServerApiUtils.uploadFileLoop("102", s.getLocalPath(), ServerApiUtils.fileUploadLoopCallback);
                    if (checkNetwork && b == false) {//有网络的时候返回false 多半是文件不存在，删除文件
                        dao.delete(s);
                        setUploadQuee(false);
                    }
                }
            }
        }else{
            I.d(TAG,"updateFile Start 没有网络或者没有文件");
            setUploadQuee(false);
        }
    }

    @SuppressLint("MissingPermission")
    public void initLocal() {
        String zt = AppSharedpreferencesUtils.get(AppConsts.CAR_GOTO_SLEEP,"00").toString();
        //I.e("init GPS ZT:"+zt);
        //如果设备处于休眠状态，则关闭GPS功能
        if ("10".equals(zt)){
            return;
        }
        if (locationManager == null){
            //I.e(TAG, "init GPS locationManager");
            locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        }

        if (locationListener == null){
            //I.e(TAG, "init GPS locationListener");
            locationListener = new LocationListener() {
                public void onLocationChanged(Location location) {
                    showLocation(location);
                }

                public void onProviderDisabled(String provider) {
                    showLocation(null);
                }

                @SuppressLint("MissingPermission")
                public void onProviderEnabled(String provider) {
                    showLocation(locationManager.getLastKnownLocation(provider));
                }

                public void onStatusChanged(String provider, int status, Bundle extras) {
                    I.e(TAG,"local GPS error "+provider+" status "+ status);
                }
            };
        }

        //获取当前可用的位置控制器
        List<String> list = locationManager.getProviders(true);
        if (list.contains(LocationManager.GPS_PROVIDER)) {
            //是否为GPS位置控制器
            provider = LocationManager.GPS_PROVIDER;
        }
        else if (list.contains(LocationManager.NETWORK_PROVIDER)) {
            //是否为网络位置控制器
            provider = LocationManager.NETWORK_PROVIDER;

        } else {
            Toast.makeText(this, "请检查网络或GPS是否打开",
                    Toast.LENGTH_LONG).show();
            return;
        }
        @SuppressLint("MissingPermission")
        Location location = locationManager.getLastKnownLocation(provider);
        showLocation(location);
        //
        locationManager.requestLocationUpdates(provider, 2000, 10, locationListener);
    }
    public void showLocation(final Location currentLocation){
        if(currentLocation != null){
            String s = "";
            s += " Current Location: (";
            s += currentLocation.getLongitude();
            s += ",";
            s += currentLocation.getLatitude();
            s += ")\n Speed: ";
            s += currentLocation.getSpeed();
            s += "\n Direction: ";
            s += currentLocation.getBearing();
            s+="\n Provider:"+currentLocation.getProvider();
            //I.e(TAG,"local:"+s);
            //mMsgShow.setText("local result: " + s);
            // text.setText(s);
            String cuSpeed = (currentLocation.getSpeed()*3.6)+"";
            cuSpeed= cuSpeed.substring(0,cuSpeed.indexOf("."));
            final int cur = Integer.parseInt(cuSpeed);
            String jd = currentLocation.getAccuracy()+"";
            if(jd.contains(".")){
                jd = jd.substring(0,jd.indexOf("."));
            }

            final DecimalFormat sdf = new DecimalFormat(".00000000000");
            //写入本地缓存，使用线程来单独处理
            taskPool.execute(new Runnable() {
                @Override
                public void run() {
                    AppSharedpreferencesUtils.put(AppConsts.CAR_LOCAL_LAT,sdf.format(currentLocation.getLatitude()));//纬度
                    AppSharedpreferencesUtils.put(AppConsts.CAR_LOCAL_LNG,sdf.format(currentLocation.getLongitude()));//经度
                    String tjd = currentLocation.getAccuracy()+"";
                    if(tjd.contains(".")){
                        tjd = tjd.substring(0, tjd.indexOf("."));
                    }
                    AppSharedpreferencesUtils.put(AppConsts.CAR_LOCAL_PRE,tjd);//定位精度
                    AppSharedpreferencesUtils.put(AppConsts.CAR_LOCAL_DIRECTION,currentLocation.getBearing()+"");//方向角
                    AppSharedpreferencesUtils.put(AppConsts.CAR_LOCAL_SPEED,cur+"");//速度
                }
            });
            if(pubDto == null){
                pubDto = new RequestCommonParamsDto();

            }
            pubDto.setLatitude(sdf.format(currentLocation.getLatitude()));
            pubDto.setLongitude(sdf.format(currentLocation.getLongitude()));
            pubDto.setGpsjd(jd);
            pubDto.setFxj(currentLocation.getBearing()+"");
            pubDto.setSpeed(cur+"");
            CAR_UP_SPEED_COUNT = CAR_UP_SPEED_COUNT--;
            if(getCarSpeedSetting()<cur){//超速了。直接上传
                I.e(TAG,"Car speed >"+carSpeed);

                RequestCommonParamsDto dto = new RequestCommonParamsDto();
                dto.setEventType(AppConsts.CAR_CSPEED);
                ServerApiUtils.pushGpsInfo(dto,ServerApiUtils.gpsInfoCallback);
                try{
                    //  mApi.playTts("您已超速，请您减速运行");
                    if(CAR_UP_SPEED_COUNT <= 0) {
                        //mApi.playTts("您已超速，请您减速运行，当前速度" + pubDto.getSpeed() + "KM");
                        CAR_UP_SPEED_COUNT = 15;
                    }
                }catch (Exception e){
                }
            }

            if(getGpsNoNetWorkFlag()){
                RequestCommonParamsDtoDao dao = new RequestCommonParamsDtoDao();
                setGpsNoNetWorkFlag("00");//需要进行检测上传了
                List<RequestCommonParamsDto> lis = dao.findAll();
                ServerApiUtils.pushGpsInfoByLocalDb(lis,ServerApiUtils.gpsInfoAllCallback);
            }
        }
    }
}
