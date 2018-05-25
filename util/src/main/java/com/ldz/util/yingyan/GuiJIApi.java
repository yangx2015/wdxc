package com.ldz.util.yingyan;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.ldz.util.bean.AddPointResponse;
import com.ldz.util.bean.TrackJiuPian;
import com.ldz.util.bean.TrackPoint;
import com.ldz.util.bean.TrackPointsForReturn;
import com.ldz.util.bean.YingyanResponse;
import com.ldz.util.bean.YyEntity;
import com.ldz.util.commonUtil.HttpUtil;
import com.ldz.util.commonUtil.JsonUtil;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

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

    public static  AddPointResponse addPoints(List<TrackPoint> entity , String url){
		Map<String, String> beanmap = new HashMap<>();

		beanmap.put("ak", AK);
		beanmap.put("service_id", SERVICE_ID+"");
		beanmap.put("point_list", createPointList(entity));
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
		beanmap.put("speed",entity.getSpeed()+"");
		beanmap.put("direction",entity.getDirection()+"");
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

		/**
		 * 构造 point_list
		 */
		public static  String createPointList(List<TrackPoint> entities){
			
			JSONArray jsonArray = new JSONArray();
			
			for(TrackPoint entity: entities) {
				JSONObject jsonObject = new JSONObject();
				jsonObject.put("entity_name", entity.getEntity_name());
				jsonObject.put("loc_time", entity.getLoc_time());
				jsonObject.put("latitude", entity.getLatitude());
				jsonObject.put("longitude", entity.getLongitude());
				jsonObject.put("coord_type_input", "bd09ll");
				jsonObject.put("speed", entity.getSpeed());
				jsonObject.put("direction", entity.getDirection());
				jsonObject.put("height", entity.getHeight());
				jsonObject.put("radius", entity.getRadius());
				jsonObject.put("_object_key", entity.get_object_key());
				
				jsonArray.add(jsonObject);
			}
			
			return jsonArray.toString();
			
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
		beanmap.put("page_size", entity.getPage_size());
		String postJson = null;
		try {
			postJson = HttpUtil.get(url, beanmap);
		} catch (Exception e) {
			e.printStackTrace();
		}

		TrackPointsForReturn addPointResponse = JsonUtil.toBean(postJson,TrackPointsForReturn.class);

		return addPointResponse;

	}



	/*public static void main(String[] args) {

		TrackPoint t = new TrackPoint();

		t.set_object_key("1111");
		t.setEntity_name("测试多个坐标上传");
		t.setLoc_time(System.currentTimeMillis()/1000);
		t.setLatitude(35.3);
		t.setLongitude(123.3);

		TrackPoint t1 = new TrackPoint();

		t1.set_object_key("2222");
		t1.setEntity_name("测试多个坐标上传");
		t1.setLoc_time(System.currentTimeMillis()/1000 + 10);
		t1.setLatitude(35.7);
		t1.setLongitude(123.6);

		List<TrackPoint> trackPoints = new ArrayList<>();

		trackPoints.add(t);
		trackPoints.add(t1);

		AddPointResponse a = addPoints(trackPoints,addPointsURL);

		System.out.println(a.toString());



	}*/






}
