package cn.bidostar.ticserver;

import android.*;
import android.annotation.SuppressLint;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.PowerManager;
import android.support.multidex.MultiDex;
import android.support.v4.app.ActivityCompat;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.View;
import android.widget.Toast;


import com.alibaba.fastjson.JSON;
import com.igexin.sdk.PushManager;
import com.miramems.carmotion.carMotion;

import org.json.JSONObject;
import org.json.JSONTokener;
import org.xutils.x;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import cn.bidostar.ticserver.dao.LocalFilesModelDao;
import cn.bidostar.ticserver.dao.RequestCommonParamsDtoDao;
import cn.bidostar.ticserver.handler.CrashHandler;
import cn.bidostar.ticserver.model.LocalFilesModel;
import cn.bidostar.ticserver.model.RequestCommonParamsDto;
import cn.bidostar.ticserver.service.API;
import cn.bidostar.ticserver.service.AppPushService;
import cn.bidostar.ticserver.service.PushIntentService;
import cn.bidostar.ticserver.utils.AppConsts;
import cn.bidostar.ticserver.utils.AppSharedpreferencesUtils;
import cn.bidostar.ticserver.utils.I;
import cn.bidostar.ticserver.utils.NetworkUtil;
import cn.bidostar.ticserver.utils.ServerApiUtils;
import cn.bidostar.ticserver.utils.TimeUtils;
import cn.bidostar.ticserver.utils.Utils;

/**
 * Created by admins on 2018/2/5.
 */

public class AppApplication extends Application  implements API.CarMotionListener {
    private static AppApplication sInstance;
    public static boolean UPLOAD_QUEE = false;
    public static final String TAG = "cn.bidostar.ticserver.AppApplication";
    public static Context getContext() {
        return sInstance.getApplicationContext();
    }
    private Class userPushService = AppPushService.class;
    private LocationManager locationManager;
    public static String APP_IMEI = "";
    public static String APP_SIMCID = "";
    public static API mApi;
    private static int timerRun = 10000;//GPS上传时间间隔
    private static double carSpeed = 40.00; //车辆速度
    public static String CAR_HB_MP4_TASKID = "";//合并视频的任务id
    public static Integer CAR_UP_SPEED_COUNT = 30;//连续超速倒计时  超速之后触发判断是否等于0 等于0让其播报一次
    public static boolean CAR_ON_RUN = false;//车辆是否点火

    public static int WIFIGPS = 1;//1 GPS  2 wifi

    public static int CAR_UPLOAD_MP4_MODEL = 1;//是否在wifi模式下上传普通视频： 0 不上传普通视频 1 wifi上传普通视频 2 wifi/4G均上传视频

    public static RequestCommonParamsDto pubDto;
    PowerManager pm = null;
    PowerManager.WakeLock wakeLock = null;

