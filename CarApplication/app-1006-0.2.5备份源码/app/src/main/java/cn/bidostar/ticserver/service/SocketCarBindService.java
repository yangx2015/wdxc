package cn.bidostar.ticserver.service;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.IBinder;
import android.os.SystemClock;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.widget.Toast;

import cn.bidostar.ticserver.CarApplication;
import cn.bidostar.ticserver.dao.RequestCommonParamsDtoDao;
import cn.bidostar.ticserver.model.RequestCommonParamsDto;
import cn.bidostar.ticserver.receiver.APICarReceive;
import cn.bidostar.ticserver.utils.AlarmManagerUtils;
import cn.bidostar.ticserver.utils.AppConsts;
import cn.bidostar.ticserver.utils.AppSharedpreferencesUtils;
import cn.bidostar.ticserver.utils.CarIntents;
import cn.bidostar.ticserver.utils.I;
import cn.bidostar.ticserver.utils.NetworkUtil;
import cn.bidostar.ticserver.utils.ServerApiUtils;
import com.igexin.sdk.PushManager;
import com.miramems.carmotion.carMotion;

import java.io.File;
import java.text.DecimalFormat;
import java.util.Date;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.logging.Handler;

import cn.bidostar.ticserver.CarApplication;
import cn.bidostar.ticserver.model.RequestCommonParamsDto;
import cn.bidostar.ticserver.utils.AppConsts;
import cn.bidostar.ticserver.utils.AppSharedpreferencesUtils;
import cn.bidostar.ticserver.utils.I;
import cn.bidostar.ticserver.utils.ServerApiUtils;
import com.miramems.carmotion.carMotion;

import org.xutils.common.Callback;

public class SocketCarBindService extends Service implements carMotion.carMotionEventListener {
    //private String pkgName = "cn.bidostar.ticserver,com.bidostar.rmt";
    public final static String pkgName = "com.bidostar.rmt,com.bidostar.liveassistant,cn.bidostar.ticserver";
    int errorNum = 0;
    //
    private double carSpeed = 120.00; //车辆速度
    public String CAR_HB_MP4_TASKID = "";//合并视频的任务id
    public Integer CAR_UP_SPEED_COUNT = 15;//连续超速倒计时  超速之后触发判断是否等于0 等于0让其播报一次

    private String provider;
    public int CAR_UPLOAD_MP4_MODEL = 0;//是否在wifi模式下上传普通视频： 0 不上传普通视频 1 wifi上传普通视频 2 wifi/4G均上传视频
    //线程任务队列，用于处理定时上传GPS或是写入本地缓存等任务
    private ScheduledThreadPoolExecutor taskPool = null;
    private LocationManager locationManager;
    public LocationListener locationListener = null;
    private APICarReceive carReceive;

    public void reloadPush(){
        errorNum++;
        if (errorNum > 5){
            errorNum = 0;
            return;
        }
        SystemClock.sleep(2000);
        initPush();
    }
    @SuppressLint("MissingPermission")
    public void initPush(){
        try{
            String deviceId = CarApplication.getApplication().getDeviceIMEI();
            if (deviceId == null || "null".equals(deviceId)){
                reloadPush();
            }else{
                CarApplication.getApplication().initPush();

                errorNum = 0;
            }
        }catch(Exception e){
            I.e("SocketCarBindService initPush exception", e);
        } catch (Throwable throwable) {
            I.e("Socketservice-initPush", throwable);
        }
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        //I.e("SocketCarBindService","onStartCommand>>>>>");
        try{
            setWakeupLockWhiteList(pkgName);
            setAppKeepAlive(pkgName);
            setAppRTC("com.bidostar.rmt");

            initPush();
            String zt = AppSharedpreferencesUtils.get(AppConsts.CAR_GOTO_SLEEP,"00").toString();
            if ("10".equals(zt)){
                if (taskPool != null){
                    //休眠停止任务
                    stopWithSleep();
                }
            }else{
                if (taskPool == null){
                    initService();
                }
            }
        }catch (Exception e){
            I.e("SocketService onStartCommand", e);
        } catch (Throwable throwable) {
            I.e("Socketservice-onStartCommand", throwable);
        }

        return START_STICKY;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        initService();
    }

    /**
     * 初始化service
     */
    public void initService(){
        CarApplication.getApplication().getPubDto();
        initThread();
    }

