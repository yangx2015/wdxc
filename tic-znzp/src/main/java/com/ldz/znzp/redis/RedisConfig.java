package com.ldz.znzp.redis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.annotation.Order;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;

import com.ldz.util.redis.RedisTemplateUtil;

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
	@Value("${spring.redis.otherDatabase}")
	private int otherDatabase;

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
	 * 从外部提取数据的Redis对象
	 * @return
	 */
	@Bean(name="redisOtherDB")
	public RedisTemplateUtil redisTemplateOtherDB(){
		JedisConnectionFactory jedisConn = (JedisConnectionFactory)redisConnectionFactory;
		JedisConnectionFactory nJedisConn = new JedisConnectionFactory(jedisConn.getPoolConfig());
		nJedisConn.setHostName(jedisConn.getHostName());
		nJedisConn.setPort(jedisConn.getPort());
		nJedisConn.setPassword(jedisConn.getPassword());
		nJedisConn.setUsePool(true);
		nJedisConn.setShardInfo(jedisConn.getShardInfo());
		nJedisConn.setDatabase(otherDatabase);
		
		RedisTemplateUtil bean = new RedisTemplateUtil(nJedisConn);
		return bean;
	}
}
