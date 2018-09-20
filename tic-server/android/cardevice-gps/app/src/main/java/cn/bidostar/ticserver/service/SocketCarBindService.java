package cn.bidostar.ticserver.service;

import android.annotation.SuppressLint;
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
import android.support.annotation.Nullable;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.miramems.carmotion.carMotion;

import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.DecimalFormat;
import java.util.List;
import java.util.concurrent.locks.ReentrantLock;

import cn.bidostar.ticserver.AppApplication;
import cn.bidostar.ticserver.dao.LocalFilesModelDao;
import cn.bidostar.ticserver.dao.RequestCommonParamsDtoDao;
import cn.bidostar.ticserver.model.LocalFilesModel;
import cn.bidostar.ticserver.model.RequestCommonParamsDto;
import cn.bidostar.ticserver.utils.AppConsts;
import cn.bidostar.ticserver.utils.AppSharedpreferencesUtils;
import cn.bidostar.ticserver.utils.I;
import cn.bidostar.ticserver.utils.NetworkUtil;
import cn.bidostar.ticserver.utils.ServerApiUtils;
import cn.wang.weicy.device.cardevice.StrongServiceAidlInterface;
import cn.bidostar.ticserver.utils.Utils;

/**
 * Created by admins on 2018/1/21.
 * 向服务器发送socket数据的service服务，并且此服务也监听carbindservice的状态
 */
public class SocketCarBindService extends Service implements API.CarMotionListener{

    public final static String TAG = "cn.bidostar.ticserver.service.SocketCarBindService";
    private static int timerRun = 10000;//GPS上传时间间隔
    public static boolean UPLOAD_QUEE = false;
    public static SocketCarBindService socketCarBindService = null;
    private Class userPushService = AppPushService.class;
    private LocationManager locationManager;
    public static String APP_IMEI = "";
    public static String APP_SIMCID = "";
    public static API mApi;
    private static double carSpeed = 120.00; //车辆速度
    public static String CAR_HB_MP4_TASKID = "";//合并视频的任务id
    public static Integer CAR_UP_SPEED_COUNT = 15;//连续超速倒计时  超速之后触发判断是否等于0 等于0让其播报一次
    public static boolean CAR_ON_RUN = false;//车辆是否点火

    public static int WIFIGPS = 1;//1 GPS  2 wifi
    private String provider;
    public static int CAR_UPLOAD_MP4_MODEL = 1;//是否在wifi模式下上传普通视频： 0 不上传普通视频 1 wifi上传普通视频 2 wifi/4G均上传视频

    public static RequestCommonParamsDto pubDto;
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.e("SocketCarBindService", "onStartCommand"+TAG);
        initApi();
        getPubDto();