    Handler mHandler = new Handler();
    Runnable timeRunExc = new Runnable() {

        @Override
        public void run() {
            //do something
            //上传GPS行驶数据
            RequestCommonParamsDto dto = new RequestCommonParamsDto();

            ServerApiUtils.pushGpsInfo(dto, ServerApiUtils.gpsInfoCallback);

            //每隔timerRun 循环执行run方法
            initPushServer();
            mHandler.postDelayed(this, timerRun);
            checkUpload();
            //mApi.playTts("您已超速，请您减速运行");
        }
    };

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
        AppApplication.getInstance().CAR_UPLOAD_MP4_MODEL = f;
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
        carSpeed = Double.parseDouble(AppSharedpreferencesUtils.get(AppConsts.CAR_SPEED_KEY,40.00).toString());
        return carSpeed;
    }

    private String provider;
    public AppApplication() {
        sInstance = this;
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

    public void uploadGps(){
        I.e(TAG,"网络状态变化上传一次");
        RequestCommonParamsDto dto = new RequestCommonParamsDto();
        ServerApiUtils.pushGpsInfo(dto, ServerApiUtils.gpsInfoCallback);
    }
    /**
     * 是否主线程
     *
     * @return
     */
    public boolean inMainProcess() {
        String packageName = getPackageName();
        return Utils.isProessRunning(this,packageName);
    }
    public static synchronized AppApplication getInstance() {
        if (sInstance == null) {
            sInstance = new AppApplication();
        }
        return sInstance;
    }

    public void setUploadQuee(boolean f){
        AppSharedpreferencesUtils.put(AppConsts.CAR_UPLOAD_QUEE,f);
        AppApplication.getInstance().UPLOAD_QUEE = f;
    }

    public boolean getUploadQuee(){
        AppApplication.getInstance().UPLOAD_QUEE = (boolean)AppSharedpreferencesUtils.get(AppConsts.CAR_UPLOAD_QUEE,false);
        return  AppApplication.getInstance().UPLOAD_QUEE;
    }


    public String getCarGPSFlag(){
        //车辆是否运行的标识
        CAR_ON_RUN = (Boolean) AppSharedpreferencesUtils.get(AppConsts.CAR_ON_RUN_FLAG,false);
        //return CAR_ON_RUN ? AppConsts.CAR_RUN_GPS : AppConsts.CAR_OFF_GPS;
        return CAR_ON_RUN ? "10" : "20";
    }
    @Override
    public void onCreate() {
        super.onCreate();
        CrashHandler.getInstance().init(this); // 一定要先初始化
        Thread.setDefaultUncaughtExceptionHandler(CrashHandler.getInstance());
        //UserCache.setContext(this);
        //HttpURIUtils.isDedebug = false;
        sInstance = this;
        x.Ext.init(this);
        x.Ext.setDebug(false);
        AppSharedpreferencesUtils.init(this);
        APP_IMEI = getDeviceIMEI();
        APP_SIMCID = getSimICCID();
            initPushServer();
            initApi();
            initLocal();//初始化定位
        timerRun = (Integer)AppSharedpreferencesUtils.get(AppConsts.UPLOAD_GPS_TIMER,10000);//默认20秒更新上传一次的心跳
        carSpeed = Double.parseDouble(AppSharedpreferencesUtils.get(AppConsts.CAR_SPEED_KEY,40.00).toString());//车辆超速设定，默认80

        //AppBusService.getInstance().initBusService();
        //5秒后执行 每5秒执行
         mHandler.postDelayed(timeRunExc, 5000);
        AppApplication.getInstance().CAR_UPLOAD_MP4_MODEL = (Integer)AppSharedpreferencesUtils.get(AppConsts.CAR_UPLOAD_MP4_MODEL,1);

        if(NetworkUtil.isConnected(this) && NetworkUtil.isWifi(this)){
            AppApplication.WIFIGPS = 2;//设置为wifi链接状态
        };
        getPubDto();
        //if(inMainProcess()) {
            AppApplication.getInstance().mApi.setWakeupLockWhiteList(getPackageName());
            pm = (PowerManager)getSystemService(Context.POWER_SERVICE);
            wakeLock = pm.newWakeLock(PowerManager.SCREEN_BRIGHT_WAKE_LOCK
                    | PowerManager.ON_AFTER_RELEASE, TAG);
        if(wakeLock.isHeld()){
            I.i(TAG,"已经存在休眠锁，不需要再次进行获取");
        }else{
            wakeLock.acquire();
        }
      //  }



    }

    public int getCarUploadMp4Model(){
        AppApplication.getInstance().CAR_UPLOAD_MP4_MODEL = (Integer)AppSharedpreferencesUtils.get(AppConsts.CAR_UPLOAD_MP4_MODEL,1);
        return  AppApplication.getInstance().CAR_UPLOAD_MP4_MODEL;
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


    public void initApi(){
        if(mApi==null) {
            mApi = new API(this);
            mApi.setAutoSleepTime(0);
            mApi.registerCarMotionListener(this);
        }
    }




    public void initPushServer(){
        PushManager.getInstance().initialize(this.getApplicationContext(), userPushService);
        PushManager.getInstance().registerPushIntentService(this.getApplicationContext(), PushIntentService.class);
        PushManager.getInstance().bindAlias(this.getApplicationContext(), getDeviceIMEI());
        if(!PushManager.getInstance().isPushTurnedOn(this.getApplicationContext())) {
            PushManager.getInstance().turnOnPush(this.getApplicationContext());
        }
        //BasicPushNotificationBuilder bBuilder = new BasicPushNotificationBuilder();
        //bBuilder.setChannelId(getDeviceIMEI());
        //bBuilder.setChannelName(getDeviceIMEI()+"@"+getSimICCID());
        //PushManager.setDefaultNotificationBuilder(this, bBuilder);
       /* List<String> ls = new ArrayList<>();
        ls.add(getDeviceIMEI());
        PushManager.setTags(this,ls);*/
        I.e("initPushServer-------");
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

    public Bitmap getMeIconBitmap() {
        InputStream is;
        Bitmap bmpMeIcon = null;
        try {
            is = getAssets().open("ufo_res/ufo_defult_me_icon.png");
            byte[] bs = this.stream2ByteArray(is);
            bmpMeIcon = BitmapFactory.decodeByteArray(bs, 0, bs.length, null);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return bmpMeIcon;
    }

    /**
     * 获取设备序列号（sn）
     *
     * @param
     * @return 设备序列号
     */
    public String getDeviceCPUID() {
        return Build.SERIAL;
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
    @Override
    public void onTerminate(){
        mApi.unregisterCarMotionListener(this);
        super.onTerminate();
    }

    public void onViolentEvent(final int value) {
        mHandler.post(new Runnable() {
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

    /**
     * 抓取当前时间段的视频
     * @param taskId 任务编号
     * @param camera 摄像头 默认0,前后都抓拍, 1表示仅前摄像头, 2表示仅仅后摄像头
     * @param seconds 抓取前后多少秒
     */
    public void takeVideo(final String taskId,final int camera,final int seconds){
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
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            I.e(TAG, "local is no permission");
            return;
        }
        locationManager.requestLocationUpdates(provider, 1000, 10, new LocationListener() {
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
                        CAR_UP_SPEED_COUNT = 30;
                    }
                }catch (Exception e){
                }
            }

            if(AppApplication.getInstance().getGpsNoNetWorkFlag()){
                RequestCommonParamsDtoDao dao = new RequestCommonParamsDtoDao();
                AppApplication.getInstance().setGpsNoNetWorkFlag("00");//需要进行检测上传了
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
        checkUpload();//检查是否有文件上传
    }

    public synchronized void checkUpload(){
        if( AppApplication.getInstance().getUploadQuee()==true){
            return;
        }
        uploadDatabase();
        AppApplication.getInstance().setUploadQuee(true);
    }


    /**
     * 上传数据库中的文件，切换到wifi的时候
     */
    public void uploadDatabase(){
        boolean checkNetwork = NetworkUtil.isConnected(AppApplication.getInstance().getContext());

        if(!checkNetwork){
            I.i(TAG,"uploadDatabase Start 没有网络");
            //AppApplication.getInstance().UPLOAD_QUEE = false;
            AppApplication.getInstance().setUploadQuee(false);
            return ;
        }
        boolean isWifi = NetworkUtil.isWifi(AppApplication.getInstance().getContext());//如果有网络链接，但是又不是wifi则是用的流量
        LocalFilesModel localFilesModel = new LocalFilesModel();
        localFilesModel.setFlagUpload("0");
        if (AppApplication.getInstance().getCarUploadMp4Model() == 0) {//任何情况下不上传普通视频
            localFilesModel.setJltype("2");
        }else if(AppApplication.getInstance().getCarUploadMp4Model() == 1){//wifi模式下上传普通视频
            if(!isWifi){//不是wifi，则只上传重要的视频
                localFilesModel.setJltype("2");
            }else{
                //是wifi的时候就不用筛选了
            }
        }else if(AppApplication.getInstance().getCarUploadMp4Model() == 2){//wifi和4G模式下上传普通视频
            //不用筛选，全部查找即可
        }else{
            //不用筛选，全部查找即可
        }

        LocalFilesModelDao dao = new LocalFilesModelDao();
        List<LocalFilesModel> fileList = dao.findListByFlagupload(localFilesModel);

        if(checkNetwork && fileList!=null && !fileList.isEmpty() && fileList.size()>0) {

            LocalFilesModel s = fileList.get(0);
            I.d(TAG,"updateFile Start 有网络，有文件，开始上传文件"+s.getLocalPath());
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
            b = ServerApiUtils.uploadFileLoop("102", s.getLocalPath(), ServerApiUtils.fileUploadLoopCallback);
            if (checkNetwork && b == false) {//有网络的时候返回false 多半是文件不存在，删除文件
                dao.delete(s);
            }
        }else{
            I.d(TAG,"updateFile Start 没有网络或者没有文件");
            AppApplication.getInstance().setUploadQuee(false);
        }
    }
}
