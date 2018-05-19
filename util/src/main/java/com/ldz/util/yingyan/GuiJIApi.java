package com.ldz.util.yingyan;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.ldz.util.bean.*;
import org.apache.commons.lang.StringUtils;

import com.ldz.util.commonUtil.DateUtils;
import com.ldz.util.commonUtil.HttpUtil;
import com.ldz.util.commonUtil.JsonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

public class GuiJIApi {

	@Autowired
	private static  RestTemplate restTemplate ;
  
    private final static String AK = "2pVOrCuBldNDOgDtwaYSP8gpQ2VQdZY9";

    private final static int SERVICE_ID = 200383;

    private final static  String  saveEntityuRL="http://yingyan.baidu.com/api/v3/entity/add";
  
    private final static  String  updateEntityuRL="http://yingyan.baidu.com/api/v3/entity/update";
    
    private final static  String  deleteEntityuRL="http://yingyan.baidu.com/api/v3/entity/delete";

    private final static String listEntityURL = "http://yingyan.baidu.com/api/v3/entity/list";

    private final static  String addPointsURL = "http://yingyan.baidu.com/api/v3/track/addpoints";


	private final static  String addPointURL = "http://yingyan.baidu.com/api/v3/track/addpoint";

	private final static String getPointsURL = "http://yingyan.baidu.com/api/v3/track/gettrack";
    
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


	public static void addPoint(TrackPoint entity , String url){

		Map<String, String> beanmap = new HashMap<>();

		beanmap.put("ak", entity.getAk());
		beanmap.put("service_id", entity.getService_id() +"");
		beanmap.put("entity_name",entity.getEntity_name());
		beanmap.put("coord_type_input",entity.getCoord_type_input());
		beanmap.put("latitude",entity.getLatitude()+"");
		beanmap.put("loc_time",entity.getLoc_time()+"");
		beanmap.put("longitude",entity.getLongitude()+"");

		String postJson = null;
		try {
			postJson = HttpUtil.post(url, beanmap);
		} catch (Exception e) {
			e.printStackTrace();
		}

		YingyanResponse addPointResponse = JsonUtil.toBean(postJson,YingyanResponse.class);

		System.out.println(addPointResponse);

	}

	public static TrackPointsForReturn getPoints(TrackJiuPian entity,String url) throws ParseException {

		Map<String, String> beanmap = new HashMap<>();

		beanmap.put("ak", entity.getAk());
		beanmap.put("service_id", entity.getService_id() +"");
		beanmap.put("entity_name",entity.getEntity_name());
		Date date = DateUtils.getDate("2018-05-17 00:00:00", "yyyy-MM-dd HH:mm:ss");
		long time = date.getTime();
		Date date1 = DateUtils.getDate("2018-05-18 00:00:00", "yyyy-MM-dd HH:mm:ss");
		long time1 = date1.getTime();
		beanmap.put("start_time",time/1000 + "");
		beanmap.put("end_time",time1/1000 + "");
		beanmap.put("supplement_mode",entity.getSupplement_mode());
		beanmap.put("sort_type","asc");
		beanmap.put("coord_type_output","bd09ll");
		beanmap.put("process_option",entity.getProcess_option());
		beanmap.put("is_processed",entity.getIs_processed());
		String postJson = null;

		try {
			postJson = HttpUtil.get(url, beanmap);
		} catch (Exception e) {
			e.printStackTrace();
		}

		TrackPointsForReturn addPointResponse = JsonUtil.toBean(postJson,TrackPointsForReturn.class);

		return addPointResponse;


	}





}