        return START_STICKY;
    }
    @SuppressLint("NewApi")
    public void onCreate() {
        super.onCreate();
        socketCarBindService = this;
        initApi();
        initLocal();//初始化定位
        timerRun = (Integer)AppSharedpreferencesUtils.get(AppConsts.UPLOAD_GPS_TIMER,10000);//默认20秒更新上传一次的心跳
        carSpeed = Double.parseDouble(AppSharedpreferencesUtils.get(AppConsts.CAR_SPEED_KEY,120.00).toString());//车辆超速设定，默认80

        //5秒后执行 每5秒执行
        handler.postDelayed(timeRunExc, 5000);
        CAR_UPLOAD_MP4_MODEL = (Integer)AppSharedpreferencesUtils.get(AppConsts.CAR_UPLOAD_MP4_MODEL,1);

        if(NetworkUtil.isConnected(this) && NetworkUtil.isWifi(this)){
            WIFIGPS = 2;//设置为wifi链接状态
        };
        getPubDto();
        setUploadQuee(false);
        /*
        Toast.makeText(SocketCarBindService.this, "SocketCarBindService 启动中...", Toast.LENGTH_SHORT)
                .show();*/
        startService1();
        /*
         * 此线程用监听Service2的状态
         */
        mApi.setWakeupLockWhiteList("com.bidostar.rmt,cn.bidostar.ticserver");
        mApi.setAppKeepAlive("com.bidostar.rmt,cn.bidostar.ticserver");
        mApi.setAppKeepAlive2("com.bidostar.rmt,cn.bidostar.ticserver");
        new Thread() {
            public void run() {
                while (true) {
                    boolean isRun = Utils.isServiceWork(SocketCarBindService.this,
                            "cn.bidostar.ticserver.service.CarBindService");
                    if (!isRun) {
                        Message msg = Message.obtain();
                        msg.what = 1;
                        handler.sendMessage(msg);
                    }
                    try {
                        Thread.sleep(1);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            };
        }.start();
    }

    private Handler handler = new Handler() {
        public void handleMessage(android.os.Message msg) {
            switch (msg.what) {
                case 1:
                    startService1();
                    break;

                default:
                    break;
            }

        };
    };
    Runnable timeRunExc = new Runnable() {

        @Override
        public void run() {
            //do something
            //上传GPS行驶数据
            RequestCommonParamsDto dto = new RequestCommonParamsDto();

            ServerApiUtils.pushGpsInfo(dto, ServerApiUtils.gpsInfoCallback);

            //每隔timerRun 循环执行run方法
            AppApplication.getInstance().initPushServer();
            handler.postDelayed(this, timerRun);
            checkUpload ();

            //mApi.playTts("您已超速，请您减速运行");
        }
    };
    public void onViolentEvent(final int value) {
        handler.post(new Runnable() {
            @Override
            public void run() {
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
                // mMsgShow.setText("CarMotion Event: " + event);
                I.d(TAG, event);
            }
        });
    }


    public void initApi(){
        if(mApi==null) {
            mApi = new API(this);
            mApi.setAutoSleepTime(0);
            mApi.registerCarMotionListener(this);
        }
    }
    public byte[] stream2ByteArray(InputStream is) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        int nRead;
        byte[] data = new byte[16384];
        try {
            while ((nRead = is.read(data, 0, data.length)) != -1) {
                baos.write(data, 0, nRead);
            }
            baos.flush();
        } catch (IOException e) {
            I.i(TAG, "stream2ByteArray fail");
        }
        return baos.toByteArray();
    }
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
     * 获取设备所插卡的iccid号码
     *
     * @param
     * @return iccid号码
     */
    @SuppressLint("MissingPermission")
    public String getSimICCID() {
        TelephonyManager tm = (TelephonyManager)getSystemService(Context.TELEPHONY_SERVICE);
        return tm.getSimSerialNumber();
    }
    /**
     * 在内存紧张的时候，系统回收内存时，会回调OnTrimMemory， 重写onTrimMemory当系统清理内存时从新启动Service1
     */
    @Override
    public void onTrimMemory(int level) {
        startService1();
        mApi.unregisterCarMotionListener(this);
    }

    private StrongServiceAidlInterface startS1 = new StrongServiceAidlInterface.Stub(){
        @Override
        public void stopService() throws RemoteException{
            Intent i = new Intent(getBaseContext(), CarBindService.class);
            getBaseContext().stopService(i);
        }
        @Override
        public void basicTypes(int anInt, long aLong, boolean aBoolean, float aFloat, double aDouble, String aString) throws RemoteException {

        }
        @Override
        public void startService() throws RemoteException {
            Intent i = new Intent(getBaseContext(), CarBindService.class);
            getBaseContext().startService(i);

        }
    };




    /**
     * 判断Service1是否还在运行，如果不是则启动Service1
     */
    private void startService1() {
        boolean isRun = Utils.isServiceWork(SocketCarBindService.this,
                "cn.bidostar.ticserver.service.CarBindService");
        if (isRun == false) {
            try {
                startS1.startService();
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
    }



    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return (IBinder) startS1;
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

    /**
     * 抓取当前时间段的视频
     * @param taskId 任务编号
     * @param camera 摄像头 默认0,前后都抓拍, 1表示仅前摄像头, 2表示仅仅后摄像头
     * @param seconds 抓取前后多少秒
     */
    public void takeVideo(final String taskId,final int camera,final int seconds){
        mApi.startCar_WAKEUP();
        mApi.takeVideo(camera, seconds, seconds, new API.TakeCallback() {
            @Override
            public void onTakeProgress(final int progressPrecentage) {
                I.d("taking video: " + progressPrecentage + "%");

            }

            @Override
            public void onTakeResult(final String jsonString) {
                I.d("takd video result: " + jsonString);
                try {
                    JSONTokener tokener = new JSONTokener(jsonString);
                    JSONObject joResult = new JSONObject(tokener);
                    if (joResult.has("videourl")) {
                        if (joResult.getString("videourl").length() > 0) {
                            final String imgPath = joResult.getString("videourl");

                            ServerApiUtils.uploadFile("60", imgPath,taskId,ServerApiUtils.fileUploadCallback);

                        }
                    }

                    if (joResult.has("videourlrear")) {
                        if (joResult.getString("videourlrear").length() > 0) {
                            final String imgPath = joResult.getString("videourlrear");
                            ServerApiUtils.uploadFile("60", imgPath,taskId,ServerApiUtils.fileUploadCallback);
                        }
                    }
                } catch (Exception e) {
                    //ignore this error
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * 拍照
     * @param taskId 任务编号
     * @param camera 默认0,前后都抓拍, 1表示仅前摄像头, 2表示仅仅后摄像头
     */
    public void takePicture(final String taskId,final int camera){
        mApi.takePicture(camera, new API.TakeCallback() {
            @Override
            public void onTakeProgress(final int progressPrecentage) {
                I.d("taking picture: " + progressPrecentage + "%");
            }

            @Override
            public void onTakeResult(final String jsonString) {
                I.d("taked jsonString: " + jsonString );
                try {
                    JSONTokener tokener = new JSONTokener(jsonString);
                    JSONObject joResult = new JSONObject(tokener);
                    if (joResult.has("imgurl")) {
                        if (joResult.getString("imgurl").length() > 0) {
                            final String imgPath = joResult.getString("imgurl");
                            //ServerApiUtils.uploadFile("50", imgPath, ServerApiUtils.fileUploadImgCallback);
                            ServerApiUtils.uploadFile("50", imgPath,taskId,ServerApiUtils.fileUploadCallback);
                        }
                    }

                    if (joResult.has("imgurlrear")) {
                        if (joResult.getString("imgurlrear").length() > 0) {
                            final String imgPath = joResult.getString("imgurlrear");
                            ServerApiUtils.uploadFile("50", imgPath,taskId,ServerApiUtils.fileUploadCallback);
                        }
                    }
                } catch (Exception e) {
                    //ignore this error
                    e.printStackTrace();
                }

            }
        });
    }


    public void checkUpload(){
        /*ReentrantLock lock = new ReentrantLock();
        lock.lock();
        I.d(TAG,"开始检查是否有文件上传");
        if( getUploadQuee()==true){
            I.d(TAG,"有文件上传");
            lock.unlock();
            return;
        }
        setUploadQuee(true);
        uploadDatabase();
        lock.unlock();*/
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
        boolean isWifi = NetworkUtil.isWifi(AppApplication.getInstance().getContext());//如果有网络链接，但是又不是wifi则是用的流量
        LocalFilesModel localFilesModel = new LocalFilesModel();
        localFilesModel.setFlagUpload("0");
        if (getCarUploadMp4Model() == 0) {//任何情况下不上传普通视频
            localFilesModel.setJltype("2");
        }else if(getCarUploadMp4Model() == 1){//wifi模式下上传普通视频
            if(!isWifi){//不是wifi，则只上传重要的视频
                localFilesModel.setJltype("2");
            }else{
                //是wifi的时候就不用筛选了
            }
        }else if(getCarUploadMp4Model() == 2){//wifi和4G模式下上传普通视频
            //不用筛选，全部查找即可
        }else{
            //不用筛选，全部查找即可
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
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

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

        locationManager.requestLocationUpdates(provider, 2000, 10, new LocationListener() {
            public void onLocationChanged(Location location) {
                // TODO Auto-generated method stub
                showLocation(location);
            }

            public void onProviderDisabled(String provider) {
                // TODO Auto-generated method stub
                showLocation(null);
            }

            @SuppressLint("MissingPermission")
            public void onProviderEnabled(String provider) {

                showLocation(locationManager.getLastKnownLocation(provider));
            }

            public void onStatusChanged(String provider, int status, Bundle extras) {
                // TODO Auto-generated method stub
                I.e(TAG,"local GPS error "+provider+" status "+ status);
            }
        });
    }
    public void showLocation(Location currentLocation){
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
            I.e(TAG,"local:"+s);
            //mMsgShow.setText("local result: " + s);
            // text.setText(s);
            String cuSpeed = (currentLocation.getSpeed()*3.6)+"";
            cuSpeed= cuSpeed.substring(0,cuSpeed.indexOf("."));
            int cur = Integer.parseInt(cuSpeed);
            String jd = currentLocation.getAccuracy()+"";
            if(jd.contains(".")){
                jd = jd.substring(0,jd.indexOf("."));
            }

            DecimalFormat sdf = new DecimalFormat(".00000000000");
            AppSharedpreferencesUtils.put(AppConsts.CAR_LOCAL_LAT,sdf.format(currentLocation.getLatitude()));//纬度
            AppSharedpreferencesUtils.put(AppConsts.CAR_LOCAL_LNG,sdf.format(currentLocation.getLongitude()));//经度
            AppSharedpreferencesUtils.put(AppConsts.CAR_LOCAL_PRE,jd);//定位精度
            AppSharedpreferencesUtils.put(AppConsts.CAR_LOCAL_DIRECTION,currentLocation.getBearing()+"");//方向角
            AppSharedpreferencesUtils.put(AppConsts.CAR_LOCAL_SPEED,cur+"");//速度
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
                        mApi.playTts("您已超速，请您减速运行，当前速度" + pubDto.getSpeed() + "KM");
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

            /*
            for(int i=0;i<10;i++) {
                mApi.playTts("您已超速，请您减速运行，当前速度" + pubDto.getSpeed().substring(0, pubDto.getSpeed().indexOf(".")) + "KM");
            }*/

        }
        else{
            // text.setText("");
            I.e(TAG,"local is null");
        }
        //checkUpload();//检查是否有文件上传
    }
}
