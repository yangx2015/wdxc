package com.ldz.util.yingyan;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.ldz.util.bean.AddPointResponse;
import com.ldz.util.bean.TrackJiuPian;
import com.ldz.util.bean.TrackPoint;
import com.ldz.util.bean.TrackPoints;
import com.ldz.util.bean.TrackPointsForReturn;
import com.ldz.util.bean.YingyanResponse;
import com.ldz.util.bean.YyEntity;
import com.ldz.util.commonUtil.HttpUtil;
import com.ldz.util.commonUtil.JsonUtil;

public class GuiJIApi {
  
    public final static String AK = "2pVOrCuBldNDOgDtwaYSP8gpQ2VQdZY9";

    public final static int SERVICE_ID = 200383;

    public final static  String  saveEntityuRL="http://yingyan.baidu.com/api/v3/entity/add";
  
    public final static  String  updateEntityuRL="http://yingyan.baidu.com/api/v3/entity/update";
    
    public final static  String  deleteEntityuRL="http://yingyan.baidu.com/api/v3/entity/delete";

    public final static String listEntityURL = "http://yingyan.baidu.com/api/v3/entity/list";

    public final static  String addPointsURL = "http://yingyan.baidu.com/api/v3/track/addpoints";

    public final static  String addPointURL = "http://yingyan.baidu.com/api/v3/track/addpoint";

    public final static String getPointsURL = "http://yingyan.baidu.com/api/v3/track/gettrack";

    //status:3005 设备已存在  status:0 新增成功  
    public static YingyanResponse changeEntity(YyEntity entity,String url){
    	
    	Map<String, String> beanmap = new HashMap<>();
    	
    	beanmap.put("ak", entity.getAk());
    	beanmap.put("service_id", entity.getService_id() +"");
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
		beanmap.put("service_id", entity.getService_id()+"");
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
		beanmap.put("service_id", entity.getService_id()+"");
		beanmap.put("entity_name",entity.getEntity_name());
		beanmap.put("coord_type_input",entity.getCoord_type_input());
		beanmap.put("latitude",entity.getLatitude()+"");
		beanmap.put("loc_time",entity.getLoc_time()+"");
		beanmap.put("longitude",entity.getLongitude()+"");
		beanmap.put("_object_key",entity.get_object_key());
		beanmap.put("speed",entity.getSpeed());
		beanmap.put("direction",entity.getDirection());
		String postJson = null;
		try {
			postJson = HttpUtil.post(url, beanmap);
		} catch (Exception e) {
			e.printStackTrace();
		}

		YingyanResponse addPointResponse = JsonUtil.toBean(postJson,YingyanResponse.class);

		return addPointResponse;
		//System.out.println(addPointResponse);

	}


	public static TrackPointsForReturn getPoints(TrackJiuPian entity , String url){

		Map<String, String> beanmap = new HashMap<>();

		beanmap.put("ak", entity.getAk());
		beanmap.put("service_id", entity.getService_id()+"");
		beanmap.put("entity_name",entity.getEntity_name());
		beanmap.put("start_time",entity.getStart_time());
		beanmap.put("end_time",entity.getEnd_time()+"");
		beanmap.put("is_processed",entity.getIs_processed()+"");
		beanmap.put("process_option",entity.getProcess_option());
		beanmap.put("supplement_mode",entity.getSupplement_mode());
		beanmap.put("sort_type",entity.getSort_type());
		beanmap.put("coord_type_output",entity.getCoord_type_output());

		String postJson = null;
		try {
			postJson = HttpUtil.get(url, beanmap);
		} catch (Exception e) {
			e.printStackTrace();
		}

		TrackPointsForReturn addPointResponse = JsonUtil.toBean(postJson,TrackPointsForReturn.class);

		return addPointResponse;

	}

  public static void main(String[] args) {
	  
	  YyEntity yyEntity=  new YyEntity();
	  yyEntity.setAk(AK);
	  yyEntity.setEntity_name("865923030032376");
	  yyEntity.setService_id(SERVICE_ID);
	
	   YingyanResponse changeEntity = changeEntity(yyEntity, saveEntityuRL);
	  
	  System.out.println(changeEntity);
	  
	  
	  
}


}
