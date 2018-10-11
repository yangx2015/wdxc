package cn.bidostar.ticserver;

import android.annotation.SuppressLint;
import android.app.Application;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.PowerManager;
import android.support.multidex.MultiDex;
import android.telephony.TelephonyManager;

import com.alibaba.fastjson.JSON;
import com.igexin.sdk.PushManager;

import org.json.JSONArray;
import org.xutils.x;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import cn.bidostar.ticserver.dao.LocalFilesModelDao;
import cn.bidostar.ticserver.handler.CrashHandler;
import cn.bidostar.ticserver.model.LocalFilesModel;
import cn.bidostar.ticserver.model.RequestCommonParamsDto;
import cn.bidostar.ticserver.service.AppPushService;
import cn.bidostar.ticserver.service.PushIntentService;
import cn.bidostar.ticserver.service.SocketCarBindService;
import cn.bidostar.ticserver.utils.AppConsts;
import cn.bidostar.ticserver.utils.AppSharedpreferencesUtils;
import cn.bidostar.ticserver.utils.I;
import cn.bidostar.ticserver.utils.ServerApiUtils;
import cn.bidostar.ticserver.utils.Utils;

/**
 * Created by admins on 2018/2/5.
 */

public class AppApplication extends Application  {
    private static AppApplication sInstance;
    public static boolean UPLOAD_QUEE = false;
    public static final String TAG = "cn.bidostar.ticserver.AppApplication";
    public static Context getContext() {
        return sInstance.getApplicationContext();
    }
    private Class userPushService = AppPushService.class;
    public static String versionStr = "";
    public static String APP_IMEI = "";
    public static String APP_SIMCID = "";
    PowerManager pm = null;
    PowerManager.WakeLock wakeLock = null;



    public String getServerUrlBase(){
        return AppSharedpreferencesUtils.get(AppConsts.CAR_BASE_SERVER_URL,AppConsts.BASE_URL).toString();
    }

    public static void setCarUploadMp4Model(int f){

        I.d(TAG,"设置文件上传模式："+f);
        AppSharedpreferencesUtils.put(AppConsts.CAR_UPLOAD_MP4_MODEL,f);
    }

    public static void setCarSpeed(double speed){
        if(speed<20){
            speed = 20.00;
        }

        AppSharedpreferencesUtils.put(AppConsts.CAR_SPEED_KEY,speed);
    }


    public AppApplication() {
        sInstance = this;
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

    public boolean uploadGps(){
        RequestCommonParamsDto dto = new RequestCommonParamsDto();
        return ServerApiUtils.pushGpsInfo(dto, ServerApiUtils.gpsInfoCallback);
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
        I.d(TAG,"文件上传设置队列状态"+AppApplication.getInstance().UPLOAD_QUEE);
    }

    public boolean getUploadQuee(){
        AppApplication.getInstance().UPLOAD_QUEE = (boolean)AppSharedpreferencesUtils.get(AppConsts.CAR_UPLOAD_QUEE,false);
        I.d(TAG,"文件上传队列状态"+AppApplication.getInstance().UPLOAD_QUEE);
        return  (boolean)AppSharedpreferencesUtils.get(AppConsts.CAR_UPLOAD_QUEE,false);
    }


    @Override
    public void onCreate() {
        super.onCreate();
        sInstance = AppApplication.this;
        APP_IMEI = getDeviceIMEI();
        APP_SIMCID = getSimICCID();
        initPushServer();
        CrashHandler.getInstance().init(AppApplication.this); // 一定要先初始化
        Thread.setDefaultUncaughtExceptionHandler(CrashHandler.getInstance());

        x.Ext.init(AppApplication.this);
        x.Ext.setDebug(false);
        AppSharedpreferencesUtils.init(AppApplication.this);
        //启动锁
        //acquire();

        versionStr = getAPPVersionCode(this);
    }
    ScheduledThreadPoolExecutor taskPool = null;
    public void sleepGpsSend(){
        //进入休眠模式
        AppSharedpreferencesUtils.put(AppConsts.CAR_GOTO_SLEEP,"10");
        //启动service，调整数据上报时长
        if (SocketCarBindService.socketCarBindService != null){
            SocketCarBindService.socketCarBindService.stopWithSleep();
        }

        if (taskPool == null){
            taskPool = new ScheduledThreadPoolExecutor(1);
            //5分钟执行一次
            taskPool.scheduleAtFixedRate(new Runnable() {
                @Override
                public void run() {
                    try{
                        AppApplication.getInstance().initPushServer();
                        //启动锁
                        AppApplication.getInstance().acquire();
                        if (SocketCarBindService.socketCarBindService != null && SocketCarBindService.socketCarBindService.mApi != null){
                            //I.e(TAG,"setMobileEnabled:");
                            SocketCarBindService.socketCarBindService.mApi.setMobileEnabled(true);
                        }

                        //执行上传GPS和个推消息连接，保证网络一直有数据传输，防止系统自动断网
                        boolean success = AppApplication.getInstance().uploadGps();
                        //I.e(TAG,"uploadGps:"+success);
                        /*if (success){
                            AppApplication.getInstance().unacquire();
                        }*/
                    }catch (Exception e){
                        I.e(TAG,"run exception not:"+e.getMessage());
                    }
                }
            }, 0, 60 * 3, TimeUnit.SECONDS);
        }
    }

    public void stopSleepGpsSend(){
        I.e("com.car.wakeup ---- stopSleepGpsSend:"+taskPool);
        if (taskPool != null){
            taskPool.shutdownNow();
        }

        taskPool = null;
        AppSharedpreferencesUtils.put(AppConsts.CAR_GOTO_SLEEP,"00");
        AppApplication.getInstance().unacquire();
    }

    public void acquire(){
        if (wakeLock == null){
            pm = (PowerManager)getSystemService(Context.POWER_SERVICE);
            wakeLock = pm.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK, TAG);
        }

        if(wakeLock.isHeld()){
            //I.e(TAG,"is lock");
        }else{
            //I.e("AppApplication", "wakeLock acquire success======================"+wakeLock.isHeld());
            long timeout = 1000 * 60 * 5;
            wakeLock.acquire(timeout);
            //I.e("AppApplication", "wakeLock acquire lock time:"+timeout+"======================"+wakeLock.isHeld());
        }
    }

    public void unacquire(){
        if(wakeLock != null && wakeLock.isHeld()){
            //I.e(TAG,"is lock release");
            wakeLock.release();
            wakeLock = null;
        }
    }

    public static String getVersionStr(){
        if(versionStr == null || versionStr.isEmpty()){
            versionStr = getAPPVersionCode(AppApplication.getInstance().getApplicationContext());
        }
        return versionStr;
    }

    public static String getAPPVersionCode(Context ctx) {
        int currentVersionCode = 0;
        PackageManager manager = ctx.getPackageManager();
        Map<String,Object> vmap = new HashMap<>();
        try {
            PackageInfo info = manager.getPackageInfo(ctx.getPackageName(), 0);
            String appVersionName = info.versionName; // 版本名
            currentVersionCode = info.versionCode; // 版本号
            System.out.println(currentVersionCode + " " + appVersionName);
            vmap.put("versionName",appVersionName);
            vmap.put("versionCode",currentVersionCode);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return JSON.toJSONString(vmap);
       // return currentVersionCode;
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



}
