package cn.bidostar.ticserver.service;

import android.app.AlarmManager;
import android.app.job.JobInfo;
import android.app.job.JobParameters;
import android.app.job.JobScheduler;
import android.app.job.JobService;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.SystemClock;

import com.igexin.sdk.PushManager;

import cn.bidostar.ticserver.CarApplication;
import cn.bidostar.ticserver.utils.AppConsts;
import cn.bidostar.ticserver.utils.AppSharedpreferencesUtils;
import cn.bidostar.ticserver.utils.I;
import cn.bidostar.ticserver.utils.NetworkUtil;

/**
 * 熄火后设备运行1个小时后自动关闭推送服务
 */
public class StopPushJobSchedulerService extends JobService {

    @Override
    public boolean onStartJob(JobParameters mJobParameters) {
        String zt = AppSharedpreferencesUtils.get(AppConsts.CAR_GOTO_SLEEP,"10").toString();
        I.e("StopPushJobSchedulerService", "<<<<<<<run stop push>>>>>>>"+zt);
        if ("10".equals(zt)){
            PushManager.getInstance().stopService(this);

            //开启定时任务
            CarApplication.getApplication().startJobService();
        }

        // 返回true，很多工作都会执行这个地方
        return false;
    }

    @Override
    public boolean onStopJob(JobParameters params) {
        // 返回false来销毁这个工作
        return false;
    }

}