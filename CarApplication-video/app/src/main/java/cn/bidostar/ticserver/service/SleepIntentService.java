package cn.bidostar.ticserver.service;

import android.app.ActivityManager;
import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.os.PowerManager;
import android.os.SystemClock;
import android.support.annotation.Nullable;
import android.util.Log;


import com.igexin.sdk.PushManager;

import java.util.List;

import cn.bidostar.ticserver.CarApplication;
import cn.bidostar.ticserver.model.RequestCommonParamsDto;
import cn.bidostar.ticserver.receiver.APIReceive;
import cn.bidostar.ticserver.utils.AppConsts;
import cn.bidostar.ticserver.utils.AppSharedpreferencesUtils;
import cn.bidostar.ticserver.utils.I;
import cn.bidostar.ticserver.utils.NetworkUtil;
import cn.bidostar.ticserver.utils.ServerApiUtils;
import cn.bidostar.ticserver.utils.TimeUtils;
import cn.bidostar.ticserver.utils.Utils;

/**
 * 熄火后休眠进行定时心跳包发送功能
 */
public class SleepIntentService extends IntentService {
    private static final String TAG = SleepIntentService.class.getSimpleName();

    PowerManager pm = null;
    PowerManager.WakeLock wakeLock = null;

    public SleepIntentService(){
        super("SleepIntentService");
    }

    @Override
    public void onCreate() {
        super.onCreate();
        //CarApplication.getApplication().lockWakeup();
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        //熄火后如果2分钟内都没有连接上WiFi，则主动解锁，使设备进入休眠
        I.e("SleepIntentService", "sleep waiting");
        SystemClock.sleep(1000 * 60 * 2);
        I.e("SleepIntentService", "sleep finished");
    }

    @Override
    public void onDestroy() {
        if (!NetworkUtil.isWifi(this)){
            I.e("SleepIntentService", "not wifi release wakeup");
            CarApplication.getApplication().releaseWakeup();
        }else{
            I.e("SleepIntentService", " wifi running");
        }
        super.onDestroy();
    }
}
