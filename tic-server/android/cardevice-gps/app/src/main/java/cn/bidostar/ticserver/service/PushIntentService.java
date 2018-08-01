package cn.bidostar.ticserver.service;

import android.content.Context;
import android.content.Intent;
import android.os.Message;
import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.igexin.sdk.GTIntentService;
import com.igexin.sdk.PushConsts;
import com.igexin.sdk.PushManager;
import com.igexin.sdk.message.BindAliasCmdMessage;
import com.igexin.sdk.message.FeedbackCmdMessage;
import com.igexin.sdk.message.GTCmdMessage;
import com.igexin.sdk.message.GTNotificationMessage;
import com.igexin.sdk.message.GTTransmitMessage;
import com.igexin.sdk.message.SetTagCmdMessage;
import com.igexin.sdk.message.UnBindAliasCmdMessage;
import com.miramems.carmotion.carMotion;

import cn.bidostar.ticserver.AppApplication;
import cn.bidostar.ticserver.TestActivity;
import cn.bidostar.ticserver.model.RequestCommonParamsDto;
import cn.bidostar.ticserver.utils.AppConsts;
import cn.bidostar.ticserver.utils.AppSharedpreferencesUtils;
import cn.bidostar.ticserver.utils.I;
import cn.bidostar.ticserver.utils.ServerApiUtils;
import cn.bidostar.ticserver.utils.TimeUtils;
import cn.bidostar.ticserver.utils.Utils;

/**
 * 继承 GTIntentService 接收来自个推的消息, 所有消息在线程中回调, 如果注册了该服务, 则务必要在 AndroidManifest中声明, 否则无法接受消息<br>
 * onReceiveMessageData 处理透传消息<br>
 * onReceiveClientId 接收 cid <br>
 * onReceiveOnlineState cid 离线上线通知 <br>
 * onReceiveCommandResult 各种事件处理回执 <br>
 */
public class PushIntentService extends GTIntentService {
    private static final String TAG = "PushIntentService";
    /**
     * 为了观察透传数据变化.
     */
    private static int cnt;

    public PushIntentService() {
    }

    @Override
    public void onReceiveServicePid(Context context, int pid) {
        Log.d(TAG, "onReceiveServicePid -> " + pid);
    }

