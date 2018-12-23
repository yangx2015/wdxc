package com.bidostar.ticserver.service;

import android.content.Context;
import android.content.Intent;

import com.alibaba.fastjson.JSON;
import com.bidostar.ticserver.model.RequestCommonParamsDto;
import com.bidostar.ticserver.utils.AppSharedpreferencesUtils;
import com.bidostar.ticserver.utils.I;
import com.igexin.sdk.GTIntentService;
import com.igexin.sdk.message.BindAliasCmdMessage;
import com.igexin.sdk.message.FeedbackCmdMessage;
import com.igexin.sdk.message.GTCmdMessage;
import com.igexin.sdk.message.GTNotificationMessage;
import com.igexin.sdk.message.GTTransmitMessage;
import com.igexin.sdk.message.SetTagCmdMessage;
import com.igexin.sdk.message.UnBindAliasCmdMessage;

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
        I.e(TAG, "onReceiveServicePid -> " + pid);
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
       if (payload == null) {
            //I.e(TAG, "receiver payload = null");
        } else {
            String data = new String(payload);
            I.e(TAG,">>>>>>>>>>>>>>>>>>"+data);
            try {

            }catch (Exception e){
                I.e(TAG, e.getMessage());
            }
        }
    }

    @Override
    public void onReceiveClientId(Context context, String clientid) {
        //I.e(TAG, "onReceiveClientId -> " + "clientid = " + clientid);

        sendMessage(clientid, 1);
    }

    @Override
    public void onReceiveOnlineState(Context context, boolean online) {
        I.e(TAG, "onReceiveOnlineState -> " + (online ? "online" : "offline"));
        //AppSharedpreferencesUtils.put(AppConsts.PUSH_ZT,(online ? "online" : "offline"));
        //如果推送服务离线了，则开启抓锁，保证网络正常可用
        /*if (!online){
            Intent intent = new Intent("com.car.mobiledata");
            intent.putExtra("enable", true);
            sendBroadcast(intent);
        }*/
    }

    @Override
    public void onReceiveCommandResult(Context context, GTCmdMessage cmdMessage) {

    }

    @Override
    public void onNotificationMessageArrived(Context context, GTNotificationMessage message) {
    }

    @Override
    public void onNotificationMessageClicked(Context context, GTNotificationMessage message) {
    }

    private void setTagResult(SetTagCmdMessage setTagCmdMsg) {
        String sn = setTagCmdMsg.getSn();
        String code = setTagCmdMsg.getCode();
    }

    private void bindAliasResult(BindAliasCmdMessage bindAliasCmdMessage) {
        String sn = bindAliasCmdMessage.getSn();
        String code = bindAliasCmdMessage.getCode();


    }

    private void unbindAliasResult(UnBindAliasCmdMessage unBindAliasCmdMessage) {
        String sn = unBindAliasCmdMessage.getSn();
        String code = unBindAliasCmdMessage.getCode();


    }


    private void feedbackResult(FeedbackCmdMessage feedbackCmdMsg) {
        String appid = feedbackCmdMsg.getAppid();
        String taskid = feedbackCmdMsg.getTaskId();
        String actionid = feedbackCmdMsg.getActionId();
        String result = feedbackCmdMsg.getResult();
        long timestamp = feedbackCmdMsg.getTimeStamp();
        String cid = feedbackCmdMsg.getClientId();

    }

    private void sendMessage(String data, int what) {
        /*Message msg = Message.obtain();
        msg.what = what;
        msg.obj = data;
        DemoApplication.sendMessage(msg);*/
    }


    @Override
    public void onLowMemory() {
        super.onLowMemory();
        I.e(TAG, "onLowMemory");
    }
}
