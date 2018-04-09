package com.ldz.ticserver.plugins.push;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.gexin.fastjson.JSON;
import com.gexin.rp.sdk.base.IPushResult;
import com.gexin.rp.sdk.base.IQueryResult;
import com.gexin.rp.sdk.base.ITemplate;
import com.gexin.rp.sdk.base.impl.AppMessage;
import com.gexin.rp.sdk.base.impl.ListMessage;
import com.gexin.rp.sdk.base.impl.SingleMessage;
import com.gexin.rp.sdk.base.impl.Target;
import com.gexin.rp.sdk.base.payload.APNPayload;
import com.gexin.rp.sdk.exceptions.RequestException;
import com.gexin.rp.sdk.http.IGtPush;
import com.gexin.rp.sdk.template.LinkTemplate;
import com.gexin.rp.sdk.template.NotificationTemplate;
import com.gexin.rp.sdk.template.TransmissionTemplate;
import com.gexin.rp.sdk.template.style.Style0;
import com.ldz.util.commonUtil.JsonUtil;

/**
 * app 推送工具类
 * 
 * @author wanggang
 *
 */
public class AppPushUtils {

	private static String appId = "20P0M5r3FJ6DiUA5x2u3w6";//默认  可提供外部赋值（在app启动完成之后可以读取配置文件得值付给它）
	private static String appKey = "9M7caq81LGAiCxFPkhdZW7";//默认  可提供外部赋值（在app启动完成之后可以读取配置文件得值付给它）
	private static String masterSecret = "BaYW4ASGfa9jcbP4jUYTS7";//默认  可提供外部赋值（在app启动完成之后可以读取配置文件得值付给它）
	private static String url = "http://sdk.open.api.igexin.com/apiex.htm";
	private static Integer EXP_TIME = 24 * 3600 * 1000;// 消息离线有效时间

	public static void setAppId(String appId) {
		AppPushUtils.appId = appId;
	}
	public static void setAppKey(String appKey) {
		AppPushUtils.appKey = appKey;
	}
	public static void setMasterSecret(String masterSecret) {
		AppPushUtils.masterSecret = masterSecret;
	}

	public static PushResult checkIdMessage(String id){
		PushResult result = new PushResult();
		IGtPush push = new IGtPush(url, appKey, masterSecret);
		IQueryResult iq =  push.getClientIdStatus(appId, id);
		String k = JSON.toJSONString(iq.getResponse());
		if(k.toLowerCase().contains("online")){
			//说明在线
		}else{
			result.setCode(11);
		}
		
		return result;
	}
	
	
	
