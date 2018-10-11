/*
 * Copyright 2016,2017 www.carassist.cn. All Rights Reserved.
 *
 * This PROPRIETARY SOFTWARE is the property of Prolink Network Shenzhen Technologies, Inc.
 * and may contain trade secrets and/or other confidential information of
 * Prolink Technologies, Inc. This file shall not be disclosed to any third party,
 * in whole or in part, without prior written consent of Prolink.
 *
 * THIS PROPRIETARY SOFTWARE AND ANY RELATED DOCUMENTATION ARE PROVIDED AS IS,
 * WITH ALL FAULTS, AND WITHOUT WARRANTY OF ANY KIND EITHER EXPRESS OR IMPLIED,
 * AND Prolink Network Shenzhen Technologies, INC. DISCLAIMS ALL EXPRESS OR IMPLIED WARRANTIES
 * OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE, QUIET ENJOYMENT OR NON-INFRINGEMENT.
 */

package cn.bidostar.ticserver.service;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.BatteryManager;
import android.os.Build;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.alibaba.sdk.android.oss.ClientException;
import com.alibaba.sdk.android.oss.OSS;
import com.alibaba.sdk.android.oss.OSSClient;
import com.alibaba.sdk.android.oss.ServiceException;
import com.alibaba.sdk.android.oss.callback.OSSCompletedCallback;
import com.alibaba.sdk.android.oss.callback.OSSProgressCallback;
import com.alibaba.sdk.android.oss.common.auth.OSSCredentialProvider;
import com.alibaba.sdk.android.oss.common.auth.OSSPlainTextAKSKCredentialProvider;
import com.alibaba.sdk.android.oss.model.PutObjectRequest;
import com.alibaba.sdk.android.oss.model.PutObjectResult;
import com.miramems.carmotion.carMotion;

import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.File;
import java.lang.ref.SoftReference;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.locks.ReentrantLock;

import cn.bidostar.ticserver.AppApplication;
import cn.bidostar.ticserver.dao.LocalFilesModelDao;
import cn.bidostar.ticserver.model.LocalFilesModel;
import cn.bidostar.ticserver.model.RequestCommonParamsDto;
import cn.bidostar.ticserver.utils.AppConsts;
import cn.bidostar.ticserver.utils.AppSharedpreferencesUtils;
import cn.bidostar.ticserver.utils.CarIntents;
import cn.bidostar.ticserver.utils.FileUtils;
import cn.bidostar.ticserver.utils.I;
import cn.bidostar.ticserver.utils.NetworkUtil;
import cn.bidostar.ticserver.utils.ServerApiUtils;
import cn.bidostar.ticserver.utils.TimeUtils;

/**
 * 定义车载的对外接口
 * 留意这个class需要在system权限下工作,可能需要系统的签名.
 *
 * The API interface for mirror device.
 *
 */
public final class API extends BroadcastReceiver implements carMotion.carMotionEventListener {

    private static final String TAG = "API";
    private Context mAppContext;
    private long mTakeId;
    private final HashMap<Long, WeakReference<TakeCallback>> mTakeCallback = new HashMap<Long, WeakReference<TakeCallback>>();
    private carMotion mCarMotion;

    /**
     * 构造API实例
     *
     * Create an API instance
     * @context Android context object.
     */
    public API(Context context) {
        this.mAppContext = context.getApplicationContext();
        IntentFilter filter = new IntentFilter();
        filter.addAction(CarIntents.ACTION_MONITOR_NOTIFY);
        //filter.addAction(CarIntents.ACTION_RECORD_FILE);
        //filter.addAction(CarIntents.ACTION_DELETE_FILE);
        //filter.addAction(CarIntents.ACTION_CAMERA_LIVING_CALLBACK);
        filter.addAction(CarIntents.ACTION_RECORDING_STORAGE_SLOW);
        filter.addAction(CarIntents.ACTION_CAPTURE_CUSTOM_VIDEO);
        filter.addAction(CarIntents.ACTION_CAPTURE_FILE_INFO);
        filter.addAction(CarIntents.ACTION_CAMERA_SNAPSHOT_CALLBACK);
        filter.addAction(Intent.ACTION_POWER_CONNECTED);//点火
        filter.addAction(Intent.ACTION_POWER_DISCONNECTED);//熄火
        mAppContext.registerReceiver(this, filter);
    }

    private boolean isFileExists(String path) {
        File file = new File(path);
        return file.exists();
    }

