package com.ldz.biz.config;


import com.ldz.util.redis.RedisTemplateUtil;
import lombok.val;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.stereotype.Component;


@Component
public class MessageReceiver  implements MessageListener {
	private RedisTemplateUtil redisTemplate;

	public MessageReceiver(RedisTemplateUtil redisTemplate) {
		this.redisTemplate = redisTemplate;
	}

	@Override
	public void onMessage(Message message, byte[] pattern) {
		val redisChannel = redisTemplate.getStringSerializer().deserialize(message.getChannel());
		val eventMessage = redisTemplate.getValueSerializer().deserialize(message.getBody());
		//System.out.println(message);
		System.out.println(eventMessage);
    	/*ObjectMapper mapper = new ObjectMapper();
    	RequestCommonParamsDto result = new RequestCommonParamsDto();
		try {
			result = (RequestCommonParamsDto)mapper.readValue(message, RequestCommonParamsDto.class);
		} catch (JsonParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JsonMappingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
		System.out.println("收到一条消息："+redisChannel);
	}
}