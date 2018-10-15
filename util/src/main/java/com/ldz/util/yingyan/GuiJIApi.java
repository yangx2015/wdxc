package com.ldz.util.yingyan;

import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;

import com.ldz.util.bean.AddPointResponse;
import com.ldz.util.bean.PointListBean;
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

    public final static String AK = "B6w4WTVTXsCQSwU9T8uVBNDnYGb7PItF";

    public final static int SERVICE_ID = 200779;

    /*public final static String AK = "Loegca5jzGqwaPWmaQEtTGooWAqexsGh";

    public final static int SERVICE_ID = 200383;*/

    public final static String saveEntityuRL = "http://yingyan.baidu.com/api/v3/entity/add";

    public final static String updateEntityuRL = "http://yingyan.baidu.com/api/v3/entity/update";

    public final static String deleteEntityuRL = "http://yingyan.baidu.com/api/v3/entity/delete";

    public final static String listEntityURL = "http://yingyan.baidu.com/api/v3/entity/list";

    public final static String addPointsURL = "http://yingyan.baidu.com/api/v3/track/addpoints";

    public final static String addPointURL = "http://yingyan.baidu.com/api/v3/track/addpoint";

    public final static String getPointsURL = "http://yingyan.baidu.com/api/v3/track/gettrack";

    public final static String trackPointURL = "http://api.map.baidu.com/rectify/v1/track?";


    //status:3005 设备已存在  status:0 新增成功
    public static YingyanResponse changeEntity(YyEntity entity, String url) {

        Map<String, String> beanmap = new HashMap<>();

        beanmap.put("ak", entity.getAk());
        beanmap.put("service_id", entity.getService_id() + "");
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

    public static AddPointResponse addPoints(List<TrackPoint> entity, String url) {
        Map<String, String> beanmap = new HashMap<>();

        beanmap.put("ak", AK);
        beanmap.put("service_id", SERVICE_ID + "");
        beanmap.put("point_list", createPointList(entity));
        String postJson = null;
        try {
            postJson = HttpUtil.post(url, beanmap);
        } catch (Exception e) {
            e.printStackTrace();
        }

        AddPointResponse addPointResponse = JsonUtil.toBean(postJson, AddPointResponse.class);

        return addPointResponse;

    }


    public static YingyanResponse addPoint(TrackPoint entity, String url) {

        Map<String, String> beanmap = new HashMap<>();

        beanmap.put("ak", entity.getAk());
        beanmap.put("service_id", entity.getService_id() + "");
        beanmap.put("entity_name", entity.getEntity_name());
        beanmap.put("coord_type_input", entity.getCoord_type_input());
        beanmap.put("latitude", entity.getLatitude() + "");
        beanmap.put("loc_time", entity.getLoc_time() + "");
        beanmap.put("longitude", entity.getLongitude() + "");
        beanmap.put("_object_key", entity.get_object_key());
        beanmap.put("speed", entity.getSpeed() + "");
        beanmap.put("direction", entity.getDirection() + "");
        String postJson = null;
        try {
            postJson = HttpUtil.post(url, beanmap);
        } catch (Exception e) {
            e.printStackTrace();
        }

        YingyanResponse addPointResponse = JsonUtil.toBean(postJson, YingyanResponse.class);

        return addPointResponse;
        //System.out.println(addPointResponse);

    }

    /**
     * 构造 point_list
     */
    public static String createPointList(List<TrackPoint> entities) {

        JSONArray jsonArray = new JSONArray();

        for (TrackPoint entity : entities) {
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

    public static TrackPointsForReturn getPoints(TrackJiuPian entity, String url) {

        Map<String, String> beanmap = new HashMap<>();

        beanmap.put("ak", entity.getAk());
        beanmap.put("service_id", entity.getService_id() + "");
        beanmap.put("entity_name", entity.getEntity_name());
        beanmap.put("start_time", entity.getStart_time());
        beanmap.put("end_time", entity.getEnd_time() + "");
        beanmap.put("is_processed", entity.getIs_processed() + "");
        beanmap.put("process_option", entity.getProcess_option());
        beanmap.put("supplement_mode", entity.getSupplement_mode());
        beanmap.put("sort_type", entity.getSort_type());
        beanmap.put("coord_type_output", entity.getCoord_type_output());
        beanmap.put("page_size", entity.getPage_size());
        if (entity.getLow_speed_threshold() != null){
        	beanmap.put("low_speed_threshold", entity.getLow_speed_threshold().toString());	
        }
        
        String postJson = null;
        try {
            postJson = HttpUtil.get(url, beanmap);
        } catch (Exception e) {
            e.printStackTrace();
        }

        TrackPointsForReturn addPointResponse = JsonUtil.toBean(postJson, TrackPointsForReturn.class);

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

    /**
     * 轨迹纠偏新接口
     */
    public static String trackPoint(List<PointListBean> pointList) {

        Map<String,String> map = new HashMap<>();
        map.put("ak",AK);
        map.put("rectify_option","need_mapmatch:1|transport_mode:driving|denoise_grade:1|vacuate_grade:1");
        map.put("point_list",JsonUtil.toJson(pointList));


        String post = HttpUtil.post(trackPointURL, map);
        return post;

    }

    public static void main(String[] args)throws Exception {
    	String[] times = {
    			"2018-09-29 13:39:05,2018-09-29 13:57:04",
    			"2018-09-29 14:02:29,2018-09-29 14:32:27",
    			"2018-09-29 14:32:32,2018-09-29 14:53:58",
    			"2018-09-29 15:06:56,2018-09-29 15:21:41",
    			"2018-09-29 15:45:41,2018-09-29 16:00:52",
    			"2018-09-29 16:26:49,2018-09-29 16:43:45",
    			"2018-09-29 17:10:03,2018-09-29 17:28:16",
    			"2018-09-29 17:45:00,2018-09-29 18:04:38",
    			"2018-09-29 18:16:01,2018-09-29 18:34:50",
    			"2018-09-29 18:52:43,2018-09-29 19:08:53",
    			"2018-09-29 19:26:24,2018-09-29 19:41:08",
    			"2018-09-29 20:03:34,2018-09-29 20:18:16",
    			"2018-09-29 20:43:31,2018-09-29 21:02:00",
    			"2018-09-29 21:04:14,2018-09-29 21:20:54",
    			"2018-09-29 21:28:19,2018-09-29 21:43:48",
    			"2018-09-29 21:50:29,2018-09-29 22:15:39",
    			"2018-09-30 07:02:35,2018-09-30 08:10:12",
    			"2018-09-30 08:30:54,2018-09-30 08:43:59",
    			"2018-09-30 09:02:49,2018-09-30 09:21:42",
    			"2018-09-30 09:36:48,2018-09-30 09:54:57",
    			"2018-09-30 10:08:50,2018-09-30 10:24:01",
    			"2018-09-30 10:45:28,2018-09-30 11:01:59",
    			"2018-09-30 11:23:08,2018-09-30 11:38:13",
    			"2018-09-30 11:55:59,2018-09-30 12:12:22",
    			"2018-09-30 12:26:31,2018-09-30 12:42:11",
    			"2018-09-30 13:01:04,2018-09-30 13:18:20",
    			"2018-09-30 13:31:55,2018-09-30 13:54:37",
    			"2018-09-30 13:54:42,2018-10-01 13:48:36",
    			"2018-10-01 13:48:36,2018-10-01 14:00:22",
    			"2018-10-01 14:08:24,2018-10-01 14:25:34",
    			"2018-10-01 14:35:54,2018-10-01 14:48:36",
    			"2018-10-01 14:55:12,2018-10-01 15:11:02",
    			"2018-10-01 15:17:55,2018-10-01 15:33:58",
    			"2018-10-01 15:52:57,2018-10-01 16:07:50",
    			"2018-10-01 16:22:28,2018-10-01 16:38:26",
    			"2018-10-01 16:50:10,2018-10-01 17:04:14",
    			"2018-10-01 17:16:25,2018-10-01 17:31:57",
    			"2018-10-01 17:37:15,2018-10-01 17:51:21",
    			"2018-10-01 18:07:30,2018-10-01 18:23:43",
    			"2018-10-01 18:33:10,2018-10-01 18:49:04",
    			"2018-10-01 19:11:34,2018-10-01 19:36:09",
    			"2018-10-01 19:43:27,2018-10-01 19:56:46",
    			"2018-10-01 20:17:03,2018-10-01 20:32:26",
    			"2018-10-01 20:51:16,2018-10-01 21:07:21",
    			"2018-10-01 21:20:46,2018-10-01 22:14:37",
    			"2018-10-01 22:14:37,2018-10-02 07:08:26",
    			"2018-10-02 07:29:30,2018-10-02 07:31:31",
    			"2018-10-02 07:49:32,2018-10-02 08:07:59",
    			"2018-10-02 08:23:29,2018-10-02 08:36:06",
    			"2018-10-02 08:47:20,2018-10-02 08:59:16",
    			"2018-10-02 09:20:17,2018-10-02 09:35:07",
    			"2018-10-02 09:52:13,2018-10-02 10:07:27",
    			"2018-10-02 10:12:15,2018-10-02 10:25:12",
    			"2018-10-02 10:27:19,2018-10-02 10:42:08",
    			"2018-10-02 10:44:11,2018-10-02 11:03:41",
    			"2018-10-02 11:08:32,2018-10-02 11:28:12",
    			"2018-10-02 11:32:34,2018-10-02 11:49:16",
    			"2018-10-02 11:57:35,2018-10-02 12:14:36",
    			"2018-10-02 12:24:39,2018-10-02 12:41:01",
    			"2018-10-02 12:49:16,2018-10-02 13:09:36",
    			"2018-10-02 13:18:46,2018-10-02 13:34:11",
    			"2018-10-02 13:40:54,2018-10-02 13:59:25",
    			"2018-10-02 13:59:26,2018-10-02 15:48:50",
    			"2018-10-02 15:58:16,2018-10-02 20:09:26",
    			"2018-10-02 22:17:20,2018-10-03 00:23:14",
    			"2018-10-03 02:28:26,2018-10-03 13:50:55",
    			"2018-10-03 13:50:57,2018-10-03 13:56:17",
    			"2018-10-03 13:58:54,2018-10-03 14:04:37",
    			"2018-10-03 14:15:50,2018-10-03 14:46:14",
    			"2018-10-03 14:46:19,2018-10-03 15:11:22",
    			"2018-10-03 15:13:29,2018-10-03 15:36:23",
    			"2018-10-03 15:43:39,2018-10-03 16:10:54",
    			"2018-10-03 16:12:50,2018-10-03 16:35:52",
    			"2018-10-03 16:36:52,2018-10-03 17:06:58",
    			"2018-10-03 17:10:02,2018-10-03 17:38:42",
    			"2018-10-03 17:42:21,2018-10-03 18:10:16",
    			"2018-10-03 18:11:51,2018-10-03 18:41:17",
    			"2018-10-03 18:43:27,2018-10-03 19:11:00",
    			"2018-10-03 19:13:04,2018-10-03 20:21:12",
    			"2018-10-03 20:21:16,2018-10-04 07:16:11",
    			"2018-10-04 07:16:11,2018-10-04 07:27:46",
    			"2018-10-04 07:41:09,2018-10-04 08:09:31",
    			"2018-10-04 08:19:14,2018-10-04 08:38:06",
    			"2018-10-04 09:00:51,2018-10-04 09:23:45",
    			"2018-10-04 09:42:35,2018-10-04 10:04:40",
    			"2018-10-04 10:19:20,2018-10-04 10:43:01",
    			"2018-10-04 10:50:12,2018-10-04 11:14:16",
    			"2018-10-04 11:18:08,2018-10-04 11:39:29",
    			"2018-10-04 11:51:26,2018-10-04 12:15:37",
    			"2018-10-04 12:22:31,2018-10-04 12:48:08",
    			"2018-10-04 12:58:55,2018-10-04 13:21:50",
    			"2018-10-04 13:24:35,2018-10-04 13:46:42",
    			"2018-10-04 13:54:10,2018-10-04 14:23:38",
    			"2018-10-04 14:23:40,2018-10-05 13:49:48",
    			"2018-10-05 13:49:52,2018-10-05 13:58:21",
    			"2018-10-05 14:02:47,2018-10-05 14:06:48",
    			"2018-10-05 14:19:59,2018-10-05 14:45:40",
    			"2018-10-05 14:50:53,2018-10-05 15:12:37",
    			"2018-10-05 15:29:15,2018-10-05 15:54:34",
    			"2018-10-05 15:56:39,2018-10-05 16:17:46",
    			"2018-10-05 16:25:55,2018-10-05 16:52:11",
    			"2018-10-05 17:03:11,2018-10-05 17:29:11",
    			"2018-10-05 17:34:11,2018-10-05 17:56:09",
    			"2018-10-05 18:02:45,2018-10-05 18:26:49",
    			"2018-10-05 18:32:24,2018-10-05 19:02:17",
    			"2018-10-05 19:02:17,2018-10-06 18:12:39",
    			"2018-10-06 21:13:17,2018-10-07 11:04:34",
    			"2018-10-07 13:47:08,2018-10-07 13:56:15",
    			"2018-10-07 13:56:15,2018-10-07 13:57:59",
    			"2018-10-07 13:59:48,2018-10-07 14:15:23",
    			"2018-10-07 14:29:04,2018-10-07 14:44:29",
    			"2018-10-07 14:58:30,2018-10-07 15:12:17",
    			"2018-10-07 15:28:31,2018-10-07 15:45:44",
    			"2018-10-07 15:56:15,2018-10-07 16:11:31",
    			"2018-10-07 16:27:27,2018-10-07 16:42:14",
    			"2018-10-07 16:55:26,2018-10-07 17:11:35",
    			"2018-10-07 17:19:51,2018-10-07 17:39:36",
    			"2018-10-07 17:46:36,2018-10-07 18:03:10",
    			"2018-10-07 18:15:36,2018-10-07 18:32:39",
    			"2018-10-07 18:45:02,2018-10-07 19:30:01",
    			"2018-10-07 19:38:33,2018-10-07 19:53:39",
    			"2018-10-07 20:05:25,2018-10-07 20:20:49",
    			"2018-10-07 20:24:43,2018-10-07 21:04:27",
    			"2018-10-07 21:09:37,2018-10-07 22:14:53",
    			"2018-10-07 22:14:55,2018-10-08 07:11:05",
    			"2018-10-08 07:13:06,2018-10-08 07:22:25",
    			"2018-10-08 07:24:53,2018-10-08 07:41:32",
    			"2018-10-08 07:59:13,2018-10-08 08:26:18",
    			"2018-10-08 08:33:38,2018-10-08 08:49:19",
    			"2018-10-08 09:10:01,2018-10-08 09:27:13",
    			"2018-10-08 09:42:52,2018-10-08 09:59:19",
    			"2018-10-08 10:12:01,2018-10-08 10:27:47",
    			"2018-10-08 10:41:37,2018-10-08 10:55:28",
    			"2018-10-08 11:20:00,2018-10-08 11:39:57",
    			"2018-10-08 11:58:20,2018-10-08 12:16:59",
    			"2018-10-08 12:34:16,2018-10-08 12:49:29",
    			"2018-10-08 13:13:58,2018-10-08 13:29:09",
    			"2018-10-08 13:45:19,2018-10-08 14:04:36",
    			"2018-10-08 14:04:40,2018-10-08 14:13:56",
    			"2018-10-08 14:28:27,2018-10-08 14:47:54",
    			"2018-10-08 14:51:25,2018-10-08 14:53:48",
    			"2018-10-08 15:01:40,2018-10-08 15:16:46",
    			"2018-10-08 15:33:40,2018-10-08 15:51:54",
    			"2018-10-08 16:08:12,2018-10-08 16:24:11",
    			"2018-10-08 16:42:04,2018-10-08 17:02:38",
    			"2018-10-08 17:20:37,2018-10-08 17:47:55",
    			"2018-10-08 17:58:39,2018-10-08 18:24:50",
    			"2018-10-08 18:33:54,2018-10-08 18:52:56",
    			"2018-10-08 18:52:56,2018-10-08 19:07:06",
    			"2018-10-08 19:07:06,2018-10-08 19:24:56",
    			"2018-10-08 19:39:22,2018-10-08 19:56:47",
    			"2018-10-08 19:56:50,2018-10-09 02:15:32",
    			"2018-10-09 04:23:04,2018-10-09 13:36:16",
    			"2018-10-09 13:45:14,2018-10-09 13:47:36",
    			"2018-10-09 14:08:39,2018-10-09 14:23:38",
    			"2018-10-09 14:46:11,2018-10-09 14:59:44",
    			"2018-10-09 15:27:15,2018-10-09 15:41:49",
    			"2018-10-09 16:07:11,2018-10-09 16:25:26",
    			"2018-10-09 16:45:39,2018-10-09 17:02:40",
    			"2018-10-09 17:17:36,2018-10-09 17:34:34",
    			"2018-10-09 17:52:17,2018-10-09 18:39:05",
    			"2018-10-09 19:04:00,2018-10-09 19:18:32",
    			"2018-10-09 19:42:29,2018-10-09 19:56:47",
    			"2018-10-09 20:25:53,2018-10-09 20:43:51",
    			"2018-10-09 20:55:42,2018-10-09 21:13:28",
    			"2018-10-09 21:19:32,2018-10-09 21:33:47",
    			"2018-10-09 21:41:10,2018-10-09 22:03:03",
    			"2018-10-10 11:35:09,2018-10-10 15:43:25",
    			"2018-10-10 15:43:27,2018-10-10 15:50:46",
    			"2018-10-10 16:09:56,2018-10-10 16:23:19",
    			"2018-10-10 16:43:37,2018-10-10 16:58:37",
    			"2018-10-10 17:18:52,2018-10-10 17:38:27",
    			"2018-10-10 17:52:56,2018-10-10 18:14:53",
    			"2018-10-10 18:30:46,2018-10-10 18:48:10",
    			"2018-10-10 19:09:16,2018-10-10 19:21:54",
    			"2018-10-10 19:52:59,2018-10-10 20:08:29",
    			"2018-10-10 20:20:29,2018-10-10 20:42:33",
    			"2018-10-10 20:47:33,2018-10-10 22:08:25",
    			"2018-10-10 22:08:30,2018-10-11 10:21:33",
    	};
    	String zdbh = "865923030010034";
    	int tDis = 0, tLDis = 0;
    	for (int i=0; i<times.length; i++){
    		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String startTime = times[i].split(",")[0];
            String endTime = times[i].split(",")[1];
            long s = 0;
            long e = 0;
            try {
                s = simpleDateFormat.parse(startTime).getTime();
                e = simpleDateFormat.parse(endTime).getTime();
            } catch (ParseException e1) {
            }
        	TrackJiuPian guijis = new TrackJiuPian();
            guijis.setAk(GuiJIApi.AK);
            guijis.setService_id(GuiJIApi.SERVICE_ID);
            guijis.setEntity_name(zdbh);
            guijis.setIs_processed("1");
            // 查询 去燥 ，抽希 ， 绑路 的坐标点
            guijis.setProcess_option("need_denoise=0,need_vacuate=1,need_mapmatch=1,transport_mode=driving");
            guijis.setSupplement_mode("driving");
            guijis.setLow_speed_threshold(30d);
            guijis.setSort_type("asc");
            guijis.setCoord_type_output("bd09ll");
            guijis.setEnd_time(String.valueOf(e / 1000));
            guijis.setStart_time(String.valueOf(s / 1000));
            guijis.setPage_size("5000");
            // 查询 小时内的轨迹坐标点
            
            TrackPointsForReturn points = GuiJIApi.getPoints(guijis, GuiJIApi.getPointsURL);
            if (!"0".equals(points.getStatus())){
            	FileUtils.write(new File("e:/xingcheng/"+zdbh+"-error.txt"), times[i]+","+points.getStatus()+"-"+points.getMessage()+"\n", true);
            	continue;
            }else{
            	String data = times[i]+"=鹰眼结果："+points.getStatus()+"-"+points.getMessage()+"-"+points.getDistance() +","+ points.getLow_speed_distance();
            	FileUtils.write(new File("e:/xingcheng/"+zdbh+"-success.txt"), data+"\n", true);
            }
            tDis += points.getDistance();
            tLDis += points.getLow_speed_distance();
            Thread.sleep(100);
    	}
    	
    	System.out.println("总里程:"+(tDis/1000)+"_"+(tLDis/1000));
    }

    // 来自stackoverflow的MD5计算方法，调用了MessageDigest库函数，并把byte数组结果转换成16进制
    public String MD5(String md5) {
        try {
            java.security.MessageDigest md = java.security.MessageDigest
                    .getInstance("MD5");
            byte[] array = md.digest(md5.getBytes());
            StringBuffer sb = new StringBuffer();
            for (int i = 0; i < array.length; ++i) {
                sb.append(Integer.toHexString((array[i] & 0xFF) | 0x100)
                        .substring(1, 3));
            }
            return sb.toString();
        } catch (java.security.NoSuchAlgorithmException e) {
        }
        return null;
    }


}
