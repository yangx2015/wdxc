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
        mTakePic = (Button) findViewById(cn.bidostar.ticserver.R.id.takepic);
        mTakeVideo = (Button) findViewById(cn.bidostar.ticserver.R.id.takevideo);
        mViewFront = (Button) findViewById(cn.bidostar.ticserver.R.id.viewfront);
        mViewRear = (Button) findViewById(cn.bidostar.ticserver.R.id.viewrear);
        mStartFrontLiving = (Button) findViewById(cn.bidostar.ticserver.R.id.startLiving);
        mStopFrontLiving = (Button) findViewById(cn.bidostar.ticserver.R.id.stopLiving);
        mvideohebin = (Button) findViewById(cn.bidostar.ticserver.R.id.videohebin);
        uploadImgBtn = (Button) findViewById(cn.bidostar.ticserver.R.id.uploadImgBtn);
        uploadMp4Btn = (Button) findViewById(cn.bidostar.ticserver.R.id.uploadMp4Btn);
        localCar = (Button) findViewById(cn.bidostar.ticserver.R.id.localCar);
        mViewFront.setEnabled(false);
        mViewRear.setEnabled(false);
        mMsgShow = (TextView) findViewById(cn.bidostar.ticserver.R.id.msgshow);


        //  final LocalLiving mLocalLiving = new LocalLiving(this);       //local tcp living
        //final Live mCamLiving;    //oss living

        //FIXME: write the correct OSS info here before support oss living
        String OSSAccessKeyID = "xxx";
        String OSSAccessKeySecret = "xxxxxx";
        String[] OSSDomains = {"liveshenzhen.xxx", "livehangzhou.xxx", "liveqingdao.xxx"}; //FIXME: set all the oss domain if exist, we will choose the fastest one
        String url = "xxx.xxx.aliyuncs.com";
        //end

        String filePath = "/sdcard/Pictures/F2017_04_27_145528.jpg";
        //mApi.uploadFile2Oss(filePath, new File(filePath).getName(), url, OSSAccessKeyID, OSSAccessKeySecret);
       //AppApplication.getInstance().initPushServer();
        activity = this;
        mTakePic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mViewFront.setEnabled(false);
                mViewRear.setEnabled(false);
                AppApplication.getInstance().mApi.takeVideo(1,new Long(TimeUtils.stringtoDate("2018-03-16 17:20:00").getTime()/1000).intValue() ,new Long(TimeUtils.stringtoDate("2018-03-16 17:40:00").getTime()/1000).intValue(),true,false);
            }
        });

        mTakeVideo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mViewFront.setEnabled(false);
                mViewRear.setEnabled(false);
                AppApplication.getInstance().mApi.takeVideo(2,new Long(TimeUtils.stringtoDate("2018-03-16 17:20:00").getTime()/1000).intValue() ,new Long(TimeUtils.stringtoDate("2018-03-16 17:40:00").getTime()/1000).intValue(),true,false);
            }
        });

        mvideohebin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                I.e(TAG, "s" + TimeUtils.stringtoDate("2018-02-06 17:30:00").getTime() + ";" + TimeUtils.stringtoDate("2018-02-06 00:00:00").getTime());
                //mApi.takeVideo(0,new Long(TimeUtils.stringtoDate("2018-02-06 17:30:00").getTime()/1000).intValue() ,new Long(TimeUtils.stringtoDate("2018-02-06 18:00:00").getTime()/1000).intValue(),true,false);
                // mApi.takeVideo(1,new Long(TimeUtils.stringtoDate("2018-02-06 17:30:00").getTime()/1000).intValue() ,new Long(TimeUtils.stringtoDate("2018-02-06 18:00:00").getTime()/1000).intValue(),true,false);
                //mApi.takeVideo(2,new Long(TimeUtils.stringtoDate("2018-02-06 18:07:00").getTime()/1000)1.intValue() ,new Long(TimeUtils.stringtoDate("2018-02-06 18:15:00").getTime()/1000).intValue(),true,false);
                AppApplication.getInstance().mApi.takeVideo(0,new Long(TimeUtils.stringtoDate("2018-03-16 17:20:00").getTime()/1000).intValue() ,new Long(TimeUtils.stringtoDate("2018-03-16 17:40:00").getTime()/1000).intValue(),true,false);
              //  mApi.takeCustomCaptureVideo(2, 1000, 20, "weicy");
            }
        });

        // mCamLiving = new Live(getApplicationContext(), OSSAccessKeyID, OSSAccessKeySecret, OSSDomains);

        mStartFrontLiving.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //tcp living
//                mLocalLiving.startLiving();

                //oss living
                // String ep = mCamLiving.startLiveUpload(Live.CameraFront, "TWNFC66DRWAAHU9D_0123456789");
                //I.d(TAG, "startLivingUpload endpoint = " + ep);

                //FIXME: pass the ep and uniqueFileName information to phone side
                //phone side write these info to OSS library, and then start living

            }
        });

        mStopFrontLiving.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //tcp living
