package com.bidostar.ticserver.utils;

import android.content.Context;
import android.content.Intent;

import com.alibaba.fastjson.JSON;
import com.bidostar.ticserver.CarApplication;
import com.bidostar.ticserver.MainActivity;
import com.bidostar.ticserver.dao.LocalFilesModelDao;
import com.bidostar.ticserver.dao.RequestCommonParamsDtoDao;
import com.bidostar.ticserver.model.RequestCommonParamsDto;
import com.bidostar.ticserver.service.SocketCarBindService;
import com.igexin.sdk.PushManager;

import org.xutils.common.Callback;
import org.xutils.common.util.KeyValue;
import org.xutils.http.RequestParams;
import org.xutils.http.body.MultipartBody;
import org.xutils.x;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * Created by admins on 2018/3/12.
 */

public class ServerApiUtils {

    public static String TAG = "ServerApiUtils";
    private static List<RequestCommonParamsDto> dtos = new ArrayList<>();
    //网络请求线程队列，同时只让一个线程执行上传操作
    public static Executor signThread = Executors.newFixedThreadPool(1);

    /**
     * 数据接收地址
     * @return
     */
    public static String getServerUrlBase(){
        return AppSharedpreferencesUtils.get(AppConsts.CAR_BASE_SERVER_URL, AppConsts.BASE_URL).toString();
    }

    public static void downLoadAppApk(final Context context, final String url, final String path){
        RequestParams requestParams = new RequestParams(url);
        requestParams.setSaveFilePath(path);
        x.http().get(requestParams, new Callback.ProgressCallback<File>() {
            @Override
            public void onWaiting() {
            }

            @Override
            public void onStarted() {
            }

            @Override
            public void onLoading(long total, long current, boolean isDownloading) {
            }

            @Override
            public void onSuccess(File result) {
                //I.e(TAG,"download Apk finish:");
                Intent intent = new Intent(CarIntents.ACTION_APK_INSTALL);
                intent.putExtra(CarIntents.EXTRA_PATH_INSTALL, path);
                intent.putExtra(CarIntents.EXTRA_PACKAGE_INSTALL, "com.bidostar.ticserver");
                intent.putExtra(CarIntents.EXTRA_CLASS_INSTALL, MainActivity.class.getName());
                context.sendBroadcast(intent);
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
            }

            @Override
            public void onCancelled(CancelledException cex) {
            }

            @Override
            public void onFinished() {

            }
        });
    }

    /**
     * 上传文件的公共方法
     * @param eventType
     * @param filePath
     * @param callback
     * @return
     */
    public static boolean uploadFile(Context context, String eventType, String filePath, Callback.CommonCallback<String> callback){
        return uploadFile(context, eventType,filePath,"",callback);
    }

    /**
     * 上传之后，不进行循环
     * @param eventType
     * @param filePath
     * @param taskId
     * @param callback
     * @return
     */
    public static boolean uploadFile(Context context, String eventType, String filePath, String taskId, Callback.CommonCallback<String> callback){
        if (taskId == null || "".equals(taskId)){
            taskId = AppSharedpreferencesUtils.get("G_TASK_ID", "").toString();
        }
        return uploadFilePrivate(context, eventType,filePath,taskId,callback);
    }


