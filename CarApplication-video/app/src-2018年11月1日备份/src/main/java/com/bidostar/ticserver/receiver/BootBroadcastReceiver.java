package com.bidostar.ticserver.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;

import com.bidostar.ticserver.service.SleepIntentService;
import com.bidostar.ticserver.service.SocketCarBindService;
import com.bidostar.ticserver.utils.AppConsts;
import com.bidostar.ticserver.utils.AppSharedpreferencesUtils;
import com.bidostar.ticserver.utils.I;
import com.bidostar.ticserver.utils.NetworkUtil;
import com.bidostar.ticserver.utils.Utils;
import com.igexin.sdk.PushManager;


/**
 * Created by admins on 2018/1/26.
 */

public class BootBroadcastReceiver extends BroadcastReceiver {

    private final static String ACTION_SIM_STATE_CHANGED = "android.intent.action.SIM_STATE_CHANGED";
    private final static int SIM_VALID = 0;
    private final static int SIM_INVALID = 1;
    private int simState = SIM_INVALID;

    public void wakeUp(Context context){
        AppSharedpreferencesUtils.put(AppConsts.CAR_GOTO_SLEEP,"00");
        boolean isRun = Utils.isServiceWork(context, SocketCarBindService.class.getName());
        if (!isRun){
            //启动service，调整数据上报时长
            Intent serviceTwo = new Intent();
            serviceTwo.setClass(context, SocketCarBindService.class);
            context.startService(serviceTwo);
        }

        /*if (SocketCarBindService.socketCarBindService != null){
            SocketCarBindService.socketCarBindService.initService();
        }*/
    }

    public void sleep(Context context){
        AppSharedpreferencesUtils.put(AppConsts.CAR_GOTO_SLEEP,"10");

        /*if (SocketCarBindService.socketCarBindService != null){
            SocketCarBindService.socketCarBindService.stopWithSleep();
        }*/
    }
    //重写onReceive方法
    @Override
    public void onReceive(Context context, Intent intent) {
        I.e("BootBroadcastReceiver", ">>>>>>>>>>>>>>"+intent.getAction());
        if(intent.getAction().equals("com.car.wakeup")){//唤醒
            I.e("com.car.wakeup ----");
            wakeUp(context);
        }else if(intent.getAction().equals("com.car.gotosleep")){//准备休眠
            I.e("com.car.gotosleep ----");
            sleep(context);
        }else if(intent.getAction().equals("com.car.alarmwakeup")){
            I.e("BootBroadcastReceiver", "com.car.alarmwakeup ----");
            //这就是其它操作
            /*if (NetworkUtil.isConnected(context)){
                context.startService(new Intent(context, SleepIntentService.class));
            }*/
        }else if(intent.getAction().equals("android.net.conn.CONNECTIVITY_CHANGE")){
            /*String zt = AppSharedpreferencesUtils.get(AppConsts.CAR_GOTO_SLEEP,"00").toString();
            if ("10".equals(zt) && !AppApplication.isMain(context)){
                try{
                    AppApplication.initPushServer(context);
                    //这就是其它操作
                    Intent intentMy=new Intent(Intent.ACTION_MAIN);
                    intentMy.addCategory(Intent.CATEGORY_LAUNCHER);
                    ComponentName cn=new ComponentName("cn.bidostar.ticserver",
                            "cn.bidostar.ticserver.TestActivity");
                    intentMy.setComponent(cn);
                    intentMy.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intentMy);
                }catch(Exception e){

                }
            }*/
        }else if (intent.getAction().equals(ACTION_SIM_STATE_CHANGED)) {
            /*TelephonyManager tm = (TelephonyManager)context.getSystemService(Service.TELEPHONY_SERVICE);
            int state = tm.getSimState();
            //I.e("BootBroadcastReceiver", "SIM>>>>>>>>"+state);
            switch (state) {
                case TelephonyManager.SIM_STATE_READY :
                    simState = SIM_VALID;
                    break;
                case TelephonyManager.SIM_STATE_UNKNOWN :
                case TelephonyManager.SIM_STATE_ABSENT :
                case TelephonyManager.SIM_STATE_PIN_REQUIRED :
                case TelephonyManager.SIM_STATE_PUK_REQUIRED :
                case TelephonyManager.SIM_STATE_NETWORK_LOCKED :
                default:
                    simState = SIM_INVALID;
                    break;
            }*/
        }else if (intent.getAction().equals("android.intent.action.BOOT_COMPLETED")) {
            //wakeUp(context);
        }

        //I.e("BootBroadcastReceiver", "开机自动服务自动启动.....");
        //启动应用，参数为需要自动启动的应用的包名
        //Intent intent1 = context.getPackageManager().getLaunchIntentForPackage("cn.bidostar.ticserver");
        //context.startActivity(intent1);
    }
}
