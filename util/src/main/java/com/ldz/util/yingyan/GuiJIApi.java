package com.ldz.util.yingyan;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.*;

import com.ldz.util.bean.YingyanResponse;
import com.ldz.util.bean.YyEntity;
import com.ldz.util.commonUtil.HttpUtil;
import com.ldz.util.commonUtil.JsonUtil;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

public class GuiJIApi {

    private final static String AK = "2pVOrCuBldNDOgDtwaYSP8gpQ2VQdZY9";

    private final static String SERVICE_ID = "200383";

    private final static  String  saveEntityuRL="http://yingyan.baidu.com/api/v3/entity/add";
  
    public static YingyanResponse addEntity(YyEntity entity){
    	
    	Map<String, String> postHeaders = new HashMap<>();
    	postHeaders.put("Content-Type", MediaType.APPLICATION_JSON_VALUE);
    	String josnEntity = JsonUtil.toJson(entity);
    	String postJson = null;
        try {
			 postJson = HttpUtil.postJson(saveEntityuRL, postHeaders, josnEntity);
		} catch (Exception e) {
			e.printStackTrace();
		}
        
        YingyanResponse bean = JsonUtil.toBean(postJson, YingyanResponse.class);


      return bean;
    }

public static void main(String[] args) {


}







}
