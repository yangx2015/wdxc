package com.ldz.util.yingyan;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.ldz.util.bean.*;
import org.apache.commons.lang.StringUtils;

import com.ldz.util.bean.YingyanResponse;
import com.ldz.util.bean.YyEntity;
import com.ldz.util.commonUtil.DateUtils;
import com.ldz.util.commonUtil.HttpUtil;
import com.ldz.util.commonUtil.JsonUtil;

public class GuiJIApi {
  
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


		public static YingyanResponse addPoint(TrackPoint entity , String url){

		Map<String, String> beanmap = new HashMap<>();

		beanmap.put("ak", entity.getAk());
		beanmap.put("service_id", entity.getService_id()+"");
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

		return addPointResponse;
		//System.out.println(addPointResponse);

	}


	public static TrackPointForReturn getPoints(TrackJiuPian entity , String url){

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
		beanmap.put("page_size",entity.getPage_size());
		beanmap.put("page_index",entity.getPage_index());

		String postJson = null;
		try {
			postJson = HttpUtil.get(url, beanmap);
		} catch (Exception e) {
			e.printStackTrace();
		}

		TrackPointForReturn addPointResponse = JsonUtil.toBean(postJson,TrackPointForReturn.class);

		return addPointResponse;

	}




}
