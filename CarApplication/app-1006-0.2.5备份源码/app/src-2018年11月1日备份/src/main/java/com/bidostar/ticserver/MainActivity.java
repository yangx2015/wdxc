package com.bidostar.ticserver;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.TelephonyManager;

import com.bidostar.ticserver.service.AppPushService;
import com.bidostar.ticserver.service.PushIntentService;
import com.bidostar.ticserver.service.SocketCarBindService;
import com.bidostar.ticserver.utils.I;
import com.bidostar.ticserver.utils.Utils;
import com.igexin.sdk.PushManager;

import static com.bidostar.ticserver.utils.AppSharedpreferencesUtils.context;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        boolean isRun = Utils.isServiceWork(context, SocketCarBindService.class.getName());
        if (!isRun){
            //启动service，调整数据上报时长
            Intent serviceTwo = new Intent();
            serviceTwo.setClass(context, SocketCarBindService.class);
            context.startService(serviceTwo);
        }
        I.e("MainActivity","onCreate>>>>>");
    }

    @Override
    protected void onResume() {
        super.onResume();

        finish();
    }
}
