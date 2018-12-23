package com.bidostar.ticserver;

import android.annotation.SuppressLint;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.PowerManager;
import android.telephony.TelephonyManager;

import com.alibaba.fastjson.JSON;
import com.bidostar.ticserver.service.SocketCarBindService;
import com.bidostar.ticserver.utils.AppConsts;
import com.bidostar.ticserver.utils.AppSharedpreferencesUtils;
import com.bidostar.ticserver.utils.I;

import org.xutils.x;

import java.util.HashMap;
import java.util.Map;

public class CarApplication extends Application {

    public static String versionStr = "";

    @Override
    public void onCreate() {
        super.onCreate();

        /*try{
            AppSharedpreferencesUtils.init(this);
            x.Ext.init(this);
            x.Ext.setDebug(false);

            PowerManager powerManager = (PowerManager) this.getSystemService(Context.POWER_SERVICE);
            boolean ifOpen = powerManager.isScreenOn();
            if (ifOpen){
                //屏幕处于点亮状态，设备就不处于休眠状态
                AppSharedpreferencesUtils.put(AppConsts.CAR_GOTO_SLEEP,"00");
            }
        }catch(Exception e){

        }*/

        initSocket();
        I.e("CarApplication","onCreate>>>>>");
    }

    public void initSocket(){
        Intent serviceTwo = new Intent();
        serviceTwo.setClass(this, SocketCarBindService.class);
        startService(serviceTwo);
    }

    /**
     * 获取设备的IMEI唯一码
     * @param context
     * @return
     */
    @SuppressLint("MissingPermission")
    public static String getDeviceIMEI(Context context) {
        TelephonyManager tm = (TelephonyManager)context.getSystemService(Context.TELEPHONY_SERVICE);
        return tm.getDeviceId();
    }

    /**
     * 获取设备所插卡的iccid号码
     *
     * @param
     * @return iccid号码
     */
    @SuppressLint("MissingPermission")
    public static String getSimICCID(Context context) {
        TelephonyManager tm = (TelephonyManager)context.getSystemService(Context.TELEPHONY_SERVICE);
        return tm.getSimSerialNumber();
    }

    public static String getVersionStr(Context ctx){
        if(versionStr == null || versionStr.isEmpty()){
            versionStr = getAPPVersionCode(ctx);
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
    }
}
