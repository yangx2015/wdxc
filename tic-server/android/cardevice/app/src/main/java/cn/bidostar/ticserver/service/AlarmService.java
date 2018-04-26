package cn.bidostar.ticserver.service;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.SystemClock;
import android.util.Log;

import org.xutils.common.Callback;

import java.util.List;

import cn.bidostar.ticserver.dao.LocalFilesModelDao;
import cn.bidostar.ticserver.model.LocalFilesModel;
import cn.bidostar.ticserver.receiver.AlarmReceive;
import cn.bidostar.ticserver.utils.ServerApiUtils;

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
    }

    /**
     * 调用Service都会执行到该方法
     */
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        //这里模拟后台操作
        new Thread(new Runnable() {
            @Override
            public void run() {
                Log.e("wj","循环执行了，哈哈."+ System.currentTimeMillis());
                LocalFilesModelDao localFilesModelDao = new LocalFilesModelDao();
                LocalFilesModel filesModel = new LocalFilesModel();
                filesModel.setFlagUpload("0");
                List<LocalFilesModel> lists = localFilesModelDao.findListByFlagupload(filesModel);
                if(lists!=null && lists.size()>0){
                    ServerApiUtils.uploadFile("05", lists.get(0).getLocalPath(), new Callback.CommonCallback<String>() {
                        @Override
                        public void onSuccess(String result) {

                        }

                        @Override
                        public void onError(Throwable ex, boolean isOnCallback) {

                        }

                        @Override
                        public void onCancelled(CancelledException cex) {

                        }

                        @Override
                        public void onFinished() {

                        }
                    });
                }
            }
        }).start();

        //通过AlarmManager定时启动广播
        AlarmManager alarmManager= (AlarmManager) getSystemService(ALARM_SERVICE);
        long triggerAtTime= SystemClock.elapsedRealtime()+ONE_Miniute;//从开机到现在的毫秒书（手机睡眠(sleep)的时间也包括在内
        Intent i=new Intent(this, AlarmReceive.class);
        PendingIntent pIntent=PendingIntent.getBroadcast(this,PENDING_REQUEST,i,PENDING_REQUEST);
        alarmManager.set(AlarmManager.ELAPSED_REALTIME_WAKEUP,triggerAtTime,pIntent);

        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }
}