    private void initCarMotion() {
        if (!isFileExists("/sys/bus/platform/drivers/gsensor/read_reg")) {
            Log.w(TAG, "unsupport car motion");
            return;
        }
        Log.d(TAG, "initCarMotion");
        mCarMotion = new carMotion();
        mCarMotion.Init(carMotion.CarMotionPinNum.PIN_NONE, carMotion.CarMotionPinLevel.NONE,
                carMotion.CarMotionPinNum.PIN_NONE, carMotion.CarMotionPinLevel.NONE);

        mCarMotion.Set_Debug_Level(0x0);
        mCarMotion.Control(carMotion.CarMotionAlgorithm.VIOLENT, carMotion.CarMotionSwitchCmd.ENABLE_X);

        /*
        0 {x,y,z} => {x,y,z}
        1 {x,y,z} => {y,‐x,z}
        2 {x,y,z} => {‐x,‐y,z}
        3 {x,y,z} => {‐y,x,z}
        4 {x,y,z} => {‐x,y,‐z}
        5 {x,y,z} => {y,x,‐z}
        6 {x,y,z} => {x,‐y,‐z}
        7 {x,y,z} => {‐y,‐x,‐z}
        8 {x,y,z} => {x,z,‐y}
        9 {x,y,z} => {‐y,z,‐x}
        10 {x,y,z} => {‐x,z,y}
        11 {x,y,z} => {y,z,x}
        12 {x,y,z} => {‐x,‐z,‐y}
        13 {x,y,z} => {‐y,‐z,x}
        14 {x,y,z} => {x,‐z,y}
        15 {x,y,z} => {y,‐z,‐x}
        */
        // mCarMotion.Direction_Set_Parma(13); // T1
        mCarMotion.Direction_Set_Parma(15); // T10

        /*
         设置加减速监测灵敏等级，level 1-6，默认为 2 :
         1. 速度从零加到百公里约58秒内，能够被检测到急加速;
         2. 速度从零加到百公里约29秒内，能够被检测到急加速;
         3. 速度从零加到百公里约19秒内，能够被检测到急加速;
         4. 速度从零加到百公里约14秒内，能够被检测到急加速;
         5. 速度从零加到百公里约11秒内，能够被检测到急加速;
         6. 速度从零加到百公里约9秒内，能够被检测到急加速
         */
        mCarMotion.Violent_Set_Parma(Integer.parseInt(AppSharedpreferencesUtils.get(AppConsts.Violent_Set_Parma_Key,2).toString()));
    }

    /**
     * 设置急加速灵敏度
     * @param violent_Set_Parma
     */
    public void setViolent_Set_Parma(int violent_Set_Parma){
        violent_Set_Parma = violent_Set_Parma == 0 ? 2 : violent_Set_Parma;
        AppSharedpreferencesUtils.put(AppConsts.Violent_Set_Parma_Key,violent_Set_Parma);
        mCarMotion.Violent_Set_Parma(violent_Set_Parma);
    }

