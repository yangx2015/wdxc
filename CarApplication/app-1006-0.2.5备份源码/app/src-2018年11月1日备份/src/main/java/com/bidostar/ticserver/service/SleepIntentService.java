package com.bidostar.ticserver.service;

import android.app.IntentService;
import android.content.Intent;
import android.os.PowerManager;
import android.support.annotation.Nullable;

import com.bidostar.ticserver.model.RequestCommonParamsDto;
import com.bidostar.ticserver.utils.AppSharedpreferencesUtils;
import com.bidostar.ticserver.utils.ServerApiUtils;

/**
 * 熄火后休眠进行定时心跳包发送功能
 */
public class SleepIntentService extends IntentService {
    private static final String TAG = SleepIntentService.class.getSimpleName();

    public SleepIntentService(){
        super("SleepIntentService");
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        if (AppSharedpreferencesUtils.context == null){
            AppSharedpreferencesUtils.init(this);
        }

        RequestCommonParamsDto dto = new RequestCommonParamsDto();
        ServerApiUtils.pushGpsInfoSleep(this, dto);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