    private static boolean uploadFilePrivate(Context context, String eventType, String filePath, String taskId, Callback.CommonCallback<String> callback){
        if(!NetworkUtil.isConnected(context)){
            I.e(TAG,">>>Netword is not connect");
            return false;
        }
        if(filePath==null || filePath.equals("null") || filePath.trim().equals("")){
            I.e(TAG,">>>Filepath is null,exit upload");
            return false;
        }
        File file = new File(filePath);
        if(file.exists()){
            boolean video = false;
            if(filePath.contains(".mp4")||filePath.contains(".ts")){
                video = true;
            }

            RequestParams params = new RequestParams(getServerUrlBase()+ (video == true? AppConsts.API_UPLOADS:AppConsts.API_UPLOAD));
            RequestCommonParamsDto dto1 = null;
            if (SocketCarBindService.pubDto == null){
                dto1 = new RequestCommonParamsDto();
                dto1.setDwjd("0");
                dto1.setLatitude(AppSharedpreferencesUtils.get(AppConsts.CAR_LOCAL_LAT,"-1").toString());
                dto1.setLongitude(AppSharedpreferencesUtils.get(AppConsts.CAR_LOCAL_LNG,"-1").toString());
                dto1.setGpsjd(AppSharedpreferencesUtils.get(AppConsts.CAR_LOCAL_PRE,"0").toString());
                dto1.setFxj(AppSharedpreferencesUtils.get(AppConsts.CAR_LOCAL_DIRECTION,"0").toString());
                dto1.setSpeed(AppSharedpreferencesUtils.get(AppConsts.CAR_LOCAL_SPEED,"0").toString());
            }else{
                dto1 = SocketCarBindService.pubDto;
            }
            String deviceId  = CarApplication.getDeviceIMEI(context);
            params.addQueryStringParameter("deviceId", deviceId);
            params.addQueryStringParameter("channelId", PushManager.getInstance().getClientid(context));
            params.addQueryStringParameter("longitude",dto1.getLongitude());
            params.addQueryStringParameter("latitude",dto1.getLatitude());
            params.addQueryStringParameter("taskId",taskId);
            params.addQueryStringParameter("speed",dto1.getSpeed());
            params.addQueryStringParameter("direction",dto1.getFxj());
            params.addQueryStringParameter("eventType",eventType);
            params.setConnectTimeout(60*1000);//60秒超时
            params.setMaxRetryCount(1);//重试1次
            params.setMultipart(true);
            params.setExecutor(signThread);
            if(video){

                List<KeyValue> list = new ArrayList<KeyValue>();
                list.add(new KeyValue("file",file));
                MultipartBody body=new MultipartBody(list,"UTF-8");
                params.setRequestBody(body);
            }else {
                params.addBodyParameter(
                        "file",
                        new File(filePath),
                        null); // 如果文件没有扩展名, 最好设置contentType参数.
            }
            x.http().post(params, callback);
            return true;
        }else{
            //不存在的话，删除数据库记录
            LocalFilesModelDao dao = new LocalFilesModelDao();
            dao.deleteLikeFileName(filePath);

            return false;//文件不存在
        }
    }

    public static boolean pushGpsInfoByLocalDb(List<RequestCommonParamsDto> lists, Callback.CommonCallback<String> callback){
        RequestCommonParamsDto dto1 = SocketCarBindService.socketCarBindService.getPubDto();
        if(lists!=null && !lists.isEmpty()){
            for (int i = 0;i<lists.size();i++){
                if(lists.get(i).getLatitude().equals("-1")){
                    lists.get(i).setLongitude(dto1.getLongitude());
                    lists.get(i).setLatitude(dto1.getLatitude());
                    lists.get(i).setSpeed(dto1.getSpeed());
                    lists.get(i).setFxj(dto1.getFxj());
                    lists.get(i).setDwjd(dto1.getDwjd());
                    lists.get(i).setGpsjd(dto1.getGpsjd());
                }
                lists.get(i).setEndTime(TimeUtils.getNowDateTime());
            }
        }
        RequestParams params = new RequestParams(getServerUrlBase()+AppConsts.API_GPSINFOALL);
        params.setAsJsonContent(true);
        params.setConnectTimeout(3*1000);//3秒超时
        params.setExecutor(signThread);
        // params.setUseCookie(false);
        params.setBodyContent(JSON.toJSONString(lists));
        //I.d(TAG,"POST DATA--->"+JSON.toJSONString(lists));

        x.http().post(params,callback);
        return true;
    }