    /**
     * 注册车辆视频事件
     */
    public void registerApiCar(){
        if (carReceive == null) {
            carReceive = new APICarReceive();
            IntentFilter filter = new IntentFilter();
            filter.addAction(CarIntents.ACTION_RECORDING_STORAGE_SLOW);
            filter.addAction(CarIntents.ACTION_CAPTURE_CUSTOM_VIDEO);
            filter.addAction(CarIntents.ACTION_CAPTURE_FILE_INFO);
            filter.addAction(CarIntents.ACTION_CAMERA_SNAPSHOT_CALLBACK);
            registerReceiver(carReceive, filter);
        }
    }

    public void unRegisterApiCar(){
        if (carReceive != null) {
            unregisterReceiver(carReceive);
            carReceive = null;
        }
    }

    /**
     * 初始化执行线程队列。只有当点火状态才执行GPS数据上传
     */
    public void initThread(){
        try{
            String zt = AppSharedpreferencesUtils.get(AppConsts.CAR_GOTO_SLEEP,"00").toString();
            //I.e("init GPS ZT:"+zt);
            //如果设备处于休眠状态，则关闭数据上报功能
            if ("10".equals(zt)){
                AppSharedpreferencesUtils.put(AppConsts.CAR_GOTO_SLEEP,"10");
                RequestCommonParamsDto dto = new RequestCommonParamsDto();
                dto.setEventType(AppConsts.CAR_OFF);
                ServerApiUtils.getInstance().pushGpsInfo(dto, ServerApiUtils.gpsInfoCallback);

                //如果是休眠状态下在线升级了apk，则不会触发sleep，手工触发一次，保证系统逻辑正常运行
                sendBroadcast(new Intent(CarIntents.ACTION_FORCE_SLEEP));
                return;
            }

            initApi();
            initLocal();
            registerApiCar();
            if (taskPool == null){
                taskPool = new ScheduledThreadPoolExecutor(3);
            }
            //循环执行GPS上传事件
            taskPool.scheduleWithFixedDelay(postGpsThread, 5, 5, TimeUnit.SECONDS);
            //循环执行GPS补传事件，每10分钟执行一次
            taskPool.scheduleWithFixedDelay(postHistoryGpsThread, 0, 10, TimeUnit.MINUTES);
            carSpeed = Double.parseDouble(AppSharedpreferencesUtils.get(AppConsts.CAR_SPEED_KEY,120.00).toString());//车辆超速设定，默认80
        }catch(Exception e){
            I.e("SocketService", e);
        } catch (Throwable throwable) {
            I.e("Socketservice-initThread", throwable);
        }
    }

    /**
     * GPS实时点上传
     */
    Runnable postGpsThread = new Runnable() {
        @Override
        public void run() {
            //上传GPS行驶数据
            RequestCommonParamsDto dto = new RequestCommonParamsDto();
            ServerApiUtils.getInstance().pushGpsInfo(dto, ServerApiUtils.gpsInfoCallback);
        }
    };

    /**
     * GPS补传，每5分钟执行一次
     */
    Runnable postHistoryGpsThread = new Runnable() {
        @Override
        public void run() {
            try{
                RequestCommonParamsDtoDao dao = new RequestCommonParamsDtoDao();
                List<RequestCommonParamsDto> lis = dao.findAll();
                if (lis != null && lis.size() > 0){
                    ServerApiUtils.getInstance().pushGpsInfoByLocalDb(lis);
                }
            }catch (Exception e){
                I.e("Socketservice-postHistoryGpsThread", e);
            } catch (Throwable throwable) {
                I.e("Socketservice-postHistoryGpsThread", throwable);
            }
        }
    };

    /**
     * 在内存紧张的时候，系统回收内存时，会回调OnTrimMemory， 重写onTrimMemory当系统清理内存时从新启动Service1
     */
    @Override
    public void onTrimMemory(int level) {
        //I.e("ServiceSocket", "onTrimMemory>>>>>>"+level);
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
                taskPool = null;
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
                taskPool = null;
                closeApi = true;
                break;
            //TRIM_MEMORY_COMPLETE 表示手机目前内存已经很低了，并且我们的程序处于LRU缓存列表的最边缘位置，系统会最优先考虑杀掉我们的应用程序，在这个时候应当尽可能地把一切可以释放的东西都进行释放。
            case Activity.TRIM_MEMORY_COMPLETE:
                I.e("close gps data thread by TRIM_MEMORY_COMPLETE");
                taskPool.shutdownNow();
                taskPool = null;
                closeApi = true;
                break;
        }

