package com.ldz.sys.util;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.ldz.util.commonUtil.HttpUtil;

import lombok.extern.slf4j.Slf4j;

/**
 * auther chenwei
 * create at 2018/5/6
 */
@Slf4j
@Component
public class RedisUtil {

    public void deleteRedisKey(String url,String key){
        Map<String, String> params = new HashMap<>();
        params.put("key",key);
        try{
        	String resultJson = HttpUtil.post(url, params);
        	System.out.println(resultJson);
        }catch (Exception e){
            log.error("deleteRedisKey 请求异常",e);
        }
    }
}
