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

import cn.bidostar.ticserver.dao.LocalFilesModelDao;
import cn.bidostar.ticserver.handler.CrashHandler;
import cn.bidostar.ticserver.model.LocalFilesModel;
import cn.bidostar.ticserver.model.RequestCommonParamsDto;
import cn.bidostar.ticserver.service.AppPushService;
import cn.bidostar.ticserver.service.PushIntentService;
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

        if(inMainProcess()) {

            pm = (PowerManager)getSystemService(Context.POWER_SERVICE);
            wakeLock = pm.newWakeLock(PowerManager.SCREEN_BRIGHT_WAKE_LOCK
                    | PowerManager.ON_AFTER_RELEASE, TAG);
        if(wakeLock.isHeld()){
            I.i(TAG,"已经存在休眠锁，不需要再次进行获取");
        }else{
            wakeLock.acquire();
        }

      }
        versionStr = getAPPVersionCode(this);


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