//                mLocalLiving.stopLiving();


                //oss living
                //  mCamLiving.stopLiveUpload(Live.CameraFront);
            }
        });
        localCar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initLocal();
            }
        });

        I.e(TAG,TestActivity.class.getName());
    }

    public void takeVideo(final String taskId){
        mApi.takeVideo(0, 10, 10, new API.TakeCallback() {
            @Override
            public void onTakeProgress(final int progressPrecentage) {
                mMsgShow.setText("taking video: " + progressPrecentage + "%");

            }

            @Override
            public void onTakeResult(final String jsonString) {
                mMsgShow.setText("takd video result: " + jsonString);
                try {
                    JSONTokener tokener = new JSONTokener(jsonString);
                    JSONObject joResult = new JSONObject(tokener);
                    if (joResult.has("videourl")) {
                        if (joResult.getString("videourl").length() > 0) {
                            final String imgPath = joResult.getString("videourl");

                            ServerApiUtils.uploadFile("60", imgPath,ServerApiUtils.fileUploadCallback);

                        }
                    }

                    if (joResult.has("videourlrear")) {
                        if (joResult.getString("videourlrear").length() > 0) {
                            final String imgPath = joResult.getString("videourlrear");

                        }
                    }
                } catch (Exception e) {
                    //ignore this error
                    e.printStackTrace();
                }
            }
        });
    }

    public void takePicture(final String taskId){
        mApi.takePicture(0, new API.TakeCallback() {
            @Override
            public void onTakeProgress(final int progressPrecentage) {
                mMsgShow.setText("taking picture: " + progressPrecentage + "%");
            }

            @Override
            public void onTakeResult(final String jsonString) {
                mMsgShow.setText("take picture result: " + jsonString);
                try {
                    JSONTokener tokener = new JSONTokener(jsonString);
                    JSONObject joResult = new JSONObject(tokener);
                    if (joResult.has("imgurl")) {
                        if (joResult.getString("imgurl").length() > 0) {
                            final String imgPath = joResult.getString("imgurl");
                            ServerApiUtils.uploadFile("50", imgPath, ServerApiUtils.fileUploadImgCallback);
                            mViewFront.setEnabled(true);
                            mViewFront.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    Intent intent = new Intent();
                                    intent.setAction(Intent.ACTION_VIEW);
                                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                    Uri uri = Uri.fromFile(new File(imgPath));
                                    intent.setDataAndType(uri, "image/*");
                                    startActivity(intent);
                                }
                            });
                        }
                    }

                    if (joResult.has("imgurlrear")) {
                        if (joResult.getString("imgurlrear").length() > 0) {
                            final String imgPath = joResult.getString("imgurlrear");
                            mViewRear.setEnabled(true);
                            mViewRear.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    Intent intent = new Intent();
                                    intent.setAction(Intent.ACTION_VIEW);
                                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                    Uri uri = Uri.fromFile(new File(imgPath));
                                    intent.setDataAndType(uri, "image/*");
                                    startActivity(intent);
                                }
                            });
                        }
                    }
                } catch (Exception e) {
                    //ignore this error
                    e.printStackTrace();
                }

            }
        });
    }


    public void initLocal() {
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        //获取当前可用的位置控制器
        List<String> list = locationManager.getProviders(true);
        if (list.contains(LocationManager.GPS_PROVIDER)) {
            //是否为GPS位置控制器
            provider = LocationManager.GPS_PROVIDER;
        }
        else if (list.contains(LocationManager.NETWORK_PROVIDER)) {
            //是否为网络位置控制器
            provider = LocationManager.NETWORK_PROVIDER;

        } else {
            Toast.makeText(this, "请检查网络或GPS是否打开",
                    Toast.LENGTH_LONG).show();
            return;
        }
        Location location = locationManager.getLastKnownLocation(provider);
        showLocation(location);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            I.e(TAG, "local is no permission");
            return;
        }
        locationManager.requestLocationUpdates(provider, 1000, 10, new LocationListener() {
            public void onLocationChanged(Location location) {
                // TODO Auto-generated method stub
                showLocation(location);
            }

            public void onProviderDisabled(String provider) {
                // TODO Auto-generated method stub
                showLocation(null);
            }

            public void onProviderEnabled(String provider) {

                showLocation(locationManager.getLastKnownLocation(provider));
            }

            public void onStatusChanged(String provider, int status, Bundle extras) {
                // TODO Auto-generated method stub
                I.e(TAG,"local GPS error");
            }
        });
    }
    public void showLocation(Location currentLocation){
        if(currentLocation != null){
            String s = "";
            s += " Current Location: (";
            s += currentLocation.getLongitude();
            s += ",";
            s += currentLocation.getLatitude();
            s += ")\n Speed: ";
            s += currentLocation.getSpeed();
            s += "\n Direction: ";
            s += currentLocation.getBearing();
            s+="\n Provider:"+currentLocation.getProvider();
            I.e(TAG,"local:"+s);
            mMsgShow.setText("local result: " + s);
           // text.setText(s);
        }
        else{
           // text.setText("");
            I.e(TAG,"local is null");
        }
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
