package com.ldz.biz.module.service.impl;

import com.ldz.biz.module.bean.ObdMessageBean;
import com.ldz.biz.module.service.ObdService;
import com.ldz.sys.base.BaseServiceImpl;
import com.ldz.util.bean.ApiResponse;
import com.ldz.util.bean.GpsObdMessageBean;
import com.ldz.util.redis.RedisTemplateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.common.Mapper;

@Service
public class ObdServiceImpl extends BaseServiceImpl<ObdMessageBean,String> implements ObdService {
    @Autowired
    private RedisTemplate redisDao;
    @Autowired
    private RedisTemplateUtil redis;


    @Value("${gpsObdMessage-key:gpsObdMessage_}")
    private String gpsObdMessage;

    @Override
    protected Mapper<ObdMessageBean> getBaseMapper() {
        return null;
    }

    public ApiResponse<Object> getObdTimely(String obdId){
        GpsObdMessageBean obds=new GpsObdMessageBean();
        try {
           obds=  (GpsObdMessageBean)redisDao.opsForValue().get(gpsObdMessage+obdId);
        }catch (Exception e){
            e.printStackTrace();
        }
        return ApiResponse.success(obds);
    }
}
