package com.ldz.biz.config;

import com.ldz.biz.module.model.ClXc;
import com.ldz.biz.module.service.XcService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.stereotype.Component;

@Component
public class TopicMessageListener implements MessageListener{


    @Autowired
    private XcService xcService;


    /**
     * 根据 redis 失效时间存储行程
     * @param message
     * @param pattern
     */
    @Override
    public void onMessage(Message message, byte[] pattern) {

        byte[] body = message.getBody();// 请使用valueSerializer
        byte[] channel = message.getChannel();
        String topic = new String(channel);
        String itemValue = new String(body);
        if(StringUtils.contains(topic,"expired")){
            String[] vals = itemValue.split(",");
            String zdbh = vals[1];
            String startTime = vals[2];
            String endTime = vals[3];
            ClXc clXc = new ClXc();
            clXc.setClZdbh(zdbh);
            clXc.setXcKssj(startTime);
            clXc.setXcJssj(endTime);
            xcService.saveEntity(clXc);
        }

    }
    
}
