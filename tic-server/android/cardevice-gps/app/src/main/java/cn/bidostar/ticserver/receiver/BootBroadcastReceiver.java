package cn.bidostar.ticserver.receiver;

import android.annotation.SuppressLint;
import android.app.ActivityManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.SystemClock;
import android.telephony.TelephonyManager;
import android.util.Log;

import com.igexin.sdk.PushManager;

import java.util.List;

import cn.bidostar.ticserver.AppApplication;
import cn.bidostar.ticserver.model.RequestCommonParamsDto;
import cn.bidostar.ticserver.service.AppPushService;
import cn.bidostar.ticserver.service.PushIntentService;
import cn.bidostar.ticserver.service.SleepIntentService;
import cn.bidostar.ticserver.service.SocketCarBindService;
import cn.bidostar.ticserver.utils.AlarmManagerUtils;
import cn.bidostar.ticserver.utils.AppConsts;
import cn.bidostar.ticserver.utils.AppSharedpreferencesUtils;
import cn.bidostar.ticserver.utils.I;
import cn.bidostar.ticserver.utils.ServerApiUtils;
import cn.bidostar.ticserver.utils.Utils;

/**
 * Created by admins on 2018/1/26.
 */

public class BootBroadcastReceiver extends BroadcastReceiver {

    //重写onReceive方法
    @Override
    public void onReceive(Context context, Intent intent) {


        if(intent.getAction().equals("com.car.wakeup")){//唤醒
            I.e("com.car.wakeup ----");
            AppSharedpreferencesUtils.put(AppConsts.CAR_GOTO_SLEEP,"00");
            AlarmManagerUtils.getInstance(context).cancelAlarmManager();

            boolean isRun = Utils.isServiceWork(context, SocketCarBindService.class.getName());
            if (!isRun){
                //启动service，调整数据上报时长
                Intent serviceTwo = new Intent();
                serviceTwo.setClass(context, SocketCarBindService.class);
                context.startService(serviceTwo);
            }
        }else if(intent.getAction().equals("com.car.gotosleep")){//准备休眠
            I.e("com.car.gotosleep ----");
            AppSharedpreferencesUtils.put(AppConsts.CAR_GOTO_SLEEP,"10");
            if (SocketCarBindService.socketCarBindService != null){
                SocketCarBindService.socketCarBindService.stopSelf();
            }

            context.stopService(new Intent(context, SocketCarBindService.class));
            AlarmManagerUtils alarmUtil = AlarmManagerUtils.getInstance(context);
            alarmUtil.createGetUpAlarmManager();
            alarmUtil.getUpAlarmManagerStartWork();
        }else if(intent.getAction().equals("sendHeartPkg")){//准备休眠
            I.e("BootBroadcastReceiver", "sendHeartPkg ----");
            context.startService(new Intent(context, SleepIntentService.class));
            AlarmManagerUtils.getInstance(context).getUpAlarmManagerWorkOnReceiver();
        }else if(intent.getAction().equals("com.car.alarmwakeup")){
            //这就是其它操作
            I.e("BootBroadcastReceiver", "com.car.alarmwakeup ----");
            context.startService(new Intent(context, SleepIntentService.class));
        }else if(intent.getAction().equals("android.net.conn.CONNECTIVITY_CHANGE") || intent.getAction().equals("android.intent.action.BOOT_COMPLETED")
                || intent.getAction().equals("android.intent.action.PACKAGE_ADDED") || intent.getAction().equals("android.intent.action.ACTION_PACKAGE_REPLACED")){
            //这就是其它操作
            //AppApplication.getInstance().initPushServer();
        }else{

        }

        //I.e("BootBroadcastReceiver", "开机自动服务自动启动.....");
        //启动应用，参数为需要自动启动的应用的包名
        //Intent intent1 = context.getPackageManager().getLaunchIntentForPackage("cn.bidostar.ticserver");
        //context.startActivity(intent1);
    }
}
