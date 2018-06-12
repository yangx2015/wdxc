package com.ldz.biz.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.annotation.Order;
import org.springframework.data.redis.connection.RedisConnectionFactory;

import com.ldz.util.redis.RedisTemplateUtil;
import org.springframework.data.redis.listener.PatternTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;

/**
 * redis配置
 * @author 李彬彬
 *
 */
@Configuration
@Order(1)
public class RedisConfig {
	
	@Autowired
	private RedisConnectionFactory redisConnectionFactory;

	/**
	 * 本项目缓存Redis对象
	 * @return
	 */
	@Bean
	@Primary
	public RedisTemplateUtil redisTemplateDefault(){
		RedisTemplateUtil bean = new RedisTemplateUtil(redisConnectionFactory);
		return bean;
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
		RedisMessageListenerContainer container = new RedisMessageListenerContainer();
		container.setConnectionFactory(connectionFactory);
		//订阅了一个叫chat 的通道
		container.addMessageListener(new MessageReceiver(redisTemplateDefault()), new PatternTopic("gps"));
		//这个container 可以添加多个 messageListener
		return container;
	}
}
