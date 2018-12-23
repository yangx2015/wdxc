package cn.bidostar.ticserver.service;

import android.app.job.JobParameters;
import android.app.job.JobService;
import android.content.Intent;

public class JobSchedulerService extends JobService {

    @Override
    public boolean onStartJob(JobParameters mJobParameters) {
        //唤醒设备
        Intent intent = new Intent("com.car.syswakeup");
        intent.putExtra("reason", "recordvideo");
        sendBroadcast(intent);
        //ServerApiUtils.getInstance().headtBeat();
        /*try{
            String gpsurl =  CarApplication.getApplication().getServerUrlBase() + "/device/other";
            RequestParams params = new RequestParams(gpsurl);
            params.setAsJsonContent(true);
            params.setConnectTimeout(2*1000);//2秒超时
            RequestCommonParamsDto dto = new RequestCommonParamsDto();
            dto.setDeviceId(CarApplication.getApplication().getDeviceIMEI());
            String jsonData = JSON.toJSONString(dto);
            params.setBodyContent(jsonData);
            x.http().post(params, new Callback.CommonCallback<String>(){
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
                }
            });
            I.e("JobSchedulerService", "[service api] finished");
        }catch (Exception e){
        } catch (Throwable throwable) {
        }*/

        // 返回true，很多工作都会执行这个地方
        return false;
    }

    @Override
    public boolean onStopJob(JobParameters params) {
        // 返回false来销毁这个工作
        return false;
    }

}