package com.bidostar.ticserver.service;

import android.annotation.SuppressLint;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.IBinder;
import android.os.SystemClock;
import android.telephony.TelephonyManager;
import android.widget.Toast;

import com.bidostar.ticserver.CarApplication;
import com.bidostar.ticserver.dao.RequestCommonParamsDtoDao;
import com.bidostar.ticserver.model.RequestCommonParamsDto;
import com.bidostar.ticserver.utils.AppConsts;
import com.bidostar.ticserver.utils.AppSharedpreferencesUtils;
import com.bidostar.ticserver.utils.CarIntents;
import com.bidostar.ticserver.utils.I;
import com.bidostar.ticserver.utils.NetworkUtil;
import com.bidostar.ticserver.utils.ServerApiUtils;
import com.igexin.sdk.PushManager;

import java.text.DecimalFormat;
import java.util.List;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class SocketCarBindService extends Service {
    private String pkgName = "com.bidostar.rmt,com.bidostar.ticserver,cn.bidostar.ticserver";
    public static SocketCarBindService socketCarBindService = null;
    public static RequestCommonParamsDto pubDto;
    int errorNum = 0;
    private static double carSpeed = 120.00; //车辆速度
    public static String CAR_HB_MP4_TASKID = "";//合并视频的任务id
    public static Integer CAR_UP_SPEED_COUNT = 15;//连续超速倒计时  超速之后触发判断是否等于0 等于0让其播报一次
    public static boolean CAR_ON_RUN = false;//车辆是否点火

    private String provider;
    public static int CAR_UPLOAD_MP4_MODEL = 0;//是否在wifi模式下上传普通视频： 0 不上传普通视频 1 wifi上传普通视频 2 wifi/4G均上传视频
    //线程任务队列，用于处理定时上传GPS或是写入本地缓存等任务
    private ScheduledThreadPoolExecutor taskPool = new ScheduledThreadPoolExecutor(3);
    private LocationManager locationManager;
    public LocationListener locationListener = null;
    public SocketCarBindService() {
    }

    public String getCarGPSFlag(){
        //车辆是否运行的标识
        CAR_ON_RUN = (Boolean) AppSharedpreferencesUtils.get(AppConsts.CAR_ON_RUN_FLAG,false);
        //return CAR_ON_RUN ? AppConsts.CAR_RUN_GPS : AppConsts.CAR_OFF_GPS;
        if (!CAR_ON_RUN){
            String zt = AppSharedpreferencesUtils.get(AppConsts.CAR_GOTO_SLEEP,"00").toString();
            CAR_ON_RUN = "00".equals(zt);
        }
        return CAR_ON_RUN ? "10" : "20";
    }

    @SuppressLint("MissingPermission")
    public void initPush(){
        String deviceId = CarApplication.getDeviceIMEI(this);
        if (deviceId == null){
            errorNum++;
            if (errorNum > 3){
                return;
            }
            SystemClock.sleep(3000);
            initPush();
        }else{
            errorNum = 0;
        }
        PushManager.getInstance().initialize(this, AppPushService.class);
        PushManager.getInstance().registerPushIntentService(this, PushIntentService.class);
        boolean isFlag = PushManager.getInstance().bindAlias(this, deviceId);
        if(!PushManager.getInstance().isPushTurnedOn(this.getApplicationContext())) {
            I.e("SocketCarBindService","initPush>>>>>"+deviceId);
            PushManager.getInstance().turnOnPush(this);
        }
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        I.e("SocketCarBindService","onStartCommand>>>>>");
        initPush();
        return START_STICKY;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        if (AppSharedpreferencesUtils.context == null){
            AppSharedpreferencesUtils.init(this);
        }

        socketCarBindService = this;
        setWakeupLockWhiteList(pkgName);
        setAppKeepAlive(pkgName);
        setAppRTC(pkgName);
        //initService();
        I.e("SocketCarBindService","onCreate>>>>>");
    }

    /**
     * 初始化service
     */
    public void initService(){
        initThread();
        initLocal();
        getPubDto();
    }

    /**
     * 初始化执行线程队列。只有当点火状态才执行GPS数据上传
     */
    public void initThread(){
        String zt = AppSharedpreferencesUtils.get(AppConsts.CAR_GOTO_SLEEP,"00").toString();
        //I.e("init GPS ZT:"+zt);
        //如果设备处于休眠状态，则关闭数据上报功能
        if ("10".equals(zt)){
            //如果是休眠状态下在线升级了apk，则不会触发sleep，手工触发一次，保证系统逻辑正常运行
            Intent intent = new Intent(CarIntents.ACTION_GOTOSLEEP);
            sendBroadcast(intent);
            return;
        }
        //initApi();
        if (taskPool == null){
            taskPool = new ScheduledThreadPoolExecutor(3);
        }
        //延迟加载配置信息，避免内存泄露
        taskPool.schedule(new Runnable() {
            @Override
            public void run() {
                carSpeed = Double.parseDouble(AppSharedpreferencesUtils.get(AppConsts.CAR_SPEED_KEY,120.00).toString());//车辆超速设定，默认80
            }
        }, 500, TimeUnit.MILLISECONDS);

        //循环执行GPS上传事件
        taskPool.scheduleWithFixedDelay(postGpsThread, 5, 5, TimeUnit.SECONDS);
        //循环执行GPS补传事件，每5分钟执行一次
        taskPool.scheduleWithFixedDelay(postHistoryGpsThread, 0, 5, TimeUnit.MINUTES);
    }

    /**
     * GPS实时点上传
     */
    Runnable postGpsThread = new Runnable() {
        @Override
        public void run() {
            //上传GPS行驶数据
            RequestCommonParamsDto dto = new RequestCommonParamsDto();
            ServerApiUtils.pushGpsInfo(socketCarBindService, dto, ServerApiUtils.gpsInfoCallback);
        }
    };

    /**
     * GPS补传，每5分钟执行一次
     */
    Runnable postHistoryGpsThread = new Runnable() {
        @Override
        public void run() {
            RequestCommonParamsDtoDao dao = new RequestCommonParamsDtoDao();
            List<RequestCommonParamsDto> lis = dao.findAll();
            if (lis.size() > 0){
                ServerApiUtils.pushGpsInfoByLocalDb(lis,ServerApiUtils.gpsInfoAllCallback);
            }
        }
    };

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

    public double getCarSpeedSetting(){
        // carSpeed = AppSharedpreferencesUtils.get(AppConsts.CAR_SPEED_KEY,40.00)
        carSpeed = Double.parseDouble(AppSharedpreferencesUtils.get(AppConsts.CAR_SPEED_KEY,120.00).toString());
        return carSpeed;
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
                RequestCommonParamsDto dto = new RequestCommonParamsDto();
                dto.setEventType(AppConsts.CAR_CSPEED);
                ServerApiUtils.pushGpsInfo(this, dto,ServerApiUtils.gpsInfoCallback);
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
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        I.e("SocketCarBindService","onDestroy>>>>>");
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

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