    /**
     * 上传GPS或者速度信息
     * @param dto
     * @param callback
     * @return
     */
    public static boolean pushGpsInfo(Context context, RequestCommonParamsDto dto, Callback.CommonCallback<String> callback){
        RequestCommonParamsDto dto1 = null;
        if (SocketCarBindService.pubDto == null){
            dto1 = new RequestCommonParamsDto();
            dto1.setLatitude(AppSharedpreferencesUtils.get(AppConsts.CAR_LOCAL_LAT,"-1").toString());
            dto1.setLongitude(AppSharedpreferencesUtils.get(AppConsts.CAR_LOCAL_LNG,"-1").toString());
            dto1.setGpsjd(AppSharedpreferencesUtils.get(AppConsts.CAR_LOCAL_PRE,"0").toString());
            dto1.setFxj(AppSharedpreferencesUtils.get(AppConsts.CAR_LOCAL_DIRECTION,"0").toString());
            dto1.setSpeed(AppSharedpreferencesUtils.get(AppConsts.CAR_LOCAL_SPEED,"0").toString());
            dto1.setDwjd("0");
        }else{
            dto1 = SocketCarBindService.pubDto;
        }
        String APP_IMEI = CarApplication.getDeviceIMEI(context);
        String APP_SIMCID = CarApplication.getSimICCID(context);
        dto.setDeviceId(APP_IMEI);
        dto.setDeviceTag(APP_SIMCID);
        //I.e(TAG,"APP_SIMCID:"+APP_SIMCID);
        dto.setLatitude(dto1.getLatitude());
        dto.setLongitude(dto1.getLongitude());
        dto.setSpeed(dto1.getSpeed());
        dto.setFxj(dto1.getFxj());
        dto.setDwjd(dto1.getDwjd());
        dto.setGpsjd(dto1.getGpsjd());
        dto.setChannelId(PushManager.getInstance().getClientid(context));
        dto.setEndTime(TimeUtils.getNowDateTime());
        dto.setStartTime(TimeUtils.getNowDateTime());
        //dto.setEventType(getCarGPSFlag());//设置GPS 状态[车辆状态]
        if (AppConsts.CAR_ON.equals(dto.getEventType())){
            dto.setSczt("10");
        }else if (AppConsts.CAR_OFF.equals(dto.getEventType()) || SocketCarBindService.socketCarBindService == null){
            dto.setSczt("20");
        }else{
            dto.setSczt(SocketCarBindService.socketCarBindService.getCarGPSFlag());
        }

        dto.setCmdParams(CarApplication.getVersionStr(context));
        if(!NetworkUtil.isConnected(context)){
            RequestCommonParamsDtoDao dao = new RequestCommonParamsDtoDao();
            dao.insertObj(dto);
            I.e(TAG,">>>Netword is not connect");
            return false;
        }

        String gpsurl = getServerUrlBase()+AppConsts.API_GPSINFO;
        RequestParams params = new RequestParams(gpsurl);
        params.setAsJsonContent(true);
        params.setConnectTimeout(3*1000);//3秒超时
        params.setExecutor(signThread);
       // params.setUseCookie(false);
        params.setBodyContent(JSON.toJSONString(dto));
        //I.d(TAG,"POST URL --->"+gpsurl);
        //I.d(TAG,"POST DATA--->"+JSON.toJSONString(dto));
        if(APP_IMEI==null || APP_IMEI.trim().equals("")){
            I.e(TAG,"APP_IMEI is null");
            return false;
        }
        x.http().post(params,callback);
        return true;
    }

    public static boolean pushGpsInfoSleep(Context context, RequestCommonParamsDto dto){
        RequestCommonParamsDto dto1 = null;
        try{
            if (SocketCarBindService.pubDto == null){
                dto1 = new RequestCommonParamsDto();
                dto1.setLatitude(AppSharedpreferencesUtils.get(AppConsts.CAR_LOCAL_LAT,"-1").toString());
                dto1.setLongitude(AppSharedpreferencesUtils.get(AppConsts.CAR_LOCAL_LNG,"-1").toString());
                dto1.setGpsjd(AppSharedpreferencesUtils.get(AppConsts.CAR_LOCAL_PRE,"0").toString());
                dto1.setFxj(AppSharedpreferencesUtils.get(AppConsts.CAR_LOCAL_DIRECTION,"0").toString());
                dto1.setSpeed(AppSharedpreferencesUtils.get(AppConsts.CAR_LOCAL_SPEED,"0").toString());
                dto1.setDwjd("0");
            }else{
                dto1 = SocketCarBindService.pubDto;
            }
            String APP_IMEI = CarApplication.getDeviceIMEI(context);
            String APP_SIMCID = CarApplication.getSimICCID(context);
            dto.setDeviceId(APP_IMEI);
            dto.setDeviceTag(APP_SIMCID);
            //I.e(TAG,"APP_SIMCID:"+APP_SIMCID);
            dto.setLatitude(dto1.getLatitude());
            dto.setLongitude(dto1.getLongitude());
            dto.setSpeed(dto1.getSpeed());
            dto.setFxj(dto1.getFxj());
            dto.setDwjd(dto1.getDwjd());
            dto.setGpsjd(dto1.getGpsjd());
            dto.setChannelId(PushManager.getInstance().getClientid(context));
            dto.setEndTime(TimeUtils.getNowDateTime());
            dto.setStartTime(TimeUtils.getNowDateTime());
            //dto.setEventType(getCarGPSFlag());//设置GPS 状态[车辆状态]
            if (AppConsts.CAR_ON.equals(dto.getEventType())){
                dto.setSczt("10");
            }else if (AppConsts.CAR_OFF.equals(dto.getEventType()) || SocketCarBindService.socketCarBindService == null){
                String zt = AppSharedpreferencesUtils.get(AppConsts.CAR_GOTO_SLEEP,"00").toString();
                if ("00".equals(zt)){
                    dto.setSczt("10");
                }else{
                    dto.setSczt("20");
                }
            }else{
                dto.setSczt(SocketCarBindService.socketCarBindService.getCarGPSFlag());
            }

            dto.setCmdParams(CarApplication.getVersionStr(context));
            String gpsurl = getServerUrlBase() + AppConsts.API_GPSINFO;
            RequestParams params = new RequestParams(gpsurl);
            params.setAsJsonContent(true);
            params.setConnectTimeout(2*1000);//2秒超时
            params.setExecutor(signThread);
            // params.setUseCookie(false);
            params.setBodyContent(JSON.toJSONString(dto));
            //I.e(TAG,"POST URL --->"+gpsurl);
            if(APP_IMEI==null || APP_IMEI.trim().equals("")){
                I.e(TAG,"APP_IMEI is null");
                return false;
            }
            x.http().post(params, gpsInfoCallback);
        } catch (Throwable throwable) {
            //I.e("SocketSleep", "error:"+throwable.getMessage());
        }

        return true;
    }

