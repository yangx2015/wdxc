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
        setContentView(cn.bidostar.ticserver.R.layout.activity_main);
        Intent service = new Intent(this,CarBindService.class);
        startService(service);
        Intent serviceTwo = new Intent();
        serviceTwo.setClass(this, SocketCarBindService.class);
        startService(serviceTwo);
        I.e(TAG,TestActivity.class.getName());
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d(TAG, "onResume");
        //mApi.registerCarMotionListener(this);
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.d(TAG, "onPause");
        //mApi.unregisterCarMotionListener(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy");
     //   mApi.unregisterCarMotionListener(this);
    }






}