        try {
            SystemClock.sleep(2000);
            Intent serviceTwo = new Intent();
            serviceTwo.setClass(CarApplication.getApplication().getApplicationContext(), SocketCarBindService.class);
            CarApplication.getApplication().getApplicationContext().startService(serviceTwo);
        } catch (Exception e) {
        } catch (Throwable e) {
        }
    }

    @SuppressLint("MissingPermission")
    public void initLocal() {
        try{
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
                        //I.e(TAG,"local GPS error "+provider+" status "+ status);
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
            }else{
                provider = LocationManager.NETWORK_PROVIDER;
            }
            @SuppressLint("MissingPermission")
            Location location = locationManager.getLastKnownLocation(provider);
            showLocation(location);
            //
            locationManager.requestLocationUpdates(provider, 2000, 10, locationListener);
        }catch (Exception e){
            I.e("Socketservice-initLocal", e);
        }catch (Throwable e){
            I.e("Socketservice-initLocal", e);
        }

    }

    public double getCarSpeedSetting(){
        // carSpeed = AppSharedpreferencesUtils.get(AppConsts.CAR_SPEED_KEY,40.00)
        carSpeed = Double.parseDouble(AppSharedpreferencesUtils.get(AppConsts.CAR_SPEED_KEY,120.00).toString());
        return carSpeed;
    }

    public void showLocation(final Location currentLocation){
        try{
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
                if (CarApplication.getApplication().pubDto == null){
                    CarApplication.getApplication().pubDto = new RequestCommonParamsDto();
                }
                CarApplication.getApplication().pubDto.setLatitude(sdf.format(currentLocation.getLatitude()));
                CarApplication.getApplication().pubDto.setLongitude(sdf.format(currentLocation.getLongitude()));
                CarApplication.getApplication().pubDto.setGpsjd(jd);
                CarApplication.getApplication().pubDto.setFxj(currentLocation.getBearing()+"");
                CarApplication.getApplication().pubDto.setSpeed(cur+"");
                CAR_UP_SPEED_COUNT = CAR_UP_SPEED_COUNT--;
                if(getCarSpeedSetting()<cur){//超速了。直接上传
                    RequestCommonParamsDto dto = new RequestCommonParamsDto();
                    dto.setEventType(AppConsts.CAR_CSPEED);
                    ServerApiUtils.getInstance().pushGpsInfo(dto,ServerApiUtils.gpsInfoCallback);
                    try{
                        //  mApi.playTts("您已超速，请您减速运行");
                        if(CAR_UP_SPEED_COUNT <= 0) {
                            //mApi.playTts("您已超速，请您减速运行，当前速度" + pubDto.getSpeed() + "KM");
                            CAR_UP_SPEED_COUNT = 15;
                        }
                    }catch (Exception e){
                    }
                }
            }
        }catch (Exception e){
            I.e("Socketservice-showLocation", e);
        }

    }

    public void stopWithSleep(){
        try{
            if (taskPool != null){
                taskPool.shutdownNow();
            }
            taskPool = null;
            if (locationManager != null){
                locationManager.removeUpdates(locationListener);
                locationManager = null;

                locationListener = null;
            }
        }catch (Exception e){
            I.e("Socketservice-stopWithSleep", e);
        }
    }

    @Override
    public void onDestroy() {
        try{
            stopWithSleep();
            unRegisterApiCar();
            unregisterCarMotionListener();
        }catch(Exception e){
            I.e("Socketservice-onDestroy", e);
        }

        I.e("SocketCarBindService","onDestroy>>>>>");

        stopSelf();
    }

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

    public void initApi(){
        Settings.Global.putInt(this.getContentResolver(),"autosleeptime", 0);
        initCarMotion();
    }

    private boolean isFileExists(String path) {
        File file = new File(path);
        return file.exists();
    }

    private carMotion mCarMotion;

    /**
     * 设置急加速灵敏度
     * @param violent_Set_Parma
     */
    public void setViolent_Set_Parma(int violent_Set_Parma){
        violent_Set_Parma = violent_Set_Parma == 0 ? 2 : violent_Set_Parma;
        AppSharedpreferencesUtils.put(AppConsts.Violent_Set_Parma_Key,violent_Set_Parma);
        mCarMotion.Violent_Set_Parma(violent_Set_Parma);
    }

    private void initCarMotion() {
        try{
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
        }catch (Exception e){
            I.e("Socketservice-initCarMotion", e);
        }
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
                //I.e("SocketService-OncarMotionEvent", "eventCode>>>>>"+value);
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
                    ServerApiUtils.getInstance().pushGpsInfo(dto, ServerApiUtils.gpsInfoCallback);
                }
            }
        }catch(Exception e){
            I.e("Socketservice-OncarMotionEvent", e);
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
            mCarMotion = null;
        }
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