    /**
     * 文件上传【上传完成之后即关闭】
     */
    public static Callback.CommonCallback<String> fileUploadCallback = new Callback.CommonCallback<String>() {
        @Override
        public void onSuccess(String result) {
            AppSharedpreferencesUtils.remove("G_TASK_ID");
            /*try{
                JSONObject jsonObject = JSON.parseObject(result,JSONObject.class);
                if(jsonObject.containsKey("code")&&jsonObject.getString("code").equals("200")){
                    RequestCommonParamsDto dto = JSON.parseObject(jsonObject.getString("result"),RequestCommonParamsDto.class);
                    LocalFilesModelDao dao = new LocalFilesModelDao();
                    dao.deleteLikeFileName(dto.getFileRealName());
                }

                I.d(TAG, "文件updateFile success:" + result );
            }catch (Exception e){
                I.e(TAG, "文件updateFile success:" + e );
            }*/

        }

        @Override
        public void onError(Throwable ex, boolean isOnCallback) {
            //I.e(TAG, "文件updateFile error:" + ex.getLocalizedMessage() );
            AppSharedpreferencesUtils.remove("G_TASK_ID");
        }

        @Override
        public void onCancelled(CancelledException cex) {

        }

        @Override
        public void onFinished() {
            System.gc();
        }
    };

    /**
     * 文件上传【上传完成之后触发下一次上传】
     */
    public static Callback.CommonCallback<String> fileUploadLoopCallback = new Callback.CommonCallback<String>() {
        @Override
        public void onSuccess(String result) {
        }

        @Override
        public void onError(Throwable ex, boolean isOnCallback) {
        }

        @Override
        public void onCancelled(CancelledException cex) {
        }

        @Override
        public void onFinished() {
            System.gc();
        }
    };

    public static Callback.CommonCallback<String> fileUploadImgCallback = new Callback.CommonCallback<String>() {
        @Override
        public void onSuccess(String result) {
        }

        @Override
        public void onError(Throwable ex, boolean isOnCallback) {
        }

        @Override
        public void onCancelled(CancelledException cex) {

        }

        @Override
        public void onFinished() {
            System.gc();
        }
    };

    public static Callback.CommonCallback<String> gpsInfoCallback = new Callback.CommonCallback<String>() {
        @Override
        public void onSuccess(String result) {
            //I.e(TAG,"POST result --->"+result);
        }

        @Override
        public void onError(Throwable ex, boolean isOnCallback) {
            //I.e(TAG+"upload gps error:",ex);
        }

        @Override
        public void onCancelled(CancelledException cex) {

        }

        @Override
        public void onFinished() {

        }
    };

    public static Callback.CommonCallback<String> gpsInfoAllCallback = new Callback.CommonCallback<String>() {
        @Override
        public void onSuccess(String result) {
            RequestCommonParamsDtoDao dao = new RequestCommonParamsDtoDao();
            dao.deleteList(dao.findAll());

        }

        @Override
        public void onError(Throwable ex, boolean isOnCallback) {
            //SocketCarBindService.socketCarBindService.setGpsNoNetWorkFlag("10");//需要进行检测上传了
            //I.e(TAG+"upload gps error:",ex);
        }

        @Override
        public void onCancelled(CancelledException cex) {

        }

        @Override
        public void onFinished() {
            System.gc();
        }
    };
}
