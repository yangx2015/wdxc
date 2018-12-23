package cn.bidostar.ticserver;

import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.app.Application;
import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Looper;
import android.os.PowerManager;
import android.telephony.TelephonyManager;
import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.igexin.sdk.PushManager;

import cn.bidostar.ticserver.model.RequestCommonParamsDto;
import cn.bidostar.ticserver.service.AppPushService;
import cn.bidostar.ticserver.service.JobSchedulerService;
import cn.bidostar.ticserver.service.PushIntentService;
import cn.bidostar.ticserver.service.SocketCarBindService;
import cn.bidostar.ticserver.utils.AppConsts;
import cn.bidostar.ticserver.utils.AppSharedpreferencesUtils;
import cn.bidostar.ticserver.utils.CrashHandler;
import cn.bidostar.ticserver.utils.I;

import org.xutils.x;

import java.util.HashMap;
import java.util.Map;

import cn.bidostar.ticserver.model.RequestCommonParamsDto;
import cn.bidostar.ticserver.utils.AppSharedpreferencesUtils;
import cn.bidostar.ticserver.utils.I;

public class CarApplication extends Application {

    private static CarApplication sInstance;

    public RequestCommonParamsDto pubDto = null;
    public String versionCodeStr = null;
    public String imei = null;
    public String simCCID = null;
    public String channelId = null;
    //是否开启休眠下网络连接
    public boolean isConnectNetwork = false;
    //休眠多少时间后开启网络连接。默认6个小时
    public int connectTimeout = 40;

    @Override
    public void onCreate() {
        super.onCreate();
        sInstance = this;
        AppSharedpreferencesUtils.init(this);
        x.Ext.init(this);
        x.Ext.setDebug(false);
        CrashHandler.getInstance().init(this); // 一定要先初始化
        Thread.setDefaultUncaughtExceptionHandler(CrashHandler.getInstance());

        //初始化APP
        PowerManager powerManager = (PowerManager) this.getSystemService(Context.POWER_SERVICE);
        boolean ifOpen = powerManager.isScreenOn();
        if (ifOpen){
            //屏幕处于点亮状态，设备就不处于休眠状态
            AppSharedpreferencesUtils.put(AppConsts.CAR_GOTO_SLEEP,"00");
        }
        initSocket();
        I.e("CarApplication","onCreate>>>>>");
    }

    public void initSocket(){
        Intent serviceTwo = new Intent();
        serviceTwo.setClass(this, SocketCarBindService.class);
        startService(serviceTwo);
    }

    public String getServerUrlBase(){
        return AppSharedpreferencesUtils.get(AppConsts.CAR_BASE_SERVER_URL,AppConsts.BASE_URL).toString();
    }

    public static CarApplication getApplication() {
        return sInstance;
    }

    public String getChannelId() {
        if (channelId == null || "".equals(channelId)){
            channelId = PushManager.getInstance().getClientid(getApplicationContext());
        }

        return channelId;
    }

    /**
     * 获取设备的IMEI唯一码
     * @return
     */
    @SuppressLint("MissingPermission")
    public String getDeviceIMEI() {
        if (imei == null || "".equals(imei)){
            TelephonyManager tm = (TelephonyManager)getSystemService(Context.TELEPHONY_SERVICE);
            imei = tm.getDeviceId();
        }

        return imei;
    }

    /**
     * 获取设备所插卡的iccid号码
     *
     * @param
     * @return iccid号码
     */
    @SuppressLint("MissingPermission")
    public String getSimICCID() {
        if (simCCID == null || "".equals(simCCID)){
            TelephonyManager tm = (TelephonyManager)getSystemService(Context.TELEPHONY_SERVICE);
            simCCID = tm.getSimSerialNumber();
        }

        return simCCID;
    }

    public void setCarUploadMp4Model(int f){
        AppSharedpreferencesUtils.put(AppConsts.CAR_UPLOAD_MP4_MODEL, f);
    }

    public void setCarSpeed(double speed){
        if(speed<20){
            speed = 20.00;
        }
        double carSpeed = speed;
        AppSharedpreferencesUtils.put(AppConsts.CAR_SPEED_KEY, carSpeed);
    }

    public String getAPPVersionCode() {
        int currentVersionCode = 0;
        if (versionCodeStr == null || "".equals(versionCodeStr)){
            PackageManager manager = getPackageManager();
            Map<String,Object> vmap = new HashMap<>();
            try {
                PackageInfo info = manager.getPackageInfo(getPackageName(), PackageManager.GET_SIGNATURES);
                String appVersionName = info.versionName; // 版本名
                currentVersionCode = info.versionCode; // 版本号
                vmap.put("versionName",appVersionName);
                vmap.put("versionCode",currentVersionCode);

                versionCodeStr = JSON.toJSONString(vmap);
            } catch (PackageManager.NameNotFoundException e) {
                e.printStackTrace();
            }
        }

        return versionCodeStr;
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

    public String getCarGPSFlag(){
        //车辆是否运行的标识
        boolean CAR_ON_RUN = false;
        String zt = AppSharedpreferencesUtils.get(AppConsts.CAR_GOTO_SLEEP,"00").toString();
        CAR_ON_RUN = "00".equals(zt);

        return CAR_ON_RUN ? "10" : "20";
    }

    PowerManager pm = null;
    PowerManager.WakeLock wakeLock = null;
    public void lockWakeup(){
        pm = (PowerManager)getSystemService(Context.POWER_SERVICE);
        wakeLock = pm.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK, "cn.bidostar.ticserver");

        if(!wakeLock.isHeld()){
            //持续加锁1个小时。大概一个20M视频，网络正常时，上传约20秒。1个小时正常情况可以上传3个小时的视频
            long timeout = 1000 * 60 * 60 * 1;
            wakeLock.acquire(timeout);
            I.e("CarApp", "wakeup lock");
        }
    }

    public void releaseWakeup(){
        if(wakeLock != null && wakeLock.isHeld()){
            I.e("CarApp","is lock release");
            wakeLock.release();
            wakeLock = null;
        }
    }

    public void initPush(){
        PushManager.getInstance().stopService(this);
        PushManager.getInstance().initialize(getApplicationContext(), AppPushService.class);
        PushManager.getInstance().registerPushIntentService(getApplicationContext(), PushIntentService.class);
        //00:00-次日5:00这5个小时内将不会联网
        PushManager.getInstance().setSilentTime(this, 0, 5);
    }

    /**
     * 开启定时任务。12小时执行一次
     */
    public void startJobService(){
        try{
            //循环定时执行，12小时执行一次。最新运行时间是15分钟，不能低于15分钟
            //当网络关闭时，开启任务，12个小时后执行一次唤醒操作
            JobScheduler scheduler = (JobScheduler) getSystemService(Context.JOB_SCHEDULER_SERVICE);
            JobInfo jobInfo = new JobInfo.Builder(0, new ComponentName(this, JobSchedulerService.class))
                    .setPeriodic(AlarmManager.INTERVAL_HALF_DAY)
                    .setRequiredNetworkType(JobInfo.NETWORK_TYPE_NONE)//不管是否有网络这个任务都会被执行
                    .build();

            if (scheduler != null) {
                scheduler.schedule(jobInfo);
            }
        }catch (Exception e){

        }
    }

    /**
     * 关闭定时任务
     */
    public void stopJobService(){
        //关闭定时任务
        JobScheduler scheduler = (JobScheduler) getSystemService(Context.JOB_SCHEDULER_SERVICE);
        scheduler.cancelAll();
    }
}
