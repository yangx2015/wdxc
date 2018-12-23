package cn.bidostar.ticserver;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.PowerManager;
import android.os.Process;
import android.os.SystemClock;
import android.support.v4.app.ActivityCompat;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.igexin.sdk.PushManager;
import com.miramems.carmotion.carMotion;

import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.bidostar.ticserver.model.RequestCommonParamsDto;
import cn.bidostar.ticserver.receiver.APIReceive;
import cn.bidostar.ticserver.service.API;
import cn.bidostar.ticserver.service.AppPushService;
import cn.bidostar.ticserver.service.PushIntentService;
import cn.bidostar.ticserver.service.SocketCarBindService;
import cn.bidostar.ticserver.utils.AppConsts;
import cn.bidostar.ticserver.utils.AppSharedpreferencesUtils;
import cn.bidostar.ticserver.utils.I;
import cn.bidostar.ticserver.utils.ServerApiUtils;
import cn.bidostar.ticserver.utils.TimeUtils;
import cn.bidostar.ticserver.utils.Utils;

/**
 * Created by admins on 2018/2/5.
 */

public class TestActivity extends Activity{

    private static final String TAG = "API.MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppSharedpreferencesUtils.init(this);
        PowerManager powerManager = (PowerManager) this.getSystemService(Context.POWER_SERVICE);
        boolean ifOpen = powerManager.isScreenOn();
        if (ifOpen){
            //屏幕处于点亮状态，设备就不处于休眠状态
            AppSharedpreferencesUtils.put(AppConsts.CAR_GOTO_SLEEP,"00");
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    public void onResume() {
        super.onResume();
        moveTaskToBack(true); // 设置为后台
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        android.os.Process.killProcess(Process.myPid());
    }
}
