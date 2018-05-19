package com.ldz.util.yingyan;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.ldz.util.bean.AddPointResponse;
import com.ldz.util.bean.TrackPoint;
import com.ldz.util.bean.TrackPoints;
import org.apache.commons.lang.StringUtils;

import com.ldz.util.bean.YingyanResponse;
import com.ldz.util.bean.YyEntity;
import com.ldz.util.commonUtil.DateUtils;
import com.ldz.util.commonUtil.HttpUtil;
import com.ldz.util.commonUtil.JsonUtil;

public class GuiJIApi {
  
    private final static String AK = "2pVOrCuBldNDOgDtwaYSP8gpQ2VQdZY9";

    private final static String SERVICE_ID = "200383";

    private final static  String  saveEntityuRL="http://yingyan.baidu.com/api/v3/entity/add";
  
    private final static  String  updateEntityuRL="http://yingyan.baidu.com/api/v3/entity/update";
    
    private final static  String  deleteEntityuRL="http://yingyan.baidu.com/api/v3/entity/delete";

    private final static String listEntityURL = "http://yingyan.baidu.com/api/v3/entity/list";

    private final static  String addPointsURL = "http://yingyan.baidu.com/api/v3/track/addpoints";
    
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

  

  public static void main(String[] args) throws ParseException {
	
	  TrackPoints entity = new TrackPoints();
	  
	  entity.setAk(AK);
	  entity.setService_id(SERVICE_ID);
	  
	  TrackPoint point = new TrackPoint();
	  point.setAk(AK);
	  point.setService_id(SERVICE_ID);
	  point.setEntity_name("赤焰军-宋老狗");
	  point.setLatitude(29.7149175565);
	  point.setLongitude(115.9997820579);
	  Date date = DateUtils.getDate("2018-05-17 18:59:45", "yyyy-MM-dd HH:mm:ss");
	  long time = date.getTime();
	  point.setLoc_time(time/1000);
	  
	  point.setCoord_type_input("bd09ll");
	  List<TrackPoint> points = new ArrayList<>();
	  points.add(point);
	  String json = JsonUtil.toJson(points);
	  entity.setPoint_list(json);
	  AddPointResponse addPoints = addPoints(entity,addPointsURL);
	  System.out.println(addPoints);
}








}
