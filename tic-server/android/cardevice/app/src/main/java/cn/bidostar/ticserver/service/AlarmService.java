package cn.bidostar.ticserver.service;

import android.app.AlarmManager;
import android.app.IntentService;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.PowerManager;
import android.os.SystemClock;
import android.util.Log;

import org.xutils.common.Callback;

import java.util.Calendar;
import java.util.List;

import cn.bidostar.ticserver.AppApplication;
import cn.bidostar.ticserver.dao.LocalFilesModelDao;
import cn.bidostar.ticserver.dao.RequestCommonParamsDtoDao;
import cn.bidostar.ticserver.model.LocalFilesModel;
import cn.bidostar.ticserver.receiver.AlarmReceive;
import cn.bidostar.ticserver.receiver.WakeSleepReceiver;
import cn.bidostar.ticserver.utils.AppConsts;
import cn.bidostar.ticserver.utils.AppSharedpreferencesUtils;
import cn.bidostar.ticserver.utils.CarIntents;
import cn.bidostar.ticserver.utils.I;
import cn.bidostar.ticserver.utils.NetworkUtil;
import cn.bidostar.ticserver.utils.ServerApiUtils;
import cn.bidostar.ticserver.utils.Utils;

/**
 * Created by admins on 2018/3/12.
 */

public class AlarmService extends Service {
    /**
     * 每1分钟更新一次数据
     */
    private static final int ONE_Miniute=60*1000;
    private static final int PENDING_REQUEST=0;

    public AlarmService() {
        //super("AlarmService");
    }

    /*@Override
    protected void onHandleIntent(Intent intent) {
        // 执行耗时任务
        I.e(AlarmService.class.getSimpleName(),"onHandleIntent wakeup");

        String zt = AppSharedpreferencesUtils.get(AppConsts.CAR_GOTO_SLEEP,"00").toString();
        if ("00".equals(zt)){
            //已经正常点火，就不需要休眠唤醒
            WakeSleepReceiver.completeWakefulIntent(intent);
            return;
        }

        //启动CPU电源锁
        if(!NetworkUtil.isConnected(AppApplication.getInstance().getApplicationContext())){
            Integer c = (Integer)AppSharedpreferencesUtils.get(AppConsts.NO_NETWORK,new Integer(0));
            if (c < 3){
                AppApplication.getInstance().initPushServer();
                AppSharedpreferencesUtils.put(AppConsts.NO_NETWORK, new Integer(c++));
            }
        }else{
            AppSharedpreferencesUtils.put(AppConsts.NO_NETWORK, new Integer(0));
            AppApplication.getInstance().uploadGps();
        }
        // 结束任务时释放唤醒锁
        WakeSleepReceiver.completeWakefulIntent(intent);
    }*/

    /**
     * 调用Service都会执行到该方法
     */
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        I.e(AlarmService.class.getSimpleName(),"onStartCommand wakeup");

        String zt = AppSharedpreferencesUtils.get(AppConsts.CAR_GOTO_SLEEP,"00").toString();
        if ("00".equals(zt)){
            //已经正常点火，就不需要休眠唤醒
            return super.onStartCommand(intent, flags, startId);
        }

        //启动CPU电源锁
        /*if(NetworkUtil.isConnected(AppApplication.getInstance().getApplicationContext())){
            AppSharedpreferencesUtils.put(AppConsts.NO_NETWORK, new Integer(0));
            AppApplication.getInstance().acquire();
            boolean flag = AppApplication.getInstance().uploadGpsSleep();
            if (flag){
                AppApplication.getInstance().unacquire();
            }
        }*/
        boolean isRun = Utils.isServiceWork(AppApplication.getContext(),
                "cn.bidostar.ticserver.service.PushIntentService");
        if (!isRun){
            I.e(AlarmService.class.getSimpleName(),"push not work ");
            AppApplication.getInstance().initPushServer();
        }

        //通过AlarmManager定时启动广播
        AlarmManager alarmManager= (AlarmManager) getSystemService(AlarmService.ALARM_SERVICE);
        //每5分钟执行一次唤醒动作
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.add(Calendar.MINUTE, 9999);
        Intent i = new Intent(this, AlarmService.class);
        PendingIntent pIntent=PendingIntent.getService(this, 0, i, 0);

        alarmManager.setExact(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pIntent);

        stopSelf();
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
