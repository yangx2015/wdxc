package cn.bidostar.ticserver.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import org.xutils.http.app.ResponseParser;
import org.xutils.http.request.UriRequest;

import java.lang.reflect.Type;

public class JsonResponseParser implements ResponseParser {
    public Object parse(Type resultType, Class<?> resultClass, String result) throws Throwable {
        try {
            if(result == null) {
                return null;
            }
        }catch (Exception e) {
            return null;
        }
 
        return JSON.parseObject(result, resultClass);
    }
 
    public void checkResponse(UriRequest request) throws Throwable {
 
    }
}