	/**
	 * 推送消息到全部用户或者推送给指定别名的用户
	 * @param pushModel
	 * @return
	 */
	public static PushResult pushMessageAllOrClientByAlias(PushModel pushModel) {
		IGtPush push = new IGtPush(url, appKey, masterSecret);
		PushResult result = new PushResult();
		result.setPushData(pushModel);
		IPushResult ret = null;
		// 所有app都推送
		ITemplate template = geTemplate(pushModel);
	/*	if ((StringUtils.isBlank(pushModel.getClientId()) && (pushModel.getClientIds() == null
				|| pushModel.getClientIds().length < 1)) || pushModel.getPushAll()) {*/
		if (pushModel.getPushAll()) {
			AppMessage message = new AppMessage();
			List<String> appIds = new ArrayList<String>();
			appIds.add(appId);
			message.setData(template);
			message.setAppIdList(appIds);
			message.setOffline(true);
			message.setOfflineExpireTime(EXP_TIME);
			ret = push.pushMessageToApp(message);
			System.out.println(ret.getResponse().toString());
		} else {
			// 推送到指定用户（单个用户）
			if (StringUtils.isNotBlank(pushModel.getClientId())) {
				SingleMessage message = new SingleMessage();
				message.setOffline(true);
				// 离线有效时间，单位为毫秒，可选
				message.setOfflineExpireTime(EXP_TIME);
				message.setData(template);
				// 可选，1为wifi，0为不限制网络环境。根据手机处于的网络情况，决定是否下发
				message.setPushNetWorkType(0);
				Target target = new Target();
				target.setAppId(appId);
				target.setAlias(pushModel.getClientId());
				//target.setClientId();
				// target.setAlias(Alias);

				try {
					ret = push.pushMessageToSingle(message, target);

				} catch (RequestException e) {
					e.printStackTrace();
					ret = push.pushMessageToSingle(message, target, e.getRequestId());
				}
				if (ret != null) {

					System.out.println(ret.getResponse().toString());
				} else {
					System.out.println("服务器响应异常");
				}
			}
			// 批量发送给其它用户
			if (pushModel.getClientIds() != null && pushModel.getClientIds().length >= 1) {
				ListMessage message = new ListMessage();
				message.setData(template);
				// 设置消息离线，并设置离线时间
				message.setOffline(true);
				// 离线有效时间，单位为毫秒，可选
				message.setOfflineExpireTime(EXP_TIME);
				// 配置推送目标
				List targets = new ArrayList();
				for (String cid : pushModel.getClientIds()) {
					Target target1 = new Target();
					target1.setAppId(appId);
					//target1.setClientId(cid);
					target1.setAlias(cid);
					targets.add(target1);
				}
				String taskId = push.getContentId(message);
				ret = push.pushMessageToList(taskId, targets);
				System.out.println(ret.getResponse().toString());
			}
		}
		if (ret != null) {
			if (ret.getResponse() == null || ret.getResponse().toString().indexOf("result=ok") == -1) {
				result.setCode(PushResult.ERROR);
				result.setResultMsg("推送失败");
				result.setResultData(ret.getResponse());
			} else {
				result.setResultData(ret.getResponse());
				result.setResultMsg("推送成功");
			}
		} else {
			result.setCode(PushResult.ERROR);
			result.setResultMsg("推送服务响应异常");
		}
		return result;
	}

	public static ITemplate geTemplate(PushModel pushModel) {
		ITemplate iTemplate = null;
		switch (pushModel.getPushType()) {
		case 1:
			iTemplate = createNotificationTemplate(pushModel);
			break;
		case 2:
			iTemplate = createLinkTemplate(pushModel);
			break;
		case 3:
			iTemplate = createLinkTemplate(pushModel);
			break;
		case 4:
			iTemplate = createTransmissionTemplate(pushModel);
			break;
		default:
			iTemplate = createNotificationTemplate(pushModel);
			break;
		}
		return iTemplate;
	}

	/**
	 * 创建打开网页得消息模板
	 * 
	 * @param pushModel
	 * @return
	 */
	public static LinkTemplate createLinkTemplate(PushModel pushModel) {
		LinkTemplate template = new LinkTemplate();
		// 设置APPID与APPKEY
		template.setAppId(appId);
		template.setAppkey(appKey);
		Style0 style = new Style0();
		// 设置通知栏标题与内容
		style.setTitle(pushModel.getTitle());
		style.setText(pushModel.getText());
		// 配置通知栏图标
		// style.setLogo("icon.png");
		// 配置通知栏网络图标
		// style.setLogoUrl("");
		// 设置通知是否响铃，震动，或者可清除
		style.setRing(true);
		style.setVibrate(true);
		style.setClearable(true);
		template.setStyle(style);
		APNPayload ap = new APNPayload();
		ap.setContentAvailable(1);
		ap.setAlertMsg(new APNPayload.SimpleAlertMsg(pushModel.getTitle()));
		/*
		Map<String,String> mp = new HashMap<>();
		mp.put("title", pushModel.getTitle());
		mp.put("body", pushModel.getText());
		ap.setAlert(JSON.toJSONString(mp));*/
		template.setAPNInfo(ap);
		// 设置打开的网址地址
		template.setUrl(pushModel.getUrl());
		return template;
	}

