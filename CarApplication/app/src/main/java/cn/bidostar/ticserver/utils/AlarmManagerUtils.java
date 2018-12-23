package cn.bidostar.ticserver.utils;

import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

public class AlarmManagerUtils {

    //private static final long TIME_INTERVAL = 3 * 60 * 1000;//闹钟执行任务的时间间隔,3分钟
    private static final long WAKEUP_TIME_INTERVAL = 9 * 60 * 60 * 1000;//闹钟执行任务的时间间隔,9个小时
    private static final long TIME_INTERVAL = 8 * 60 * 1000;//闹钟执行任务的时间间隔,8分钟
    private static final long TIME_INTERVAL_MORE = 2 * 60 * 1000;//闹钟执行任务的时间间隔,2分钟
    private Context context;
    public static AlarmManager am;
    public static PendingIntent pendingIntent;
    public static PendingIntent closePushPendingIntent;
    public static PendingIntent wakeUpPendingIntent;
    //
    private AlarmManagerUtils(Context aContext) {
        this.context = aContext;
    }

    //饿汉式单例设计模式
    private static AlarmManagerUtils instance = null;

    public static AlarmManagerUtils getInstance(Context aContext) {
        if (instance == null) {
            synchronized (AlarmManagerUtils.class) {
                if (instance == null) {
                    instance = new AlarmManagerUtils(aContext);
                }
            }
        }

        return instance;
    }

    public void createGetUpAlarmManager() {
        if (am == null){
            am = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
            Intent intent = new Intent("sendHeartPkg");
            pendingIntent = PendingIntent.getBroadcast(context, 0, intent, PendingIntent.FLAG_CANCEL_CURRENT);//每隔5秒发送一次广播
            closePushPendingIntent = PendingIntent.getBroadcast(context, 0, new Intent("closePush"), PendingIntent.FLAG_CANCEL_CURRENT);
            wakeUpPendingIntent = PendingIntent.getBroadcast(context, 0, new Intent("alarmWakeup"), PendingIntent.FLAG_CANCEL_CURRENT);
        }
    }

    public void cancelAlarmManager() {
        try{
            if (am != null){
                am.cancel(pendingIntent);
                am.cancel(closePushPendingIntent);
                am.cancel(wakeUpPendingIntent);
                am = null;
                instance = null;
            }else{
                if (context != null){
                    createGetUpAlarmManager();
                    am.cancel(pendingIntent);
                    am.cancel(closePushPendingIntent);
                    am.cancel(wakeUpPendingIntent);
                    am = null;
                    instance = null;
                }
            }
        }catch(Exception e){

        }catch(Throwable e){

        }
    }

    @SuppressLint("NewApi")
    public void getUpAlarmManagerStartWork() {
        if (am != null){
            am.setExact(AlarmManager.RTC_WAKEUP, System.currentTimeMillis(), pendingIntent);
        }
    }

    @SuppressLint("NewApi")
    public void getUpAlarmManagerWorkOnReceiver() {
        if (am != null){
            am.setExact(AlarmManager.RTC_WAKEUP, System.currentTimeMillis() + TIME_INTERVAL, pendingIntent);
        }
    }

    public void getUpAlarmManagerWorkOnReceiverMoreTime() {
        if (am != null){
            am.setExact(AlarmManager.RTC_WAKEUP, System.currentTimeMillis() + TIME_INTERVAL_MORE, closePushPendingIntent);
        }
    }

    public void getUpAlarmManagerWorkOnReceiverWakeup() {
        if (am != null){
            am.setExact(AlarmManager.RTC_WAKEUP, System.currentTimeMillis() + WAKEUP_TIME_INTERVAL, wakeUpPendingIntent);
        }
    }
}