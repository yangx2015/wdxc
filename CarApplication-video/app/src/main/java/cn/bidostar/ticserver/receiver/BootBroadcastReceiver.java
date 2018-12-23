package cn.bidostar.ticserver.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.PowerManager;

import cn.bidostar.ticserver.CarApplication;
import cn.bidostar.ticserver.service.AppPushService;
import cn.bidostar.ticserver.service.PushIntentService;
import cn.bidostar.ticserver.service.SleepIntentService;
import cn.bidostar.ticserver.service.SocketCarBindService;
import cn.bidostar.ticserver.utils.AlarmManagerUtils;
import cn.bidostar.ticserver.utils.AppConsts;
import cn.bidostar.ticserver.utils.AppSharedpreferencesUtils;
import cn.bidostar.ticserver.utils.I;
import cn.bidostar.ticserver.utils.NetworkUtil;
import cn.bidostar.ticserver.utils.ServerApiUtils;
import cn.bidostar.ticserver.utils.Utils;
import com.igexin.sdk.PushManager;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import cn.bidostar.ticserver.utils.AppConsts;
import cn.bidostar.ticserver.utils.AppSharedpreferencesUtils;
import cn.bidostar.ticserver.utils.I;
import cn.bidostar.ticserver.utils.NetworkUtil;


/**
 * Created by admins on 2018/1/26.
 */

public class BootBroadcastReceiver extends BroadcastReceiver {

    private final static String ACTION_SIM_STATE_CHANGED = "android.intent.action.SIM_STATE_CHANGED";
    private final static int SIM_VALID = 0;
    private final static int SIM_INVALID = 1;
    private int simState = SIM_INVALID;

    public void wakeUp(){
        AppSharedpreferencesUtils.put(AppConsts.CAR_GOTO_SLEEP,"00");

        //启动service，调整数据上报时长
        Intent serviceTwo = new Intent();
        serviceTwo.setClass(CarApplication.getApplication().getApplicationContext(), SocketCarBindService.class);
        CarApplication.getApplication().getApplicationContext().startService(serviceTwo);
    }

    public void sleep(){
        AppSharedpreferencesUtils.put(AppConsts.CAR_GOTO_SLEEP,"10");

        Intent serviceTwo = new Intent();
        serviceTwo.setClass(CarApplication.getApplication().getApplicationContext(), SocketCarBindService.class);
        CarApplication.getApplication().getApplicationContext().startService(serviceTwo);
    }
    private Map lockMap = new ConcurrentHashMap();
    //重写onReceive方法
    @Override
    public void onReceive(Context context, Intent intent) {
        //I.e("BootBroadcastReceiver", ">>>>>>>>>>>>>>"+intent.getAction());
        try{
            if(intent.getAction().equals("com.car.wakeup")){//唤醒
                I.e("com.car.wakeup ----");
                wakeUp();
            }else if(intent.getAction().equals("com.car.gotosleep")){//准备休眠
                I.e("com.car.gotosleep ----");
                sleep();
            }else if(intent.getAction().equals("sendHeartPkg")){
                /*I.e("BootBroadcastReceiver", "sendHeartPkg ---->>>>");
                if (NetworkUtil.isConnected(CarApplication.getApplication().getApplicationContext())){
                    CarApplication.getApplication().getApplicationContext().startService(new Intent(CarApplication.getApplication().getApplicationContext(), SleepIntentService.class));
                }*/
                //检查心跳连接是否正常
                boolean isOnline = ServerApiUtils.getInstance().isOnline(CarApplication.getApplication().getDeviceIMEI());
                I.e("BootBroadcastReceiver", "offline ---->>>> start online");
                if (!isOnline){
                    //PushManager.getInstance().initialize(CarApplication.getApplication().getApplicationContext(), AppPushService.class);
                    //PushManager.getInstance().registerPushIntentService(CarApplication.getApplication().getApplicationContext(), PushIntentService.class);
                }
                AlarmManagerUtils.getInstance(CarApplication.getApplication().getApplicationContext()).getUpAlarmManagerWorkOnReceiver();
            }else if(intent.getAction().equals("com.car.alarmwakeup")){
                I.e("BootBroadcastReceiver", "com.car.alarmwakeup ----" + NetworkUtil.isConnected(CarApplication.getApplication().getApplicationContext()));
                if (NetworkUtil.isConnected(CarApplication.getApplication().getApplicationContext())){
                    CarApplication.getApplication().getApplicationContext().startService(new Intent(CarApplication.getApplication().getApplicationContext(), SleepIntentService.class));
                }
            }else if(intent.getAction().equals("android.net.conn.CONNECTIVITY_CHANGE")){
                if (NetworkUtil.isConnected(CarApplication.getApplication().getApplicationContext())){
                    Intent serviceTwo = new Intent();
                    serviceTwo.setClass(CarApplication.getApplication().getApplicationContext(), SocketCarBindService.class);
                    CarApplication.getApplication().getApplicationContext().startService(serviceTwo);
                }
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
        }catch (Exception e){
            I.e("BootBroadcastReceiver", "exception>>>>"+e.getMessage());
        }


        //I.e("BootBroadcastReceiver", "开机自动服务自动启动.....");
        //启动应用，参数为需要自动启动的应用的包名
        //Intent intent1 = context.getPackageManager().getLaunchIntentForPackage("cn.bidostar.ticserver");
        //context.startActivity(intent1);
    }
}