    @Override
    public void onReceiveMessageData(Context context, GTTransmitMessage msg) {
        String appid = msg.getAppid();
        String taskid = msg.getTaskId();
        String messageid = msg.getMessageId();
        byte[] payload = msg.getPayload();
        String pkg = msg.getPkgName();
        String cid = msg.getClientId();

        // 第三方回执调用接口，actionid范围为90000-90999，可根据业务场景执行
        //boolean result = PushManager.getInstance().sendFeedbackMessage(context, taskid, messageid, 90001);
        //Log.d(TAG, "call sendFeedbackMessage = " + (result ? "success" : "failed"));

        I.e(TAG, "onReceiveMessageData -> " + "appid = " + appid + "\ntaskid = " + taskid + "\nmessageid = " + messageid + "\npkg = " + pkg
                + "\ncid = " + cid);

        if (payload == null) {
            I.e(TAG, "receiver payload = null");
        } else {
            String data = new String(payload);
            I.e(TAG, "receiver payload = " + data);
            try {
                RequestCommonParamsDto dto = JSON.parseObject(data,RequestCommonParamsDto.class);
                if(dto == null || dto.getCmdType()==null || dto.getCmdType().trim().equals("")){

                }else{
                    //这里处理具体的命令
                    boolean isRun = Utils.isServiceWork(AppApplication.getContext(),
                            "cn.bidostar.ticserver.service.SocketCarBindService");
                    I.e(TAG,"isRun SocketCarBindService:"+isRun);
                    switch (dto.getCmdType()){
                        case "01"://01：超速设定 02:灵敏度设定(急加速灵敏度)   11:拍视频 12:拍图片   20 碰撞灵敏度
                            if(isRun){
                                double s = 10.00;
                                try{
                                    s = Double.parseDouble(dto.getCmd());
                                }catch (Exception e){
                                    s = 10.00;
                                }
                                SocketCarBindService.socketCarBindService.setCarSpeed(s);

                                //TestActivity.activity.takePicture(dto.getCmd());
                            }
                            break;
                        case "02"://灵敏度设定(急加速灵敏度)
                            if(AppApplication.getInstance()!=null&&isRun){
                                if(dto.getCmd()!=null){
                                    try{
                                        SocketCarBindService.socketCarBindService.mApi.setViolent_Set_Parma(Integer.parseInt(dto.getCmd()));//恢复默认2
                                    }catch (Exception e){
                                        SocketCarBindService.socketCarBindService.mApi.setViolent_Set_Parma(2);//恢复默认2
                                    }
                                }else{
                                    SocketCarBindService.socketCarBindService.mApi.setViolent_Set_Parma(2);//恢复默认2
                                }
                            }
                            break;
                        case "20"://碰撞灵敏度
                            if(isRun){
                                if(dto.getCmd()==null || dto.getCmd().equals("00")){
                                    SocketCarBindService.socketCarBindService.mApi.setCollisionSensitivity(API.CollisionSensitivityLow);
                                }else if(dto.getCmd()!=null && dto.getCmd().equals("10")){
                                    SocketCarBindService.socketCarBindService.mApi.setCollisionSensitivity(API.CollisionSensitivityNormal);
                                }else if(dto.getCmd()!=null && dto.getCmd().equals("20")){
                                    SocketCarBindService.socketCarBindService.mApi.setCollisionSensitivity(API.CollisionSensitivityHigh);
                                }else{
                                    SocketCarBindService.socketCarBindService.mApi.setCollisionSensitivity(API.CollisionSensitivityLow);
                                }

                            }
                            break;
                        case "11"://抓取视频（当前时间点前后十秒）
                            if(isRun){
                                int carm =  0;
                                int sec = 10;//默认前后10秒
                                try{
                                    String cmdParams = dto.getCmdParams();
                                    if(cmdParams==null || cmdParams.trim().equals("") || !cmdParams.contains("-")){
                                        cmdParams = "0-10";
                                    }
                                    String[] tmp = cmdParams.split("-");
                                    carm = Integer.valueOf(tmp[0]);
                                    sec = Integer.valueOf(tmp[1]);
                                }catch (Exception e){
                                    carm = 0;
                                    I.e(TAG,"抓取视频（当前时间点前后十秒） error:"+e);
                                }
                                SocketCarBindService.socketCarBindService.CAR_HB_MP4_TASKID  = dto.getCmd();
                                SocketCarBindService.socketCarBindService.takeVideo(dto.getCmd(),carm,sec);

                            }
                            break;
                        case "12"://拍照（当前时间点的拍照）
                            if(isRun){
                                int carm =  0;
                                try{
                                    String cmdParams = dto.getCmdParams();
                                    if(cmdParams==null || cmdParams.trim().equals("") || !cmdParams.contains("-")){
                                        cmdParams = "0-0";
                                    }
                                    carm = Integer.valueOf(cmdParams.split("-")[0]);
                                }catch (Exception e){
                                    carm = 0;
                                    I.e(TAG,"拍照（当前时间点的拍照） error:"+e);
                                }
                                SocketCarBindService.socketCarBindService.CAR_HB_MP4_TASKID  = dto.getCmd();
                                SocketCarBindService.socketCarBindService.takePicture(dto.getCmd(),carm);
                            }
                            break;
                        case "13"://抓拍指定时间的视频
                            if(AppApplication.getInstance()!=null && isRun){
                                SocketCarBindService.socketCarBindService.CAR_HB_MP4_TASKID  = dto.getTaskId();
                                try{
                                    int carm =  0;
                                    String cmdParams = dto.getCmdParams();
                                    if(cmdParams==null || cmdParams.trim().equals("") || !cmdParams.contains("-")){
                                        cmdParams = "0-0";
                                    }
                                    carm = Integer.valueOf(cmdParams.split("-")[0]);
                                    if(carm>2){
                                        carm = 0;
                                    }
                                    SocketCarBindService.socketCarBindService.mApi.startCar_WAKEUP();
                                    SocketCarBindService.socketCarBindService.mApi.takeVideo(carm,new Long(TimeUtils.stringtoDate(dto.getStartTime()).getTime()/1000).intValue() ,new Long(TimeUtils.stringtoDate(dto.getEndTime()).getTime()/1000).intValue(),true,false);
                                }catch (Exception e){
                                    I.e(TAG,"抓拍出错 error:"+e);
                                }
                            }
                            break;
                        case "30"://设置上传模式【暂时无用】
                            if(AppApplication.getInstance()!=null){
                                if(dto.getCmd()!=null && dto.getCmd().equals("10")){
                                   // AppApplication.getInstance().
                                }
                            }

                            break;
                        case "40"://GPS心跳间隔
                            if(AppApplication.getInstance()!=null && isRun){
                                int s = 0;
                                try{
                                    s = Integer.parseInt(dto.getCmd());
                                }catch (Exception e){
                                    s = 2;
                                    I.e(TAG,"GPS心跳间隔 error:"+e);
                                }
                                SocketCarBindService.socketCarBindService.setTimerRun(s);
                            }
                            break;
                        case "50"://设置上传模式
                            if(AppApplication.getInstance()!=null && isRun){
                                if(dto.getCmd()!=null && dto.getCmd().equals("10")){
                                    AppApplication.getInstance().setCarUploadMp4Model(1);
                                }else if(dto.getCmd()!=null && dto.getCmd().equals("20")){
                                    AppApplication.getInstance().setCarUploadMp4Model(0);
                                }else if(dto.getCmd()!=null && dto.getCmd().equals("30")){
                                    AppApplication.getInstance().setCarUploadMp4Model(2);
                                }else{
                                    AppApplication.getInstance().setCarUploadMp4Model(0);
                                }
                            }
                            break;
                        case "90"://apk更新
                            if(AppApplication.getInstance()!=null){
                                ServerApiUtils.downLoadAppApk(dto.getCmd(),"/mnt/sdcard/carserver.apk");
                            }
                            break;
                        case "91"://设置app提交数据得baseServer http://127.0.0.1:8089/api 这种路径
                            if(AppApplication.getInstance()!=null){
                                if(dto.getCmd()!=null && dto.getCmd().contains("http")) {//判断是否是http开头
                                    AppSharedpreferencesUtils.put(AppConsts.CAR_BASE_SERVER_URL, dto.getCmd());
                                }
                            }
                            break;
                        case "92"://服务器推送播报语音内容
                            if(AppApplication.getInstance()!=null&&isRun){
                                try{
                                    SocketCarBindService.socketCarBindService.mApi.playTts(dto.getCmd());
                                }catch (Exception e){

                                }
                            }
                            break;
                        case "95"://清除数据库信息
                            if(AppApplication.getInstance()!=null){
                                try{
                                    AppApplication.getInstance().clearDatabase();
                                }catch (Exception e){

                                }
                            }
                            break;
                        case "96"://清空缓存，类似恢复apk原始数据
                            if(AppApplication.getInstance()!=null){
                                try{
                                    AppSharedpreferencesUtils.clear();//
                                }catch (Exception e){

                                }
                            }
                            break;
                        case "97"://客户端上传指定日志文件【作为服务器检查日志使用】
                            if(AppApplication.getInstance()!=null){
                                try{
                                    //97 的时候是上传日志 dto.getCmd() 为具体的日志路径，直接后台指定就可以了。
                                    ServerApiUtils.uploadFile("97",dto.getCmd(),ServerApiUtils.fileUploadImgCallback);
                                }catch (Exception e){

                                }
                            }
                            break;
                        default:
                            break;
                    }
                }
            }catch (Exception e){

            }


            sendMessage(data, 0);
        }

        I.d(TAG, "----------------------------------------------------------------------------------------------");
    }