    /**
     * 接收广播(内部使用/Internal used only)
     * @hide
     */
    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals(CarIntents.ACTION_MONITOR_NOTIFY)) {

            long takeId = intent.getLongExtra("id", 0);
            I.d(TAG,"taking pic:"+takeId);
            String op = intent.getStringExtra("operation");
            int percent = intent.getIntExtra("percent", 0);  //int, 当前操作的进度, 如: 10, 50, 100
            //当operation=capd时可以获取该值, 
            String jsonstr = intent.getStringExtra("result");

            WeakReference<TakeCallback> ref = mTakeCallback.get(takeId);
            TakeCallback cb = null;
            if (ref != null && (cb = ref.get()) != null) {
                if (op.equals("capd")) {
                    cb.onTakeResult(jsonstr);
                } else {
                    cb.onTakeProgress(percent);
                }
            }else{
                if(cb!=null) {
                    I.d(TAG, "taking capd:" + cb.toString());
                }else{
                    I.d(TAG, "taking capd is null"+mTakeCallback.size()+jsonstr);
                    if(jsonstr!=null && jsonstr.length()>5) {
                        try{
                            JSONTokener tokener = new JSONTokener(jsonstr);
                            JSONObject joResult = new JSONObject(tokener);
                            if (joResult.has("imgurl")) {
                                if (joResult.getString("imgurl").length() > 0) {
                                    final String imgPath = joResult.getString("imgurl");
                                    //ServerApiUtils.uploadFile("50", imgPath, ServerApiUtils.fileUploadImgCallback);
                                    I.d(TAG,"上传后台指令图片："+imgPath);
                                    ServerApiUtils.uploadFile("50", imgPath,new String(SocketCarBindService.socketCarBindService.CAR_HB_MP4_TASKID),ServerApiUtils.fileUploadCallback);
                                }
                            }

                            if (joResult.has("imgurlrear")) {
                                if (joResult.getString("imgurlrear").length() > 0) {
                                    final String imgPath = joResult.getString("imgurlrear");
                                    I.d(TAG,"上传后台指令图片："+imgPath);
                                    ServerApiUtils.uploadFile("50", imgPath,new String(SocketCarBindService.socketCarBindService.CAR_HB_MP4_TASKID),ServerApiUtils.fileUploadCallback);
                                }
                            }
                            if (joResult.has("videourl")) {
                                if (joResult.getString("videourl").length() > 0) {
                                    final String imgPath = joResult.getString("videourl");
                                    I.d(TAG,"上传后台指令视频："+imgPath);
                                    if(SocketCarBindService.socketCarBindService.CAR_HB_MP4_TASKID!=null && SocketCarBindService.socketCarBindService.CAR_HB_MP4_TASKID.length()>2) {
                                        ServerApiUtils.uploadFile("60", imgPath, new String(SocketCarBindService.socketCarBindService.CAR_HB_MP4_TASKID), ServerApiUtils.fileUploadCallback);
                                    }

                                }
                            }

                            if (joResult.has("videourlrear")) {
                                if (joResult.getString("videourlrear").length() > 0) {
                                    final String imgPath = joResult.getString("videourlrear");
                                    I.d(TAG,"上传后台指令视频："+imgPath);
                                    if(SocketCarBindService.socketCarBindService.CAR_HB_MP4_TASKID!=null && SocketCarBindService.socketCarBindService.CAR_HB_MP4_TASKID.length()>2) {
                                        ServerApiUtils.uploadFile("60", imgPath, new String(SocketCarBindService.socketCarBindService.CAR_HB_MP4_TASKID), ServerApiUtils.fileUploadCallback);
                                    }
                                }
                            }
                        }catch (Exception e){

                        }

                    }
                }
            }
        } else if(intent.getAction().equals(CarIntents.ACTION_RECORD_FILE)) {
            String filename = intent.getStringExtra("filename");
            int duration = intent.getIntExtra("duration", 0);
            I.e(TAG, "Add Record filename:" + filename + " duration: " + duration + "ms");
            //FileUtils.TsConvertMp4(filename);
            if(!filename.contains("S.ts")) {
                LocalFilesModel localFilesModel = new LocalFilesModel();
                localFilesModel.setLocalPath(filename);
                localFilesModel.setFlagUpload("0");
                localFilesModel.setJltype("1");
                localFilesModel.setFileType(2);
                LocalFilesModelDao dao = new LocalFilesModelDao();
                dao.insertModel(localFilesModel);
            }
            //AppApplication.getInstance().checkUpload();
            //在wifi模式并且后台设定wifi模式上传普通视频使用
            /*
            if(AppApplication.getInstance().CAR_UPLOAD_MP4_MODEL&&NetworkUtil.isWifi(AppApplication.getInstance().getApplicationContext())){
                ServerApiUtils.uploadFile("102",filename,ServerApiUtils.fileUploadCallback);
            }*/

           //I.e(TAG,"databasesList:"+JSON.toJSONString(dao.findListByFlagupload(localFilesModel))); ;
        } else if(intent.getAction().equals(CarIntents.ACTION_DELETE_FILE)) {
            try{
                String filename = intent.getStringExtra("filename");
                I.e(TAG, "Delete Record filename:" + filename);
                LocalFilesModel localFilesModel = new LocalFilesModel();
                localFilesModel.setLocalPath(filename);

                LocalFilesModelDao dao = new LocalFilesModelDao();
                dao.deleteLikeFileName(filename);


            }catch (Exception e){

            }

        } else if(intent.getAction().equals(CarIntents.ACTION_CAMERA_LIVING_CALLBACK)) {
            int result = intent.getIntExtra("result", 0);
            String error = intent.getStringExtra("error");
            if(result == 0) {
                I.e(TAG, "Camera Living Started");
            } else if(result == 1) {
                I.e(TAG, "Camera Living Stopped");
            } else if(result == 2) {
                I.e(TAG, "Camera Living Error:" + error);
            }
        } else if(intent.getAction().equals(CarIntents.ACTION_RECORDING_STORAGE_SLOW)) {
            Log.d(TAG, "Write Storgae speed slow, change high speed card");
        } else if(intent.getAction().equals(CarIntents.ACTION_CAMERA_SNAPSHOT_CALLBACK)) {
            int ret = intent.getIntExtra("ret", -1);
            String error = intent.getStringExtra("error");
            String path = intent.getStringExtra("path");
            LocalFilesModel localFilesModel = new LocalFilesModel();
            localFilesModel.setLocalPath(path);
            localFilesModel.setFlagUpload("0");
            localFilesModel.setJltype("2");
            localFilesModel.setFileType(2);
            LocalFilesModelDao dao = new LocalFilesModelDao();
            dao.insertModel(localFilesModel);
            if(path!=null&&path.trim().length()>1){
                ServerApiUtils.uploadFile("100",path,new String(SocketCarBindService.socketCarBindService.CAR_HB_MP4_TASKID),ServerApiUtils.fileUploadCallback);//直接上传到服务器 出现这个表示是后台截图的视频，所以直接上传即可
                SocketCarBindService.socketCarBindService.CAR_HB_MP4_TASKID = "";//上传完成之后任务编号设置为null
            }
            I.e(TAG, "ACTION_CAMERA_SNAPSHOT_CALLBACK ret = " + ret + " error = " + (error != null ? error : "")
                                        + " path = " + (path != null ? path : ""));
        } else if (intent.getAction().equals(CarIntents.ACTION_CAPTURE_CUSTOM_VIDEO)) {
            int prev = intent.getIntExtra("prev", 10);
            int post = intent.getIntExtra("post", 10);
            int type = intent.getIntExtra("type", 1);
            String prefix = intent.getStringExtra("prefix");

            takeCustomCaptureVideo(type, prev, post, prefix);
        } else if(intent.getAction().equals(CarIntents.ACTION_CAPTURE_FILE_INFO)) {
            int cameraid = intent.getIntExtra("cameraid", 0);
            String filename = intent.getStringExtra("filename");
            String error = intent.getStringExtra("error");
            long takeId = intent.getLongExtra("id", 0);
            if(filename!=null && filename.contains("lock")) {//如果再lock目录，则是碰撞的直接上传
                /*LocalFilesModel localFilesModel = new LocalFilesModel();
                localFilesModel.setLocalPath(filename);
                localFilesModel.setFlagUpload("0");
                localFilesModel.setJltype("2");
                localFilesModel.setFileType(2);
                LocalFilesModelDao dao = new LocalFilesModelDao();
                dao.insertModel(localFilesModel);*/
                //ServerApiUtils.uploadFile("101", filename, ServerApiUtils.fileUploadCallback);*///这是碰撞索引，直接上传
            }else{
                LocalFilesModel localFilesModel = new LocalFilesModel();
                localFilesModel.setLocalPath(filename);
                localFilesModel.setFlagUpload("0");
                localFilesModel.setJltype("2");
                localFilesModel.setFileType(2);
                LocalFilesModelDao dao = new LocalFilesModelDao();
                dao.insertModel(localFilesModel);
                ServerApiUtils.uploadFile("102", filename,new String(SocketCarBindService.socketCarBindService.CAR_HB_MP4_TASKID), ServerApiUtils.fileUploadCallback);//截图视频，直接上传
                SocketCarBindService.socketCarBindService.CAR_HB_MP4_TASKID = "";
            }
            I.e(TAG, "capture fileinfo cameraid = " + cameraid
                            + " filename = " + filename + " error = " + error+" takeId = "+takeId);
        }else if(intent.getAction().equals(Intent.ACTION_POWER_CONNECTED)){
            I.e(TAG,"Intent.ACTION_POWER_CONNECTED="+intent);//点火
            AppSharedpreferencesUtils.put(AppConsts.CAR_ON_RUN_FLAG,true);//设置汽车为点火状态
            AppSharedpreferencesUtils.put(AppConsts.CAR_GOTO_SLEEP,"00");
            RequestCommonParamsDto dto = new RequestCommonParamsDto();

            dto.setEventType(AppConsts.CAR_ON);
            ServerApiUtils.pushGpsInfo(dto,ServerApiUtils.gpsInfoCallback);

        }else if(intent.getAction().equals(Intent.ACTION_POWER_DISCONNECTED)){
            I.e(TAG,"Intent.ACTION_POWER_DISCONNECTED="+intent);//熄火  设置汽车为熄火状态
            AppSharedpreferencesUtils.put(AppConsts.CAR_ON_RUN_FLAG,false);
            AppSharedpreferencesUtils.put(AppConsts.CAR_GOTO_SLEEP,"10");
            RequestCommonParamsDto dto = new RequestCommonParamsDto();

            dto.setEventType(AppConsts.CAR_OFF);
            ServerApiUtils.pushGpsInfo(dto,ServerApiUtils.gpsInfoCallback);
        }
    }

    /**
     * carMotion库的回调(内部使用/Internal used only)
     * @exclude
     * {@hide}
     */
    @Override
    public void OncarMotionEvent(int event, int value) {
        Log.d(TAG, "CarMotionEvent(" + event + ", " + value + ")");
        if (event == carMotion.CarMotionEVENT.VIOLENT_NOTIFY) {
            synchronized (mCarMotionListeners) {
                for (CarMotionListener listener : mCarMotionListeners) {
                    listener.onViolentEvent(value);
                }
            }
        }
    }

    private ArrayList<CarMotionListener> mCarMotionListeners = new ArrayList<CarMotionListener>();

    /**
     * 注册汽车移动监听回调
     * Register g-sensor motion listener
     *
     * @param listener CarMotionListener
     * @return None
     */
    public void registerCarMotionListener(CarMotionListener listener) {
        synchronized (mCarMotionListeners) {
            mCarMotionListeners.add(listener);

            if (mCarMotion == null) {
                initCarMotion();
            }

            if (mCarMotion != null && mCarMotionListeners.size() == 1) {
                mCarMotion.RegisteOncarMotionEventListener(this);
            }
        }
    }

    /**
     * 反注册汽车移动监听回调
     * Un-register g-sensor motion listener
     *
     * @param listener CarMotionListener
     * @return 无
     */
    public void unregisterCarMotionListener(CarMotionListener listener) {
        synchronized (mCarMotionListeners) {
            mCarMotionListeners.remove(listener);

            if (mCarMotion != null && mCarMotionListeners.size() == 0) {
                mCarMotion.UnRegisteOncarMotionEventListener(this);
            }
        }
    }

    /**
     * 汽车移动监测，可用于监测急加速、急减速、左急转弯、右急转弯。
     * 注：行车中的碰撞锁影和停车休眠中的碰撞唤醒在系统级实现，暂时没有对外提供接口
     *
     * The g-sensor motion interface.
     */
    public interface CarMotionListener {
        /**
         * 汽车移动事件回调
         *
         * @param value 事件内容，carMotion.CarMotionViolent.VIOLENT_SPEED_DOWN/VIOLENT_SPEED_UP/VIOLENT_TURN_LEFT/VIOLENT_TURN_RIGHT
         * @return 无
         */
        void onViolentEvent(int value);
    }

    /**
     * 抓拍图片和视频的回调。
     *
     * Take picture to video clip callback interface
     */
    public interface TakeCallback {
        /**
         * 抓拍的进度
         *
         * Taking progress from 0 to 100
         */
        void onTakeProgress(int progressPrecentage);

        /**
         * 抓拍结果
         * Taking result
         * @return 返回一个json对象，包含关键词: imgurl, imgurlrear, videourl, videourlrear
         * returna a json object which contains imgurl, imgurlrear, videourl and videourlreal fields.
         */
        void onTakeResult(String jsonString);
    }

    /**
     * 前摄像头 front camera
     */
    public static final int CameraFront = 1;
    /**
     * 后摄像头 back/rear camera
     */
    public static final int CameraBack = 2;
    /**
     * 双摄像头 both camera
     */
    public static final int CameraBoth = 0;

    /**
     * 抓取摄像头图片
     * Take camera picture
     *
     * @param camera CameraBoth/CameraBack/CameraFront
     * @param cb     回调通知 callback
     * @return true for ok, false for failed
     */
    public boolean takePicture(int camera, TakeCallback cb) {
        long takeId;
        synchronized (this) {
            takeId = ++mTakeId;
        }
        mTakeCallback.put(takeId, new WeakReference<TakeCallback>(cb));

        Intent i = new Intent("com.car.monitor");
        i.putExtra("operation", "image");
        i.putExtra("id", takeId);   //long, auto increment, id是长整型, 必须是唯一递增的且大于0；
        i.putExtra("cam", camera);  //int, default is 0 to capture both cameras, 默认0,前后都抓拍, 1表示仅前摄像头, 2表示仅仅后摄像头
        i.putExtra("nosound", true);    //boolean，是否拍照要静音，不发出卡嚓声

        mAppContext.sendBroadcast(i);
        return true;
    }

    /**
     * 抓取基于当前时间点的视频
     * Taking video clip
     *
     * @param camera         CameraBoth/CameraBack/CameraFront
     * @param forwardSeconds 抓取当前时间之前的n秒视频 the forward seconds based on current time, for example -5
     * @param afterSeconds   抓取后n秒的视频          the backward seconds based on current time, for example 5
     * @param cb             回调通知       callback
     * @return boolean true for OK, false if failed
     */
    public boolean takeVideo(int camera, int forwardSeconds, int afterSeconds, TakeCallback cb) {
        long takeId;
        synchronized (this) {
            takeId = ++mTakeId;
        }
        mTakeCallback.put(takeId, new WeakReference<TakeCallback>(cb));

        Intent i = new Intent("com.car.monitor");
        i.putExtra("operation", "video");
        i.putExtra("id", takeId);   //long, id, auto increment, 是长整型, 必须是唯一递增的且大于0；
        i.putExtra("cam", camera);  //int, default is 0 for both cameras, 默认0,前后都抓拍, 1表示仅前摄像头, 2表示仅仅后摄像头

        i.putExtra("length", afterSeconds);
        i.putExtra("length_forward", forwardSeconds);

        mAppContext.sendBroadcast(i);
        return true;
    }

    /**
     * 抓取任意时间点的视频或者图片
     * Taking video clip or picture
     *
     * @param cameraId       0/1/2  //front/back/inside cameras
     * @param starttime      抓取视频起始时间,或者抓取图片时间 unix timestamp from 1970年1月1日
     * @param endtime        抓取视频结束时间,抓取图片时无意义 unix timestamp from 1970年1月1日
     * @param compress       视频是否需要压缩,抓取图片时无意义 主要针对1080P视频
     * @param picture        抓取视频还是图片
     * @return boolean true for OK, false if failed
     */
    public boolean takeVideo(int cameraId, int starttime, int endtime, boolean compress, boolean picture) {
        Intent i = new Intent(CarIntents.ACTION_CAMERA_SNAPSHOT);
        i.putExtra("camid", cameraId);
        i.putExtra("starttime", starttime);
        i.putExtra("endtime", endtime);

        i.putExtra("compress", compress);
        i.putExtra("picture", picture);
        I.e(TAG,"startTime="+starttime+";endtime=>"+endtime);
        mAppContext.sendBroadcast(i);
        return true;
    }

    /**
     * 抓拍自定义文件名的视频文件
     *
     * @param captureType     1: 碰撞锁影, 存在/storage/sdcard1/DVR/lock目录
     *                        2: 视频抓拍, 存在/storage/sdcard1/DVR/capture目录
     * @param prevTime  抓拍当前时间点以前视频长度
     * @param postTime  抓拍当前时间点以后视频长度
     * @param filePrefix  自定义文件前缀名
     * @return 无
     */
    public void takeCustomCaptureVideo(int captureType, int prevTime, int postTime, String filePrefix) {
        Log.d(TAG, "captureType = " + captureType + " prevTime = " + prevTime + " postTime = " + postTime + " filePrefix = " + filePrefix);
        String t = String.format("%d,%d", prevTime, postTime);
        if(captureType == 1) {
            setProperty("persist.accident.record.param", t);    //设置抓拍时间
        } else {
            setProperty("persist.capture.record.param", t);     //设置抓拍时间
        }
        setProperty("sys.dvr.capture.prefix", filePrefix);  //设置自定义文件名
        setProperty("sys.car.dvr.accident.f", String.valueOf(captureType)); //启动前摄像头抓拍
        setProperty("sys.car.dvr.accident.b", String.valueOf(captureType)); //启动后摄像头抓拍
        return;
    }

    /**
     * 修改WiFi SoftAP SSID的信息
     * Change wifi softAP SSID information
     *
     * @param ssidName     ssid的ap名称
     * @param ssidPassword ssid的密码
     * @return 无
     */
    public void changeSSID(String ssidName, String ssidPassword) {
        Intent i = new Intent(CarIntents.ACTION_SOFTAP_CONFIG);
        i.putExtra(CarIntents.EXTRA_SSID_SOFTAP, ssidName);
        i.putExtra(CarIntents.EXTRA_PWD_SOFTAP, ssidPassword);
        mAppContext.sendBroadcast(i);
    }

    /**
     * 使系统进入休眠, suspend system
     * @return None
     */
    public void suspendSystem() {
        mAppContext.sendBroadcast(new Intent(CarIntents.ACTION_FORCE_SLEEP));
    }

    /**
     * 使系统重启 / reboot system
     *
     * @return None
     */
    public void rebootSystem() {
        Intent i = new Intent("com.car.fakebtn.longpressed");
        i.putExtra("repeatCount", 40);
        mAppContext.sendBroadcast(i);
    }

    /**
     * 设置设备在ACC ON并且停车不动N分钟，会自动进入休眠。其中N可以配置，默认为30分钟，0表示禁止停车自动休眠功能
     *
     * @param minutes 分钟，停车N分钟后
     */
    public void setAutoSleepTime(int minutes) {
        Settings.Global.putInt(mAppContext.getContentResolver(),
                "autosleeptime", minutes);
    }

    //
    //DVR (Digital Video Recorder) 记录仪录像 related function.
    /**
     * 设置某个摄像头的分辨率,bitrate/fps 等信息
     *
     * @param camera  CameraFront/CameraBack
     * @param width   -1 则不修改
     * @param height  -1 则不修改
     * @param bitrate -1 则不修改
     * @param fps     -1 则不修改
     * @return
     */
    public boolean setVideoParams(int camera, int width, int height, int bitrate, int fps) {
        return false;
    }

    /**
     * 视频是否录制声音
     *
     * @param camera            CameraFront/CameraBack/
     * @param enableAudioRecord 是否录音
     * @return 无
     */
    public void setDVRAudioEnable(int camera, boolean enableAudioRecord) {
        Settings.Global.putInt(mAppContext.getContentResolver(),
                "mute_record", enableAudioRecord? 0: 1);
    }

    /**
     * 视频是否录制声音
     *
     * @param camera            CameraFront/CameraBack/
     * @return true表示录制声音，false表示静音
     */
    public boolean getDVRAudioEnable(int camera) {
        return Settings.Global.getInt(mAppContext.getContentResolver(),
                "mute_record", 0) == 0;
    }

    /**
     * 重启摄像头
     *
     * @param camera            CameraFront/CameraBack/
     * @return true表示成功，false表示失败
     */
    public boolean restartDVR(int camera){
        mAppContext.sendBroadcast(new Intent("com.car.restart_dvr"));
        return true;
    }

    /**
     * 设置录影文件每段的时长
     *  @param seconds 单位秒，目前支持60，120，180
     */
    public void setDvrAutoSaveTime(int seconds) {
        Settings.Global.putInt(mAppContext.getContentResolver(),
                "autosave_time", seconds);
    }

    /**
     * 获取录影文件每段的时长
     *  @return 单位秒
     */
    public int getDvrAutoSaveTime() {
        return Settings.Global.getInt(mAppContext.getContentResolver(),
                "autosave_time", 120);
    }

    /**
     * 获取设备序列号（sn）
     *
     * @param
     * @return 设备序列号
     */
    public String getDeviceCPUID() {
        return Build.SERIAL;
    }

    /**
     * 得到设备的IMEI
     *
     * @param
     * @return IMEI号码
     */
    @SuppressLint("MissingPermission")
    public String getDeviceIMEI() {
        TelephonyManager tm = (TelephonyManager)mAppContext.getSystemService(Context.TELEPHONY_SERVICE);
        return tm.getDeviceId();
    }

    /**
     * 获取设备所插卡的iccid号码
     *
     * @param
     * @return iccid号码
     */
    @SuppressLint({"HardwareIds", "MissingPermission"})
    public String getSimICCID() {
        TelephonyManager tm = (TelephonyManager)mAppContext.getSystemService(Context.TELEPHONY_SERVICE);
        return tm.getSimSerialNumber();
    }


    /**
     * 碰撞检测-高灵敏度
     */
    public static final int CollisionSensitivityHigh = 2;
    /**
     * 碰撞检测-中灵敏度
     */
    public static final int CollisionSensitivityNormal = 1;
    /**
     * 碰撞检测-低灵敏度
     */
    public static final int CollisionSensitivityLow = 0;

    /**
     * 设置碰撞灵敏度
     *
     * @param level CollisionSensitivityHigh/.../
     * @return true表示成功，false表示失败
     */
    public boolean setCollisionSensitivity(int level) {
        return Settings.Global.putInt(mAppContext.getContentResolver(), "gsensor_sensitive", level);
    }

    /**
     * 获取碰撞灵敏度
     *
     * @param
     * @return 灵敏度（整形值）
     */
    public int getCollisionSensitivity() {
        return Settings.Global.getInt(mAppContext.getContentResolver(), "gsensor_sensitive", CollisionSensitivityNormal);
    }

    /**
     * 行车碰撞锁影的禁止与使能
     */
    public boolean enableCollision(boolean enable) {
        return Settings.Global.putInt(mAppContext.getContentResolver(), "video_lock_enable", enable ? 1 : 0);
    }

    /**
     * 休眠时碰撞唤醒的禁止与使能
     */
    public boolean enableSuspendCollision(boolean enable) {
        return Settings.Global.putInt(mAppContext.getContentResolver(), " gsensor_enable", enable ? 1 : 0);
    }

    /**
    * 静默安装某个apk/ install apk in silence
    * @param apkPath apk全路径/the full path of apk to be installed.
    * @param packageName 包名/the package name
    * @param className 安装后自动启动的activity全路径，类似于package.class/the activity class name will be auto run after installed.
    */
    public void installApk(String apkPath, String packageName, String className) {
        Intent intent = new Intent(CarIntents.ACTION_APK_INSTALL);
        intent.putExtra(CarIntents.EXTRA_PATH_INSTALL, apkPath);
        intent.putExtra(CarIntents.EXTRA_PACKAGE_INSTALL, packageName);
        intent.putExtra(CarIntents.EXTRA_CLASS_INSTALL, className);
        mAppContext.sendBroadcast(intent);
    }

    /**
     * 格式化SD卡
     */
    public void formatSdcard() {
        Intent intent = new Intent(CarIntents.ACTION_SD_FORMAT);
        mAppContext.sendBroadcast(intent);
    }

    /**
    *   ACC on or off,主动查询接口，被动通知需注册个自己的BroadcastReceiver来监听Intent.ACTION_POWER_DISCONNECTED跟
    *   Intent.ACTION_POWER_CONNECTED，并参考本函数的实现代码进行判断
    *
    */
    public static boolean isAccOn(Context context) {
        IntentFilter ifilter = new IntentFilter(Intent.ACTION_BATTERY_CHANGED);
        Intent batteryStatus = context.registerReceiver(null, ifilter);
        if (batteryStatus != null) {
            int status = batteryStatus.getIntExtra(BatteryManager.EXTRA_STATUS, -1);
            boolean isCharging = status == BatteryManager.BATTERY_STATUS_CHARGING ||
                    status == BatteryManager.BATTERY_STATUS_FULL;
            return isCharging;
        }
        return false;
    }

    /**
     * 坐标系类型：coordinate type, GPS
     */
    public static final int COORDTYPE_GPS = 0;
    /**
     * 坐标系类型：百度, Baidu
     */
    public static final int COORDTYPE_BAIDU = 1;
    /**
     * 坐标系类型：高德, Gaode
     */
    public static final int COORDTYPE_AMAP = 2;

    /**
     * 外部发起导航请求
     * Start the navigation reuqest
     * @param lat Latitude, 纬度，double型，不为0
     * @param lng Longitude, 经度，double型，不为0
     * @param addr dest address, can be null, 目的地地址名，可为空
     * @param coordType coordinate type, support GPS/Baidu/Gaode, 坐标系类型，现在支持GPS，百度，高德三种坐标系
     */

    public void startNavi(double lat, double lng, String addr, int coordType) {
        Intent intent = new Intent(CarIntents.ACTION_NAVI_GO);
        intent.putExtra(CarIntents.EXTRA_NAVI_GO_LAT, lat);
        intent.putExtra(CarIntents.EXTRA_NAVI_GO_LNG, lng);
        intent.putExtra(CarIntents.EXTRA_NAVI_GO_ADDR, addr);
        intent.putExtra(CarIntents.EXTRA_NAVI_GO_TYPE, coordType);
        mAppContext.sendBroadcast(intent);
    }

    /**
     * 禁止设备的网络功能，执行后设备将不能使用网络功能，只能重启恢复
     */
    public void netDisable() {
        Intent intent = new Intent(CarIntents.ACTION_NET_FIREWALL);
        mAppContext.sendBroadcast(intent);
    }

    /**
     * Sample shows how to upload file to aliyun oss
     * @param filePath the path of file need to be uploaded
     * @param objectKey the unique key for uploading to identify your file
     * @param url the url of your oss server, just like xxx.xxx.aliyuncs.com
     * @param accessKeyId accessKeyId from you oss server
     * @param accessKeySecret accessKeySecret from you oss server
     */
    public void uploadFile2Oss(final String filePath, final String objectKey, String url, String accessKeyId, String accessKeySecret) {
        OSSCredentialProvider credentialProvider = new OSSPlainTextAKSKCredentialProvider(accessKeyId,
                accessKeySecret);
        final String bucket = url.substring(0, url.indexOf('.'));
        String endpoint = url.substring(url.indexOf('.') + 1);
        final OSS oss = new OSSClient(mAppContext, endpoint, credentialProvider);

        PutObjectRequest put = new PutObjectRequest(bucket, objectKey, filePath);
        put.setProgressCallback(new OSSProgressCallback<PutObjectRequest>() {
            @Override
            public void onProgress(PutObjectRequest request, long currentSize, long totalSize) {

            }
        });
        oss.asyncPutObject(put, new OSSCompletedCallback<PutObjectRequest, PutObjectResult>() {
            @Override
            public void onSuccess(PutObjectRequest request, PutObjectResult result) {
                try {
                    String publicUrl = oss.presignConstrainedObjectURL(bucket, objectKey,
                            Integer.MAX_VALUE);
                    Log.d(TAG, "UploadSuccess: " + publicUrl);
                } catch (ClientException ce) {

                }
            }

            @Override
            public void onFailure(PutObjectRequest request, ClientException clientException, ServiceException serviceException) {
                Log.d(TAG, "onFailure: ");
                // 请求异常
                if (clientException != null) {
                    // 本地异常如网络异常等
                    clientException.printStackTrace();
                }
                if (serviceException != null) {
                    // 服务异常
                    Log.e("ErrorCode", serviceException.getErrorCode());
                    Log.e("RequestId", serviceException.getRequestId());
                    Log.e("HostId", serviceException.getHostId());
                    Log.e("RawMessage", serviceException.getRawMessage());
                }
            }
        });
    }

    /**
     * TTS语音播报接口
     * @param content 需要播报的文字
     */
    public  void playTts(String content) {
       // lock.lock();
        Intent intent = new Intent("com.car.tts");
        intent.putExtra("content", content);
        mAppContext.sendBroadcast(intent);
        /*try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        lock.unlock();*/
    }

    /**
     * When acc power is off, the system will kill not-in-white-list packages what still hold the wakeup lock then enter sleep mode.
     * 
     * So if you want to do something before sleeping, for example uploading a photo to your cloud server when acc is off, you need to 
     * set your apk's package name to the white list firstly, 
     * then acquire a wakeup lock through standard Android API PowerManager.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK, "xxx".
     *
     * After done, release the wakeup lock to allow the system enter sleep mode.
     *
     * You can call this when your apk starts
     *
     *
     * 增加包名到系统抓锁白名单，系统会等待该package释放锁后才进入休眠,否则抓锁的app会被强行kill掉
     * 建议开机后立刻调用该接口，并保证对应app有抓锁行为，当然app本身逻辑要保证wakelock的正常释放，例如网络实在不行不通，超时后也要释放锁，
     * 否则系统无法休眠，会消耗汽车电瓶。
     *
     * @param packageName the white list package name list, use "," as the seperate char, for example com.android.xxx,foo.com.bar
     *                    if null, use current apk's packagename
     *
     */
    public void setWakeupLockWhiteList(String packageName) {
        if (packageName == null) {
            packageName = mAppContext.getPackageName();
        }

        Intent intent = new Intent();
        intent.setAction(CarIntents.ACTION_SET_PROP);
        intent.putExtra(CarIntents.EXTRA_SET_PROP_KEY, "sys.wakelock.whitelist");
        intent.putExtra(CarIntents.EXTRA_SET_PROP_VAL, packageName);
        mAppContext.sendBroadcast(intent);
    }

    /**
     * 新增的api 让app在休眠时可以不断网
     */
    public void setAppKeepAlive(String packageName){
        if(packageName == null){
            packageName = mAppContext.getPackageName();
        }
        Intent intent = new Intent();
        intent.setAction(CarIntents.ACTION_SET_PROP);
        intent.putExtra(CarIntents.EXTRA_SET_PROP_KEY, "persist.sys.app.keepalive");
        intent.putExtra(CarIntents.EXTRA_SET_PROP_VAL, packageName);
        mAppContext.sendBroadcast(intent);
    }

    /**
     * #RTC定时唤醒系统程序
     */
    public void setAppRTC(String packageName){
        if(packageName == null){
            packageName = mAppContext.getPackageName();
        }
        Intent intent = new Intent();
        intent.setAction(CarIntents.ACTION_SET_PROP);
        intent.putExtra(CarIntents.EXTRA_SET_PROP_KEY, "sys.rtc.packages");
        intent.putExtra(CarIntents.EXTRA_SET_PROP_VAL, packageName);
        mAppContext.sendBroadcast(intent);
    }


    /**
     * 通过广播设置系统属性
     * @param key 属性名字
     * @param value 属性值
     */
    public void setProperty(String key, String value) {
        Intent intent = new Intent();
        intent.setAction(CarIntents.ACTION_SET_PROP);
        intent.putExtra(CarIntents.EXTRA_SET_PROP_KEY, key);
        intent.putExtra(CarIntents.EXTRA_SET_PROP_VAL, value);
        mAppContext.sendBroadcast(intent);
        return;
    }

    /*
     * 恢复出厂设置，factory reset
     */
    public void factoryReset() {
        Intent intent = new Intent("com.android.internal.os.storage.FORMAT_AND_FACTORY_RESET");
        ComponentName cn = new ComponentName("android", "com.android.internal.os.storage.ExternalStorageFormatter");
        intent.setComponent(cn);
        mAppContext.startService(intent);
    }

    /*
     * 恢复出厂设置，factory reset
     * 普通权限apk请使用该方式
     *
     */
    public void factoryReset2() {
        Intent intent = new Intent("com.car.factory");
        mAppContext.sendBroadcast(intent);
    }


    /**
     * 设置系统时间, setup system time
     *
     * @param timeSet ms, utc time from 1970
     *
     */
    public void setSysTime(long timeSet) {
        Intent intent = new Intent("com.car.time.set");
        intent.putExtra("time2set", timeSet);
        mAppContext.sendBroadcast(intent);
    }

    /**
     * 截屏，screen capture
     *
     */
    public void snapshot() {
        Intent intent = new Intent("com.car.snapshot");
        mAppContext.sendBroadcast(intent);
    }


    /*
     * Settings.Global
     */
    public static int SETTINGS_TABLE_GLOBAL = 0x01;

    /*
     * Settings.Secure
     */
    public static int SETTINGS_TABLE_SECURE = 0x02;

    /*
     * Settings.System
     */
    public static int SETTINGS_TABLE_SYSTEM = 0x04;

    /*
     * int值，系统将调用Integer.valueOf()对value进行转换，value值应保证类似为"0"，"100"
     */
    public static int SETTINGS_TYPE_INT = 0x08;

    /*
     * string值
     */
    public static int SETTINGS_TYPE_STRING = 0x010;

    /*
     * long值，系统将调用Long.valueOf()对value进行转换
     */
    public static int SETTINGS_TYPE_LONG = 0x020;

    /*
     * float值，系统将调用Float.valueOf()对value进行转换
     */
    public static int SETTINGS_TYPE_FLOAT = 0x040;

    /**
     *  设置系统settings对字段值
     *  @param key 数据库字段
     *  @param value 字段值，字符串类型，系统将根据tableAndType进行对应类型转换
     *  @param tableAndType 数据库表跟value对类型
     */
    public void setSysSettings(String key, String value, int tableAndType) {
        if (tableAndType == 0) return;
        Intent intent = new Intent("com.car.settings");
        intent.putExtra("key", key);
        intent.putExtra("value", value);
        intent.putExtra("tat", tableAndType);
        mAppContext.sendBroadcast(intent);
    }

    /**
     *   开关热点
     *   @param enabled 是否打开热点
     */
    public void setSoftApEnabled(boolean enabled) {
        Intent intent = new Intent("com.car.softap");
        intent.putExtra("enable", enabled);
        mAppContext.sendBroadcast(intent);
    }

    /**
     *   开关数据网络
     *   @param enabled 是否打开数据网络
     */
    public void setMobileEnabled(boolean enabled) {
        Intent intent = new Intent("com.car.mobiledata");
        intent.putExtra("enable", enabled);
        mAppContext.sendBroadcast(intent);
    }

    /**
     * 休眠状态下发起录像时，一定要先调用这个广播
     */
    public void startCar_WAKEUP(){
        Intent intent = new Intent("com.car.syswakeup");
        intent.putExtra("reason", "recordvideo");
        mAppContext.sendBroadcast(intent);
    }

}