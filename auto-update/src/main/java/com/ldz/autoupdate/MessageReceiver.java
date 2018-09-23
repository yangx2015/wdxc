package com.ldz.autoupdate;


import com.ldz.autoupdate.util.HttpUtil;
import com.ldz.autoupdate.util.JsonUtil;
import com.ldz.autoupdate.util.LogUtil;
import lombok.val;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.http.MediaType;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;


public class MessageReceiver implements MessageListener {

	private LogUtil logUtil = LogUtil.getInstance();

	private String apiUrl;

	private Config config;

	private RedisTemplateUtil redisTemplate;

	public MessageReceiver(RedisTemplateUtil redisTemplate,Config config) {
		this.config = config;
		this.redisTemplate = redisTemplate;
	}

	@Override
	public void onMessage(Message message, byte[] pattern) {
		val redisChannel = redisTemplate.getStringSerializer().deserialize(message.getChannel());
		val eventMessage = redisTemplate.getValueSerializer().deserialize(message.getBody());
		String topic = redisChannel;
        GpsInfo gpsInfo = new GpsInfo();
        RequestCommonParamsDto dto = (RequestCommonParamsDto) eventMessage;
		logUtil.info("收到redis消息："+dto.toString());
        BeanUtils.copyProperties(dto,gpsInfo);
		switch (topic){
			case "gps":
				List<String> deviceIds = config.getDecviceIds();
				if (!deviceIds.contains(gpsInfo.getDeviceId())){
					logUtil.info("该设备未配置自动升级 ："+gpsInfo.getDeviceId());
					return;
				}
				if (StringUtils.isEmpty(gpsInfo.getCmdParams()) || !gpsInfo.getCmdParams().contains("versionCode")) {
					senUpdate(gpsInfo);
					return;
				}
				Map<String, Object> map = JsonUtil.toMap(gpsInfo.getCmdParams());
				if (map == null) {
					senUpdate(gpsInfo);
					return;
				}
				if (!map.containsKey("versionCode") || !map.containsKey("versionName")) {
					senUpdate(gpsInfo);
					return;
				}
				String versionCode = map.get("versionCode").toString();
				float currentVersion = Float.parseFloat(versionCode);
				String targetVersionStr = config.getConfigMap().get("targetVersion");
				float targetVersion = Float.parseFloat(targetVersionStr);
				if (currentVersion < targetVersion){
					senUpdate(gpsInfo);
				}
				break;
		}
		System.out.println("收到一条消息："+redisChannel);
	}

	private void senUpdate(GpsInfo info){
		logUtil.info("自动升级终端："+info.getDeviceId());
		logUtil.info("apiUrl："+apiUrl);
		String key = "sendAutoUpdate-"+info.getDeviceId()+"-";
		if (redisTemplate.hasKey(key)){
			logUtil.info("指令发送过于频繁");
			return;
		}
		redisTemplate.boundValueOps(key).set("", 10, TimeUnit.MINUTES);

		Map<String, String> postHeaders = new HashMap<>();
		postHeaders.put("Content-Type", MediaType.APPLICATION_JSON_VALUE);
		try {
			info.setDeviceId(info.getDeviceId());
			String finalPostEntity = JsonUtil.toJson(info);
			HttpUtil.postJson(apiUrl + "/push/carcmd", postHeaders, finalPostEntity);
		} catch (Exception e) {
			logUtil.error("自动升级失败，终端编号：" +info.getDeviceId());
		}
	}

}
