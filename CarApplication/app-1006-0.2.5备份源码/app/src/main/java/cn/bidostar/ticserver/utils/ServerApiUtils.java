package cn.bidostar.ticserver.utils;

import android.content.Context;
import android.content.Intent;
import android.os.SystemClock;

import com.alibaba.fastjson.JSON;
import cn.bidostar.ticserver.CarApplication;
import cn.bidostar.ticserver.MainActivity;
import cn.bidostar.ticserver.dao.LocalFilesModelDao;
import cn.bidostar.ticserver.dao.RequestCommonParamsDtoDao;
import cn.bidostar.ticserver.model.RequestCommonParamsDto;
import cn.bidostar.ticserver.service.SocketCarBindService;
import com.igexin.sdk.PushManager;

import org.xutils.common.Callback;
import org.xutils.common.util.KeyValue;
import org.xutils.http.RequestParams;
import org.xutils.http.body.MultipartBody;
import org.xutils.x;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import cn.bidostar.ticserver.CarApplication;
import cn.bidostar.ticserver.MainActivity;
import cn.bidostar.ticserver.model.RequestCommonParamsDto;

/**
 * Created by admins on 2018/3/12.
 */

public class ServerApiUtils {

    public static String TAG = "ServerApiUtils";
    //网络请求线程队列，同时只让一个线程执行上传操作
    public Executor signThread = null;
    //使用volatile能够保证可见性
    private volatile static ServerApiUtils mSingleton = null;
    private ServerApiUtils () {
        signThread = Executors.newFixedThreadPool(1);
    }

    /**
     * 单实例，避免内存泄露
     * @return
     */
    public static ServerApiUtils getInstance() {
        if (mSingleton == null) {
            synchronized (ServerApiUtils.class) {
                if (mSingleton == null) {
                    mSingleton = new ServerApiUtils();
                }
            }
        }

        return mSingleton;
    }

