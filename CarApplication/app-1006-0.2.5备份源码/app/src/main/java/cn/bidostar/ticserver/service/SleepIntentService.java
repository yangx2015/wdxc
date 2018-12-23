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
        try{
            RequestCommonParamsDto dto = new RequestCommonParamsDto();
            //ServerApiUtils.getInstance().pushGpsInfoSleep(dto);
        }catch (Exception e){
            I.e("SleepIntentService", "exception>>>>>"+e.getMessage());
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
