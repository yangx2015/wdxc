package com.ldz.biz.config;


import com.ldz.biz.module.bean.GpsInfo;
import com.ldz.biz.module.service.SpkService;
import com.ldz.util.bean.RequestCommonParamsDto;
import com.ldz.util.redis.RedisTemplateUtil;
import lombok.val;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.stereotype.Component;

import java.util.Arrays;


@Component
public class MessageReceiver  implements MessageListener {

	@Autowired
	private SpkService spkService;
	private RedisTemplateUtil redisTemplate;

	public MessageReceiver(RedisTemplateUtil redisTemplate) {
		this.redisTemplate = redisTemplate;
	}

	@Override
	public void onMessage(Message message, byte[] pattern) {
		val redisChannel = redisTemplate.getStringSerializer().deserialize(message.getChannel());
		val eventMessage = redisTemplate.getValueSerializer().deserialize(message.getBody());
		System.out.println(eventMessage);
		String topic = Arrays.toString(pattern);
		switch (topic){
			case "spk":
				GpsInfo gpsInfo = new GpsInfo();
				RequestCommonParamsDto dto = (RequestCommonParamsDto) eventMessage;
				BeanUtils.copyProperties(dto,gpsInfo);
				spkService.saveSpk(gpsInfo);
				break;
			case "gps":
				break;
		}
		System.out.println("收到一条消息："+redisChannel);
	}
}