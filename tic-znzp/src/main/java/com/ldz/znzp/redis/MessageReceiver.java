package com.ldz.znzp.redis;


import com.ldz.util.bean.ApiResponse;
import com.ldz.util.bean.RequestCommonParamsDto;
import com.ldz.util.commonUtil.DateUtils;
import com.ldz.util.redis.RedisTemplateUtil;
import com.ldz.znzp.bean.GpsInfo;
import com.ldz.znzp.model.ClCl;
import com.ldz.znzp.model.ClClyxjl;
import com.ldz.znzp.model.ClPb;
import com.ldz.znzp.model.ClXl;
import com.ldz.znzp.service.ClService;
import com.ldz.znzp.service.ClyxjlService;
import com.ldz.znzp.service.XlService;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;

import java.util.Date;
import java.util.List;

@Slf4j
public class MessageReceiver implements MessageListener {
	Logger errorLog = LoggerFactory.getLogger("error_info");

	@Autowired
	private ClService clService;

	@Autowired
	private XlService xlService;
	@Autowired
	private ClyxjlService clyxjlService;

	private RedisTemplateUtil redisTemplate;

	public MessageReceiver(RedisTemplateUtil redisTemplate, ClService clService, XlService xlService, ClyxjlService clyxjlService) {
		this.redisTemplate = redisTemplate;
		this.clService=clService;
		this.xlService=xlService;
		this.clyxjlService=clyxjlService;
	}

	@Override
	public void onMessage(Message message, byte[] pattern) {
		try {


		val topic = redisTemplate.getStringSerializer().deserialize(message.getChannel());
		val eventMessage = redisTemplate.getValueSerializer().deserialize(message.getBody());
		log.error("订阅的topic:"+topic);
		log.error("订阅的值:"+eventMessage.toString());
		System.out.println(eventMessage);
		// String topic = Arrays.toString(pattern);
        GpsInfo gpsInfo = new GpsInfo();
        RequestCommonParamsDto dto = (RequestCommonParamsDto) eventMessage;
        BeanUtils.copyProperties(dto,gpsInfo);
		switch (topic){
			case "gps":
				if (StringUtils.isEmpty(gpsInfo.getDeviceId())){
					return ;
				}
				if (StringUtils.isEmpty(gpsInfo.getLongitude())){
					return ;
				}
				if (StringUtils.isEmpty(gpsInfo.getLatitude())){
					return ;
				}

				ClCl car = clService.getByDeviceId(gpsInfo.getDeviceId());
				if (car == null)return  ;

				List<ClClyxjl> clClyxjls = clyxjlService.findEq(ClClyxjl.InnerColumn.clId,car.getClId());

				ClPb pb = clService.getCarPb(car.getClId());
				if (pb == null)return ;

				ClXl route = xlService.findById(pb.getXlId());
				if (route == null)return ;
				String yxkssj =route.getYxkssj();//运行开始时间
				String yxjssj =route.getYxjssj();//运行结束时间
				if(StringUtils.isNotEmpty(yxkssj)&&StringUtils.isNotEmpty(yxjssj)){
					try {
						Date ksDate = DateUtils.getDate(DateUtils.getToday()+" "+yxkssj,"yyyy-MM-dd HH:mm");
						ksDate=new Date(ksDate.getTime() - 1000*60*5);//当前时间向前推5分钟
						Date jsDate= DateUtils.getDate(DateUtils.getToday()+" "+yxjssj,"yyyy-MM-dd HH:mm");
						jsDate= new Date(jsDate.getTime()+ 1000*60*30);//当前时间向后推30分钟
						//开始时间
						if(ksDate==null || ksDate.compareTo(new Date())>0 ||jsDate==null || new Date().compareTo(jsDate)>0){
							return ;
						}
					} catch (Exception e) {
						e.printStackTrace();
					}
				}

				ClClyxjl clClyxjl = clClyxjls.size() == 0 ? null : clClyxjls.get(0);

				ApiResponse<String> res = clService.updateGps(gpsInfo,pb,car,route,clClyxjl);
				if (!res.isSuccess())return ;
				xlService.checkRouteInfo(route);
				clService.report(gpsInfo.getDeviceId(),pb,car,route,clClyxjl);
				break;
		}
		System.out.println("收到一条消息："+topic);
		}catch (Exception e){
			e.printStackTrace();
			errorLog.error("智能站牌，REIDS消息订阅报出异常",e);
		}
	}
}