	/**
	 * 创建提示消息模板
	 * 
	 * @param pushModel
	 * @return
	 */
	public static NotificationTemplate createNotificationTemplate(PushModel pushModel) {
		NotificationTemplate template = new NotificationTemplate();
		// 设置APPID与APPKEY
		template.setAppId(appId);
		template.setAppkey(appKey);
		// 透传消息设置，1为强制启动应用，客户端接收到消息后就会立即启动应用；2为等待应用启动
		template.setTransmissionType(pushModel.getRunAppState());
		if (pushModel.getPushData() instanceof String) {
			template.setTransmissionContent(pushModel.getPushData().toString());
		} else {
			
			template.setTransmissionContent(JsonUtil.toJson(pushModel.getPushData()));
		}
		APNPayload ap = new APNPayload();
		ap.setContentAvailable(0);
		ap.addCustomMsg("iosPush", pushModel.getPushData());
		ap.setAlertMsg(new APNPayload.SimpleAlertMsg(pushModel.getTitle()));
		/*
		Map<String,String> mp = new HashMap<>();
		mp.put("title", pushModel.getTitle());
		mp.put("body", pushModel.getText());
		ap.setAlert(JSON.toJSONString(mp));*/
		template.setAPNInfo(ap);
		// 设置定时展示时间
		// template.setDuration("2015-01-16 11:40:00", "2015-01-16 12:24:00");

		Style0 style = new Style0();
		// 设置通知栏标题与内容
		style.setTitle(pushModel.getTitle());
		style.setText(pushModel.getText());
		// 配置通知栏图标
		// style.setLogo("icon.png");
		// 配置通知栏网络图标
		// style.setLogoUrl("");
		// 设置通知是否响铃，震动，或者可清除
		style.setRing(true);
		style.setVibrate(true);
		style.setClearable(true);
		template.setStyle(style);

		return template;
	}

	/**
	 * 创建透传消息模板
	 * 
	 * @param pushModel
	 * @return
	 */
	public static TransmissionTemplate createTransmissionTemplate(PushModel pushModel) {
		TransmissionTemplate template = new TransmissionTemplate();
		template.setAppId(appId);
		template.setAppkey(appKey);
		// 透传消息设置，1为强制启动应用，客户端接收到消息后就会立即启动应用；2为等待应用启动
		template.setTransmissionType(pushModel.getRunAppState());
		if (pushModel.getPushData() instanceof String) {
			template.setTransmissionContent(pushModel.getPushData().toString());
		} else {
			template.setTransmissionContent(JsonUtil.toJson(pushModel.getPushData()));
		}
		/*
		APNPayload ap = new APNPayload();
		ap.setContentAvailable(1);
		//ap.addCustomMsg("busId", pushModel.getPushData());
		ap.setAlertMsg(new APNPayload.SimpleAlertMsg(pushModel.getTitle()));*/
		/*
		Map<String,String> mp = new HashMap<>();
		mp.put("title", pushModel.getTitle());
		mp.put("body", pushModel.getText());
		ap.setAlert(JSON.toJSONString(mp));*/
		//template.setAPNInfo(ap);
		// 设置定时展示时间
		// template.setDuration("2015-01-16 11:40:00", "2015-01-16 12:24:00");
		return template;
	}

	public static void main(String[] args) {//87aa0967aa2c2e69c60c045479b40c54  e6feafe1ae47694fbb2e64b77402c60a
		System.out.println(JsonUtil.toJson(AppPushUtils
				.pushMessageAllOrClientByAlias(new PushModel(4, "巡检已经完成22", "这是一条消息", "865923030035668", "{\"cmdType\":\"90\",\"cmd\":\"http://192.168.1.148:8089/app-debug.apk\",\"pcRedirect\":\"/module/task-xjd/taskInstance-find-\",\"ifRedirect\":\"00\",\"jsr\":22,\"msgId\":162,\"appRedirect\":\"\",\"msgContent\":\"职能部门管理人员审核、完善整改措施\",\"msgTitle\":\"审核补充巡检单-单号[YGS-ZL-RC-20171213-001]\",\"sjrxm\":\"陶凯\",\"msgFrom\":\"流程启动\",\"state\":\"10\"}"))));
	}

	// public static
}
