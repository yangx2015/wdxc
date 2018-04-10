package com.ldz.znzp.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ldz.znzp.bean.ZnzpOnlineBean;
import com.ldz.znzp.server.IotServer;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.nio.charset.Charset;
import java.util.Iterator;
import java.util.Set;

@Slf4j
@Component
public class NettyUtil {

    @Autowired
    private StringRedisTemplate redisDao;
    private static ObjectMapper mapper = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);


    public void sendData(ChannelHandlerContext ctx, Object sendData){
        writeDataByte(ctx, sendData);
    }
    public void sendData(Channel channel, Object sendData){
        writeDataByte(channel, sendData);
    }

    public  Channel getChannelByTid(String tid){
//        Set<String> keys =  redisDao.keys(tid+"-*-"+ZnzpOnlineBean.class.getSimpleName());
        Set<String> keys =  redisDao.keys("*-"+ZnzpOnlineBean.class.getSimpleName());
        if (keys.size() == 0)return null;
        Iterator<String> keyIt = keys.iterator();
        String key = keyIt.next();
        String[] part = key.split("-");
        String cid = part[1];
        for (Channel channel : IotServer.onlineChannels) {
            if (channel.id().asShortText().equals(cid)) {
                return channel;
            }
        }
        return null;
    }

    private void writeDataByte(ChannelHandlerContext ctx, Object sendData){
        if (ctx == null)return;
        try{
            ByteBuf data = buildData(sendData);
            ctx.write(data);
            ctx.flush();
        }catch (Exception e){
            log.error("发送数据异常",e);
        }
    }
    private void writeDataByte(Channel channel, Object sendData){
        if (channel == null)return;
        try{
            ByteBuf data = buildData(sendData);
            channel.write(data);
            channel.flush();
        }catch (Exception e){
            log.error("发送数据异常",e);
        }
    }

    private ByteBuf buildData(Object sendData){
        String content = "";
        try {
            content = mapper.writeValueAsString(sendData);
        } catch (JsonProcessingException e) {

        }
        int length = Unpooled.copiedBuffer(content.getBytes(Charset.forName("GBK"))).array().length;
        String data = "$" + StringUtils.leftPad(length+"", 5, "0") + content;
        return Unpooled.copiedBuffer(data.getBytes(Charset.forName("GBK")));
    }

}