    @Override
    public void onReceiveClientId(Context context, String clientid) {
        I.e(TAG, "onReceiveClientId -> " + "clientid = " + clientid);

        sendMessage(clientid, 1);
    }

    @Override
    public void onReceiveOnlineState(Context context, boolean online) {
        I.d(TAG, "onReceiveOnlineState -> " + (online ? "online" : "offline"));
    }

    @Override
    public void onReceiveCommandResult(Context context, GTCmdMessage cmdMessage) {
        I.d(TAG, "onReceiveCommandResult -> " + cmdMessage);

        int action = cmdMessage.getAction();

        if (action == PushConsts.SET_TAG_RESULT) {
            setTagResult((SetTagCmdMessage) cmdMessage);
        } else if(action == PushConsts.BIND_ALIAS_RESULT) {
            bindAliasResult((BindAliasCmdMessage) cmdMessage);
        } else if (action == PushConsts.UNBIND_ALIAS_RESULT) {
            unbindAliasResult((UnBindAliasCmdMessage) cmdMessage);
        } else if ((action == PushConsts.THIRDPART_FEEDBACK)) {
            feedbackResult((FeedbackCmdMessage) cmdMessage);
        }
    }

    @Override
    public void onNotificationMessageArrived(Context context, GTNotificationMessage message) {
        I.d(TAG, "onNotificationMessageArrived -> " + "appid = " + message.getAppid() + "\ntaskid = " + message.getTaskId() + "\nmessageid = "
                + message.getMessageId() + "\npkg = " + message.getPkgName() + "\ncid = " + message.getClientId() + "\ntitle = "
                + message.getTitle() + "\ncontent = " + message.getContent());
    }

