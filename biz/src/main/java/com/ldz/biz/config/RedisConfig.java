package com.ldz.biz.config;

import com.ldz.biz.module.service.*;
import com.ldz.util.redis.RedisTemplateUtil;
import com.ldz.util.spring.SpringContextUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.annotation.Order;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.listener.PatternTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.listener.Topic;

import java.util.ArrayList;
import java.util.List;

/**
 * redis配置
 * @author 李彬彬
 *
 */
@Configuration
@Order(1)
public class RedisConfig {

	@Value("${apiurl}")
	public  String url;
	@Value("${znzpurl}")
	public  String znzpurl;
	@Value("${biz_url: }") // : http://47.98.39.45:8080
	public  String bizurl;

	@Autowired
	private RedisConnectionFactory redisConnectionFactory;


	private RedisTemplateUtil redisTemplateUtil;



	/**
	 * 本项目缓存Redis对象
	 * @return
	 */
	@Bean
	@Primary
	public RedisTemplateUtil redisTemplateDefault(){
		redisTemplateUtil = new RedisTemplateUtil(redisConnectionFactory);
		return redisTemplateUtil;
	}
	/**
	 * redis消息监听器容器
	 * 可以添加多个监听不同话题的redis监听器，只需要把消息监听器和相应的消息订阅处理器绑定，该消息监听器
	 * 通过反射技术调用消息订阅处理器的相关方法进行一些业务处理
	 * @param connectionFactory
	 * @return
	 */
	@Bean //相当于xml中的bean
	public RedisMessageListenerContainer container(RedisConnectionFactory connectionFactory) {
		System.out.println("container");
		RedisMessageListenerContainer container = new RedisMessageListenerContainer();
		container.setConnectionFactory(connectionFactory);

		//订阅了一个叫chat 的通道
		List<Topic> topics = new ArrayList<>();
		topics.add(new PatternTopic("gps"));
		topics.add(new PatternTopic("spk"));

		// 订阅过期 topic
		// 设置监听的Topic
		PatternTopic channelTopic = new PatternTopic("__keyevent@*__:expired");
		SpkService spkService = SpringContextUtil.getBean(SpkService.class);
		GpsService gpsservice = SpringContextUtil.getBean(GpsService.class);
		MessageReceiver messageReceiver = new MessageReceiver(spkService,gpsservice,redisTemplateUtil);

		XcService xcService = SpringContextUtil.getBean(XcService.class);
		ClYyService clYyService = SpringContextUtil.getBean(ClYyService.class);
		GpsLsService gpsLsService = SpringContextUtil.getBean(GpsLsService.class);
		ZdglService zdglService = SpringContextUtil.getBean(ZdglService.class);
		//topicMessageListener.setRedisTemplate(redisTemplateUtil);
		container.addMessageListener(messageReceiver, topics);
		container.addMessageListener(new TopicMessageListener(xcService,clYyService,gpsLsService,zdglService,redisTemplateUtil,url,znzpurl,bizurl) , channelTopic);
		//这个container 可以添加多个 messageListener
		return container;
	}

	@Bean
	public static PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() {
		return new PropertySourcesPlaceholderConfigurer();
	}
}
