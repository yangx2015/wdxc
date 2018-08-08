package com.ldz.biz.config;


import com.ldz.biz.module.bean.GpsInfo;
import com.ldz.biz.module.service.GpsService;
import com.ldz.biz.module.service.SpkService;
import com.ldz.util.bean.RequestCommonParamsDto;
import com.ldz.util.redis.RedisTemplateUtil;
import lombok.val;
import org.springframework.beans.BeanUtils;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;


public class MessageReceiver  implements MessageListener {

	private SpkService spkService;

    private GpsService gpsservice;

	private RedisTemplateUtil redisTemplate;

	public MessageReceiver(SpkService spkService, GpsService gpsservice, RedisTemplateUtil redisTemplate) {
		this.spkService = spkService;
		this.gpsservice = gpsservice;
		this.redisTemplate = redisTemplate;
	}

	@Override
	public void onMessage(Message message, byte[] pattern) {
		val redisChannel = redisTemplate.getStringSerializer().deserialize(message.getChannel());
		val eventMessage = redisTemplate.getValueSerializer().deserialize(message.getBody());
		System.out.println(eventMessage);
		String topic = redisChannel;
		// String topic = Arrays.toString(pattern);
        GpsInfo gpsInfo = new GpsInfo();
        RequestCommonParamsDto dto = (RequestCommonParamsDto) eventMessage;
        BeanUtils.copyProperties(dto,gpsInfo);
		switch (topic){
			case "spk":
				spkService.saveSpk(gpsInfo);
				break;
			case "gps":
                gpsservice.onReceiveGps(gpsInfo);
				break;
		}
		System.out.println("收到一条消息："+redisChannel);
	}

}
