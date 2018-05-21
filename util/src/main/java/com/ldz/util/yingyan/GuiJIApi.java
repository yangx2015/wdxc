package com.ldz.util.yingyan;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.ldz.util.bean.AddPointResponse;
import com.ldz.util.bean.TrackPoint;
import com.ldz.util.bean.TrackPoints;
import com.ldz.util.bean.YingyanResponse;
import com.ldz.util.bean.YyEntity;
import com.ldz.util.commonUtil.HttpUtil;
import com.ldz.util.commonUtil.JsonUtil;

public class GuiJIApi {
  
    public final static String AK = "2pVOrCuBldNDOgDtwaYSP8gpQ2VQdZY9";

    public final static String SERVICE_ID = "200383";

    public final static  String  saveEntityuRL="http://yingyan.baidu.com/api/v3/entity/add";
  
    public final static  String  updateEntityuRL="http://yingyan.baidu.com/api/v3/entity/update";
    
    public final static  String  deleteEntityuRL="http://yingyan.baidu.com/api/v3/entity/delete";

    public final static String listEntityURL = "http://yingyan.baidu.com/api/v3/entity/list";

    public final static  String addPointsURL = "http://yingyan.baidu.com/api/v3/track/addpoints";


    public final static  String addPointURL = "http://yingyan.baidu.com/api/v3/track/addpoint";
    
    public static YingyanResponse changeEntity(YyEntity entity,String url){
    	
    	Map<String, String> beanmap = new HashMap<>();
    	
    	beanmap.put("ak", entity.getAk());
    	beanmap.put("service_id", entity.getService_id());
    	beanmap.put("entity_name", entity.getEntity_name());
    	if (StringUtils.isNotEmpty(entity.getEntity_desc())) {
    		beanmap.put("entity_desc", entity.getEntity_desc());
		}
    
    	String postJson = null;
        try {
			 postJson = HttpUtil.post(url, beanmap);
		} catch (Exception e) {
			e.printStackTrace();
		}
        
        YingyanResponse bean = JsonUtil.toBean(postJson, YingyanResponse.class);


      return bean;
    }

    public static  AddPointResponse addPoints(TrackPoints entity , String url){
		Map<String, String> beanmap = new HashMap<>();

		beanmap.put("ak", entity.getAk());
		beanmap.put("service_id", entity.getService_id());
		beanmap.put("point_list", entity.getPoint_list());
		String postJson = null;
		try {
			postJson = HttpUtil.post(url, beanmap);
		} catch (Exception e) {
			e.printStackTrace();
		}

		AddPointResponse addPointResponse = JsonUtil.toBean(postJson,AddPointResponse.class);

    	return addPointResponse;

	}


	public static YingyanResponse addPoint(TrackPoint entity , String url){

		Map<String, String> beanmap = new HashMap<>();

		beanmap.put("ak", entity.getAk());
		beanmap.put("service_id", entity.getService_id());
		beanmap.put("entity_name",entity.getEntity_name());
		beanmap.put("coord_type_input",entity.getCoord_type_input());
		beanmap.put("latitude",entity.getLatitude().toString());
		beanmap.put("loc_time",entity.getLoc_time().toString());
		beanmap.put("longitude",entity.getLongitude().toString());
		beanmap.put("_object_key",entity.get_object_key());
		String postJson = null;
		try {
			postJson = HttpUtil.post(url, beanmap);
		} catch (Exception e) {
			e.printStackTrace();
		}

		YingyanResponse addPointResponse = JsonUtil.toBean(postJson,YingyanResponse.class);
 
	return addPointResponse;

	}

  public static void main(String[] args) {
	  YyEntity yyEntity = new YyEntity();
	  yyEntity.setAk(AK);
	  yyEntity.setService_id(SERVICE_ID);
	  yyEntity.setEntity_name("songlingyun");
	  yyEntity.setGpsid(null);
	  YingyanResponse changeEntity = changeEntity(yyEntity, saveEntityuRL);
	  System.out.println(changeEntity);
}




}
