package com.ldz.biz.config;


import com.ldz.biz.module.bean.GpsInfo;
import com.ldz.biz.module.service.GpsService;
import com.ldz.biz.module.service.PbService;
import com.ldz.biz.module.service.SpkService;
import com.ldz.util.bean.RequestCommonParamsDto;
import com.ldz.util.redis.RedisTemplateUtil;
import lombok.val;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;


public class MessageReceiver  implements MessageListener {

	Logger errorLog = LoggerFactory.getLogger("error_info");
	private SpkService spkService;

    private GpsService gpsservice;
    private PbService pbService;

	private RedisTemplateUtil redisTemplate;

	public MessageReceiver(SpkService spkService, GpsService gpsservice,PbService pbService, RedisTemplateUtil redisTemplate) {
		this.spkService = spkService;
		this.gpsservice = gpsservice;
		this.pbService = pbService;
		this.redisTemplate = redisTemplate;
	}

	@Override
	public void onMessage(Message message, byte[] pattern) {
		val redisChannel = redisTemplate.getStringSerializer().deserialize(message.getChannel());
		val eventMessage = redisTemplate.getValueSerializer().deserialize(message.getBody());
		String topic = redisChannel;
		if(StringUtils.equals("removePbJOB",topic)){
			errorLog.error("收到排班消息:"+eventMessage.toString());
			pbService.removePbList(eventMessage.toString());
		}else{
			// String topic = Arrays.toString(pattern);
			GpsInfo gpsInfo = new GpsInfo();
			RequestCommonParamsDto dto = (RequestCommonParamsDto) eventMessage;
			errorLog.error("dto:"+dto.toString());
			BeanUtils.copyProperties(dto,gpsInfo);
			gpsInfo.setSpeed(Integer.parseInt(dto.getSpeed()));
			switch (topic){
				case "spk":
					spkService.saveSpk(gpsInfo);
					break;
				case "gps":
					gpsservice.onReceiveGps(gpsInfo);
					break;
			}

		}
		System.out.println("收到一条消息："+redisChannel);
	}

}
