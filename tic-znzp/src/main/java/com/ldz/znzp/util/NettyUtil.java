package com.ldz.znzp.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ldz.znzp.bean.ZnzpOnlineBean;
import com.ldz.znzp.server.IotServer;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.nio.charset.Charset;
import java.util.Iterator;
import java.util.Set;

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
        Set<String> keys =  redisDao.keys(tid+"-*-"+ZnzpOnlineBean.class.getSimpleName());
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
        String content = "";
        try {
            content = mapper.writeValueAsString(sendData);
        } catch (JsonProcessingException e) {

        }
        String data = "$" + StringUtils.leftPad(content.length()+"", 5, "0") + content;
        ctx.write(Unpooled.copiedBuffer(data, Charset.forName("GBK")));
        //发送数据同时刷新通道，数据发送完后，由netty自行负责释放该ByteBuf对象
        ctx.flush();
    }
    private void writeDataByte(Channel channel, Object sendData){
        if (channel == null)return;
        String content = "";
        try {
            content = mapper.writeValueAsString(sendData);
        } catch (JsonProcessingException e) {

        }
        String data = "$" + StringUtils.leftPad(content.length()+"", 5, "0") + content;
        channel.write(Unpooled.copiedBuffer(data, Charset.forName("GBK")));
        //发送数据同时刷新通道，数据发送完后，由netty自行负责释放该ByteBuf对象
        channel.flush();
    }

}