    public void downLoadAppApk(final String url, final String path){
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
                Intent intent = new Intent(CarIntents.ACTION_APK_INSTALL);
                intent.putExtra(CarIntents.EXTRA_PATH_INSTALL, path);
                intent.putExtra(CarIntents.EXTRA_PACKAGE_INSTALL, "cn.bidostar.ticserver");
                intent.putExtra(CarIntents.EXTRA_CLASS_INSTALL, MainActivity.class.getName());
                CarApplication.getApplication().sendBroadcast(intent);

                String zt = AppSharedpreferencesUtils.get(AppConsts.CAR_GOTO_SLEEP,"10").toString();
                if ("10".equals(zt)){
                    Intent intenttmp = new Intent("com.car.syswakeup");
                    intenttmp.putExtra("reason", "recordvideo");
                    CarApplication.getApplication().sendBroadcast(intenttmp);
                }

                Intent serviceTwo = new Intent();
                serviceTwo.setClass(CarApplication.getApplication().getApplicationContext(), SocketCarBindService.class);
                CarApplication.getApplication().getApplicationContext().startService(serviceTwo);
                // 退出程序
                android.os.Process.killProcess(android.os.Process.myPid());
                System.exit(0);
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
    public boolean uploadFile(String eventType, String filePath, Callback.CommonCallback<String> callback){
        return uploadFile(eventType,filePath,"",callback);
    }

    /**
     * 上传之后，不进行循环
     * @param eventType
     * @param filePath
     * @param taskId
     * @param callback
     * @return
     */
    public boolean uploadFile(String eventType, String filePath, String taskId, Callback.CommonCallback<String> callback){
        if (taskId == null || "".equals(taskId)){
            taskId = AppSharedpreferencesUtils.get("G_TASK_ID", "").toString();
        }
        return uploadFilePrivate(eventType,filePath,taskId,callback);
    }


    private boolean uploadFilePrivate(String eventType, String filePath, String taskId, Callback.CommonCallback<String> callback){
        if(!NetworkUtil.isConnected(CarApplication.getApplication().getApplicationContext())){
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

            RequestParams params = new RequestParams(CarApplication.getApplication().getServerUrlBase()+ (video == true? AppConsts.API_UPLOADS:AppConsts.API_UPLOAD));
            RequestCommonParamsDto dto1 =  CarApplication.getApplication().getPubDto();

            String deviceId  = CarApplication.getApplication().getDeviceIMEI();
            params.addQueryStringParameter("deviceId", deviceId);
            params.addQueryStringParameter("channelId", CarApplication.getApplication().getChannelId());
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
            I.e(TAG,"file not found");
            //不存在的话，删除数据库记录
            LocalFilesModelDao dao = new LocalFilesModelDao();
            dao.deleteLikeFileName(filePath);

            return false;//文件不存在
        }
    }

    public boolean pushGpsInfoByLocalDb(final List<RequestCommonParamsDto> lists){
        String postJson = null;
        try{
            final List<Long> deleteIds = new ArrayList<Long>();
            if(lists!=null && !lists.isEmpty()){
                RequestCommonParamsDto dto1 = CarApplication.getApplication().getPubDto();
                for (int i = 0;i<lists.size();i++){
                    RequestCommonParamsDto rpd = lists.get(i);
                    if(rpd.getLatitude().equals("-1")){
                        rpd.setLongitude(dto1.getLongitude());
                        rpd.setLatitude(dto1.getLatitude());
                        rpd.setSpeed(dto1.getSpeed());
                        rpd.setFxj(dto1.getFxj());
                        rpd.setDwjd(dto1.getDwjd());
                        rpd.setGpsjd(dto1.getGpsjd());
                    }
                    rpd.setEndTime(TimeUtils.getNowDateTime());

                    deleteIds.add(new Long(rpd.getId()));
                }
            }else{
                return false;
            }

            String url = CarApplication.getApplication().getServerUrlBase()+AppConsts.API_GPSINFOALL;
            postJson = JSON.toJSONString(lists);
            RequestParams params = new RequestParams(url);
            params.setAsJsonContent(true);
            params.setConnectTimeout(5*1000);//3秒超时
            params.setExecutor(signThread);
            // params.setUseCookie(false);
            params.setBodyContent(postJson);

            x.http().post(params, new Callback.CommonCallback<String>() {
                @Override
                public void onSuccess(String result) {
                    try{
                        AppDBUtils.getDB().delete(lists);

                        lists.clear();
                    }catch (Exception e){
                        I.e(TAG+"gpsInfoAllCallback:",e);
                    }
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
        }catch (Exception e){
            I.e("ServiceAPi-pushGpsInfoByLocalDb", e);
        } catch (Throwable throwable) {
            I.e("ServiceAPi-pushGpsInfoByLocalDb", throwable);
        }finally {
            postJson = null;
        }

        return true;
    }



    /**
     * 上传GPS或者速度信息
     * @param dto
     * @param callback
     * @return
     */
    public boolean pushGpsInfo(RequestCommonParamsDto dto, Callback.CommonCallback<String> callback){
        try{
            RequestCommonParamsDto dto1 = CarApplication.getApplication().getPubDto();
            String APP_IMEI = CarApplication.getApplication().getDeviceIMEI();
            if(APP_IMEI==null || APP_IMEI.trim().equals("")){
                //I.e(TAG,"APP_IMEI is null");
                return false;
            }
            String APP_SIMCID = CarApplication.getApplication().getSimICCID();
            dto.setDeviceId(APP_IMEI);
            dto.setDeviceTag(APP_SIMCID);
            //I.e(TAG,"APP_SIMCID:"+APP_SIMCID);
            dto.setLatitude(dto1.getLatitude());
            dto.setLongitude(dto1.getLongitude());
            dto.setSpeed(dto1.getSpeed());
            dto.setFxj(dto1.getFxj());
            dto.setDwjd(dto1.getDwjd());
            dto.setGpsjd(dto1.getGpsjd());
            dto.setChannelId(CarApplication.getApplication().getChannelId());
            dto.setEndTime(TimeUtils.getNowDateTime());
            dto.setStartTime(TimeUtils.getNowDateTime());
            //dto.setEventType(getCarGPSFlag());//设置GPS 状态[车辆状态]
            if (AppConsts.CAR_ON.equals(dto.getEventType())){
                dto.setSczt("10");
            }else if (AppConsts.CAR_OFF.equals(dto.getEventType())){
                dto.setSczt("20");
            }else{
                dto.setSczt(CarApplication.getApplication().getCarGPSFlag());
            }

            dto.setCmdParams(CarApplication.getApplication().getAPPVersionCode());
            if(!NetworkUtil.isConnected(CarApplication.getApplication())){
                RequestCommonParamsDtoDao dao = new RequestCommonParamsDtoDao();
                dao.insertObj(dto);
                //I.e(TAG,">>>Netword is not connect");
                return false;
            }

            String gpsurl =  CarApplication.getApplication().getServerUrlBase()+AppConsts.API_GPSINFO;
            String jsonData = JSON.toJSONString(dto);
            /*2018年11月8日。使用xutils操作*/
            RequestParams params = new RequestParams(gpsurl);
            params.setAsJsonContent(true);
            params.setConnectTimeout(3*1000);//3秒超时
            params.setExecutor(signThread);
            params.setBodyContent(jsonData);
            x.http().post(params,callback);
        }catch (Exception e){
            I.e("ServiceAPi-pushGpsInfo", e);
        } catch (Throwable throwable) {
            I.e("ServiceAPi-pushGpsInfo", throwable);
        }

        return true;
    }

    public boolean headtBeat(){
        try{
            I.e("JobSchedulerService", "<<<<<<<headtBeat service api>>>>>>>");
            String gpsurl =  CarApplication.getApplication().getServerUrlBase() + "/device/other";
            I.e("JobSchedulerService", "<<<<<<<"+gpsurl+">>>>>>>");
            RequestParams params = new RequestParams(gpsurl);
            params.setAsJsonContent(true);
            params.setConnectTimeout(2*1000);//2秒超时
            RequestCommonParamsDto dto = new RequestCommonParamsDto();
            dto.setDeviceId(CarApplication.getApplication().getDeviceIMEI());
            String jsonData = JSON.toJSONString(dto);
            I.e("JobSchedulerService", "[service api]<<<<<<<"+jsonData+">>>>>>>");
            params.setBodyContent(jsonData);
            x.http().post(params, null);
        }catch (Exception e){
            I.e("ServiceAPI", " sleep gps exception>>>>"+e.getMessage());
            return false;
        } catch (Throwable throwable) {
            I.e("ServiceAPI", " sleep gps exception>>>>"+throwable.getMessage());
            return false;
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
            I.e(TAG, "updateFile error:" + ex.getLocalizedMessage() );
            AppSharedpreferencesUtils.remove("G_TASK_ID");
        }

        @Override
        public void onCancelled(CancelledException cex) {
            AppSharedpreferencesUtils.remove("G_TASK_ID");
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
}
