package cn.bidostar.ticserver;

import android.Manifest;
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
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.miramems.carmotion.carMotion;

import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.File;
import java.util.List;

import cn.bidostar.ticserver.model.RequestCommonParamsDto;
import cn.bidostar.ticserver.service.API;
import cn.bidostar.ticserver.service.AppPushService;
import cn.bidostar.ticserver.service.CarBindService;
import cn.bidostar.ticserver.service.SocketCarBindService;
import cn.bidostar.ticserver.utils.AppConsts;
import cn.bidostar.ticserver.utils.AppSharedpreferencesUtils;
import cn.bidostar.ticserver.utils.I;
import cn.bidostar.ticserver.utils.ServerApiUtils;
import cn.bidostar.ticserver.utils.TimeUtils;

/**
 * Created by admins on 2018/2/5.
 */

public class TestActivity extends Activity{

    private static final String TAG = "API.MainActivity";
    Button mTakePic, mTakeVideo, mViewFront, mViewRear, mStartFrontLiving, mStopFrontLiving, mvideohebin, uploadImgBtn, uploadMp4Btn, localCar;
    TextView mMsgShow;
    public static API mApi;
    public static TestActivity activity;
    private LocationManager locationManager;
    private String provider;
    Handler mHandler = new Handler();

    private Class userPushService = AppPushService.class;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //2018年9月24日。先取消socket守护线程，以后根据测算情况，如果效果不佳，再开启
        /*setContentView(cn.bidostar.ticserver.R.layout.activity_main);
        Intent service = new Intent(this,CarBindService.class);
        startService(service);*/
        PowerManager powerManager = (PowerManager) this.getSystemService(Context.POWER_SERVICE);
        boolean ifOpen = powerManager.isScreenOn();
        if (ifOpen){
            //屏幕处于点亮状态，设备就不处于休眠状态
            AppSharedpreferencesUtils.put(AppConsts.CAR_GOTO_SLEEP,"00");
        }
        Intent serviceTwo = new Intent();
        serviceTwo.setClass(this, SocketCarBindService.class);
        startService(serviceTwo);
        //
        finish();
    }

    @Override
    public void onResume() {
        super.onResume();
        //mApi.registerCarMotionListener(this);
    }

    @Override
    public void onPause() {
        super.onPause();
        //mApi.unregisterCarMotionListener(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
     //   mApi.unregisterCarMotionListener(this);
    }






}
