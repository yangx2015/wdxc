package com.ldz.util.yingyan;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.MediaType;

import com.ldz.util.bean.YingyanResponse;
import com.ldz.util.bean.YyEntity;
import com.ldz.util.commonUtil.HttpUtil;
import com.ldz.util.commonUtil.JsonUtil;

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
	YyEntity yyEntity= new  YyEntity();
	yyEntity.setAk(AK);
	yyEntity.setService_id(SERVICE_ID);
	yyEntity.setEntity_name("赤焰军-宋老狗");
	
	YingyanResponse addEntity = addEntity(yyEntity);
	System.out.println(addEntity);
}







}