    @Override
    public void onNotificationMessageClicked(Context context, GTNotificationMessage message) {
        I.d(TAG, "onNotificationMessageClicked -> " + "appid = " + message.getAppid() + "\ntaskid = " + message.getTaskId() + "\nmessageid = "
                + message.getMessageId() + "\npkg = " + message.getPkgName() + "\ncid = " + message.getClientId() + "\ntitle = "
                + message.getTitle() + "\ncontent = " + message.getContent());
    }

    private void setTagResult(SetTagCmdMessage setTagCmdMsg) {
        String sn = setTagCmdMsg.getSn();
        String code = setTagCmdMsg.getCode();


        I.d(TAG, "settag result sn = " + sn + ", code = " + code + ", text = ");
    }

    private void bindAliasResult(BindAliasCmdMessage bindAliasCmdMessage) {
        String sn = bindAliasCmdMessage.getSn();
        String code = bindAliasCmdMessage.getCode();


        I.d(TAG, "bindAlias result sn = " + sn + ", code = " + code + ", text = ");

    }

    private void unbindAliasResult(UnBindAliasCmdMessage unBindAliasCmdMessage) {
        String sn = unBindAliasCmdMessage.getSn();
        String code = unBindAliasCmdMessage.getCode();


        I.d(TAG, "unbindAlias result sn = " + sn + ", code = " + code + ", text = ");

    }


    private void feedbackResult(FeedbackCmdMessage feedbackCmdMsg) {
        String appid = feedbackCmdMsg.getAppid();
        String taskid = feedbackCmdMsg.getTaskId();
        String actionid = feedbackCmdMsg.getActionId();
        String result = feedbackCmdMsg.getResult();
        long timestamp = feedbackCmdMsg.getTimeStamp();
        String cid = feedbackCmdMsg.getClientId();

        I.d(TAG, "onReceiveCommandResult -> " + "appid = " + appid + "\ntaskid = " + taskid + "\nactionid = " + actionid + "\nresult = " + result
                + "\ncid = " + cid + "\ntimestamp = " + timestamp);
    }

    private void sendMessage(String data, int what) {
        /*Message msg = Message.obtain();
        msg.what = what;
        msg.obj = data;
        DemoApplication.sendMessage(msg);*/
    }


}
