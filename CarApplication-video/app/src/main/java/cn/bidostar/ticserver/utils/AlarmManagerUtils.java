package cn.bidostar.ticserver.utils;

import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

public class AlarmManagerUtils {

    //private static final long TIME_INTERVAL = 3 * 60 * 1000;//闹钟执行任务的时间间隔,3分钟
    private static final long TIME_INTERVAL = 5 * 60 * 1000;//闹钟执行任务的时间间隔,5分钟
    private Context context;
    public static AlarmManager am;
    public static PendingIntent pendingIntent;
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
            pendingIntent = PendingIntent.getBroadcast(context, 0, intent, 0);//每隔5秒发送一次广播
        }
    }

    public void cancelAlarmManager() {
        if (am != null){
            am.cancel(pendingIntent);
            am = null;
            instance = null;
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
            am.setExact(AlarmManager.RTC_WAKEUP, System.currentTimeMillis()
                    + TIME_INTERVAL, pendingIntent);
        }
    }
}