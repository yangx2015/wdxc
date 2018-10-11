package cn.bidostar.ticserver.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.PowerManager;
import android.util.Log;

import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import cn.bidostar.ticserver.AppApplication;
import cn.bidostar.ticserver.service.CarBindService;
import cn.bidostar.ticserver.service.SocketCarBindService;
import cn.bidostar.ticserver.utils.AppConsts;
import cn.bidostar.ticserver.utils.AppSharedpreferencesUtils;
import cn.bidostar.ticserver.utils.I;

/**
 * Created by admins on 2018/1/26.
 */

public class BootBroadcastReceiver extends BroadcastReceiver {

    PowerManager pm = null;
    PowerManager.WakeLock wakeLock = null;
    public static final String TAG = "cn.bidostar.ticserver.service.SocketCarBindService";

    static ScheduledThreadPoolExecutor taskPool = null;
    //重写onReceive方法
    @Override
    public void onReceive(Context context, Intent intent) {
        //后边的XXX.class就是要启动的服务   启动第一个service
        /*Intent service = new Intent(context,CarBindService.class);
        context.startService(service);
        Intent serviceTwo = new Intent();
        serviceTwo.setClass(context, SocketCarBindService.class);
        context.startService(serviceTwo);

        */

        if(intent.getAction().equals("com.car.wakeup")){//唤醒
            I.e("com.car.wakeup ---- taskPool:");
            AppApplication.getInstance().stopSleepGpsSend();
            //启动service，调整数据上报时长
            if (SocketCarBindService.socketCarBindService != null){
                SocketCarBindService.socketCarBindService.onCreate();
            }
        }else if(intent.getAction().equals("com.car.gotosleep")){//准备休眠
            I.e("com.car.gotosleep ----");
            AppApplication.getInstance().sleepGpsSend();

                /*Intent intentService = new Intent(context, SocketCarBindService.class);
                //context.startService(intentService);
                context.stopService(intentService);*/
            /*taskPool.shutdownNow();
            taskPool = new ScheduledThreadPoolExecutor(1);
            final Context runnContext = context;
            //1分钟执行一次
            taskPool.scheduleWithFixedDelay(new Runnable() {
                @Override
                public void run() {
                    //I.e(TAG,"uploadGps scheduleWithFixedDelay");
                    AppApplication.getInstance().initPushServer();
                    //执行上传GPS和个推消息连接，保证网络一直有数据传输，防止系统自动断网
                    boolean success = AppApplication.getInstance().uploadGps();
                    if (success){
                        try{
                            pm = (PowerManager)runnContext.getSystemService(Context.POWER_SERVICE);
                            wakeLock = pm.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK, TAG);
                            if(wakeLock.isHeld()){
                                I.e(TAG,"uploadGps success and release lock");
                                wakeLock.release();
                                wakeLock = null;
                            }
                        }catch(Exception e){

                        }
                    }
                }
            }, 5, 60, TimeUnit.SECONDS);*/
            //2018年10月8日。网络休眠后进行抓锁处理，否则网络将无法正常使用
            /*pm = (PowerManager)context.getSystemService(Context.POWER_SERVICE);
            wakeLock = pm.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK, TAG);
            if(wakeLock.isHeld()){
                //I.i("BootBroadcastReceiver","wakeLock is lock");
            }else{
                //I.e("BootBroadcastReceiver", "wakeLock acquire success======================");
                //抓锁最长时间为10分钟
                wakeLock.acquire(1000 * 60 * 1);
                //关闭GPS监听标记
                AppSharedpreferencesUtils.put(AppConsts.CAR_GOTO_SLEEP,"10");

                //I.e("BootBroadcastReceiver", " GPS Manager:"+SocketCarBindService.locationManager);
            }*/
        }else if(intent.getAction().equals("android.net.conn.CONNECTIVITY_CHANGE")){
            I.e("BootBroadcastReceiver", "CONNECTIVITY_CHANGE");
        }else{

        }

        //I.e("BootBroadcastReceiver", "开机自动服务自动启动.....");
        //启动应用，参数为需要自动启动的应用的包名
        //Intent intent1 = context.getPackageManager().getLaunchIntentForPackage("cn.bidostar.ticserver");
        //context.startActivity(intent1);
    }
}
