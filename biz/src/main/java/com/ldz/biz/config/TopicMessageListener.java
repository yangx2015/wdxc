package com.ldz.biz.config;

import com.ldz.biz.module.bean.GpsInfo;
import com.ldz.biz.module.bean.gpsSJInfo;
import com.ldz.biz.module.model.*;
import com.ldz.biz.module.service.*;
import com.ldz.sys.model.SysZdxm;
import com.ldz.sys.service.ZdxmService;
import com.ldz.util.bean.*;
import com.ldz.util.commonUtil.HttpUtil;
import com.ldz.util.commonUtil.JsonUtil;
import com.ldz.util.redis.RedisTemplateUtil;
import com.ldz.util.yingyan.GuiJIApi;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.http.MediaType;
import org.springframework.util.ObjectUtils;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

//@Component
public class TopicMessageListener implements MessageListener {


    private String url;
    private String znzpurl;
    private String bizurl;
    public double distance;
    private XcService xcService;

    private ClYyService clYyService;

    private GpsLsService gpsLsService;
    private GpsService gpsService;

    private ZdglService zdglService;

    private ZdxmService zdxmService;



    private ExecutorService pool = Executors.newSingleThreadExecutor();

    private RedisTemplateUtil redisTemplate;

    Logger error = LoggerFactory.getLogger("error_info");

    public TopicMessageListener(ZdxmService zdxmService,XcService xcService,GpsService gpsService, ClYyService clYyService, GpsLsService gpsLsService, ZdglService zdglService, RedisTemplateUtil redisTemplate,String url,String znzpurl,String bizurl,double distance) {
        this.zdxmService = zdxmService;
        this.xcService = xcService;
        this.clYyService = clYyService;
        this.gpsService = gpsService;
        this.gpsLsService = gpsLsService;
        this.zdglService = zdglService;
        this.redisTemplate = redisTemplate;
        this.url = url;
        this.znzpurl = znzpurl;
        this.bizurl = bizurl;
        this.distance = distance;
    }

    /**
     * 根据 redis 失效时间存储行程
     *
     * @param message
     * @param pattern
     */
    @Override
    public void onMessage(Message message, byte[] pattern) {
        String itemValue = new String(message.getBody());
        String topic =  new String(message.getChannel());
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        if (StringUtils.contains(topic, "expired")) {
            if((StringUtils.contains(itemValue,"CX") && !StringUtils.contains(itemValue,"xc")) || StringUtils.contains(itemValue,"compencate")){
                // 过期事件存储车辆行程
                saveClXc(itemValue, simpleDateFormat);
            }else if(StringUtils.contains(itemValue,"offline")){
                // 车辆离线状态更新
                updateClZt(itemValue);
            }
        }

    }

    /**
     * 更新车辆离线状态
     * @param itemValue
     */
    private void updateClZt(String itemValue) {
        List<String> zdbhs = Arrays.asList(itemValue.split("-"));
        String zdbh = zdbhs.get(1);
        ApiResponse<String> bean = null;
        try {
            String string = HttpUtil.get(url +"/push/checkOnlin/" + zdbh);
            bean = JsonUtil.toBean(string, ApiResponse.class);
        } catch (Exception e) {
            error.error("验证客户端是否在线接口异常：", e);
        }


        if(ObjectUtils.isEmpty(bean)||bean.getCode() !=200) {
            boolean isOnline = false;
            //2018年10月24日。终端的版本号（版本号比对全部字符串）是字典配置的版本，这样就只把设备修改为熄火，速度值之类的更新为0，不做更新时间处理
            ClZdgl zdgl = zdglService.findById(zdbh);
            if (!ObjectUtils.isEmpty(zdgl)) {
                //字典项如果不存在，也不比较
                SimpleCondition condition = new SimpleCondition(SysZdxm.class);
                condition.eq(SysZdxm.InnerColumn.zdlmdm,"bbh");
                condition.eq(SysZdxm.InnerColumn.zddm,"value");
                List<SysZdxm> zdxms = zdxmService.findByCondition(condition);
                if (zdxms.size() != 0){
                    String value = zdxms.get(0).getZdmc();
                    if (value.equals(zdgl.getVersion())){
                        //如果一直把设备修改为熄火，速度值之类的更新为0，不做更新时间处理
                        isOnline = true;
                    }
                }
            }
            if (isOnline){
                zdgl.setZxzt("10");
                ClGps gps = gpsService.findById(zdbh);
                if (gps != null){
                    gps.setYxsd("0");
                    gps.setFxj(new BigDecimal(0));
                    gpsService.update(gps);
                }
            }else{
                zdgl.setZxzt("20");
                //独立线程通知其他服务器离线消息
                pool.submit(new Runnable() {

                    @Override
                    public void run() {
                        // 并将离线消息通知到gps上传
                        ApiResponse<String> senML = senML(zdbh, bizurl +"/pub/gps/save");
                        //accessLog.debug(senML+"biz接口离线消息返回");
                        ApiResponse<String> znzpsenML = senML(zdbh, znzpurl  + "/reporting");
                        //accessLog.debug(znzpsenML+"znzp接口离线消息返回");
                    }
                });
            }

            zdglService.update(zdgl);
        }else{
            redisTemplate.boundValueOps("offline-" + zdbh).set(1,10,TimeUnit.MINUTES);
            ClZdgl zdgl = zdglService.findById(zdbh);
            if (!ObjectUtils.isEmpty(zdgl)) {
                zdgl.setZxzt("10");
                zdglService.update(zdgl);

                ClGps gps = gpsService.findById(zdbh);
                if (gps != null){
                    gps.setYxsd("0");
                    gps.setFxj(new BigDecimal(0));
                    gps.setGxsj(new Date());
                    gpsService.update(gps);
                }
            }
        }
    }


    @SuppressWarnings("unchecked")
     public ApiResponse<String> senML(String zdbh, String url) {
        String bean2 = (String) redisTemplate.boundValueOps(ClGps.class.getSimpleName() + zdbh).get();
        ClGps object2 = JsonUtil.toBean(bean2, ClGps.class);
        GpsInfo gpsinfo = new GpsInfo();
        gpsinfo.setDeviceId(zdbh);
        gpsinfo.setEventType("80");
        //百度经纬度
        gpsinfo.setLatitude(object2.getWd().toString());
        gpsinfo.setLongitude(object2.getJd().toString());
        gpsinfo.setFxj(object2.getFxj().toString());
        gpsinfo.setGpsjd(object2.getJd().toString());

        String starttime = DateTime.now().toString("yyyy-MM-dd HH:mm:ss");
        gpsinfo.setStartTime(starttime);
        String postEntity = JsonUtil.toJson(gpsinfo);
        ApiResponse<String> apiResponse = null;
        Map<String, String> postHeaders = new HashMap<String, String>();
        postHeaders.put("Content-Type", MediaType.APPLICATION_JSON_VALUE);
        try {
            String postJson = HttpUtil.postJson(url, postHeaders, postEntity);
            apiResponse = (ApiResponse<String>) JsonUtil.toBean(postJson, ApiResponse.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return apiResponse;
    }

    /**
     * 存储过期车辆行程
     * @param itemValue
     * @param simpleDateFormat
     */
    private void saveClXc(String itemValue, SimpleDateFormat simpleDateFormat) {
        String[] vals = itemValue.split(",");
        String type = vals[0];
        String zdbh = vals[1];
        String startTime = vals[2];
        String endTime = DateTime.now().toString("yyyy-MM-dd HH:mm:ss");//(String)redisTemplate.boundValueOps("start_end," + zdbh + "xc" + startTime).get();
        if (StringUtils.isBlank(endTime)){
        	return;
        }

        long s = 0;
        long e = 0;
        try {
            s = simpleDateFormat.parse(startTime).getTime();
            e = simpleDateFormat.parse(endTime).getTime();
        } catch (ParseException e1) {
        }
        if((e - s) < 60000 ){
            return;
        }
        if((e - s) > 60000 * 60 * 24 ){
            return;
        }
        /*if((e - s) < 60000 ){ // 开始时间与结束时间小于1分钟 ， 行程短 ， 过滤
            // 轨迹点不存储
            return;
        }*/
        String start_end =null;
        try {
            // start_end =  newGuiJiJiuPian(zdbh, startTime, endTime);  // 新纠偏接口
            start_end = guiJiJiuPian(zdbh,s,e); // 鹰眼纠偏接口
        }catch (Exception e2){
            if(StringUtils.equals(type,"CX")) {
                // 百度轨迹点异常 ， 存储异常行程 ， 等待第二次纠偏
                redisTemplate.boundValueOps("compencate," + zdbh + "," + startTime).set("1", 1, TimeUnit.MINUTES);
                return;
            }else if(StringUtils.equals(type,"compencate")){
                // 存储当前原始轨迹点
                start_end=  saveGps(zdbh,startTime,endTime);
            }
        }

        if(StringUtils.isNotBlank(start_end)) {
            ClXc clXc = new ClXc();
            clXc.setClZdbh(zdbh);
            clXc.setXcKssj(startTime);
			try{
            	String[] arrs = start_end.split(",");
                clXc.setXcJssj(arrs[3]);
            }catch(Exception ex){
            	clXc.setXcJssj(endTime);
            }
            clXc.setXcStartEnd(start_end);
			try {
                Date startDate = DateUtils.getDate(startTime,"yyyy-MM-dd HH:mm:ss");
                Date endDate = DateUtils.getDate(clXc.getXcJssj(),"yyyy-MM-dd HH:mm:ss");
                long subTime = endDate.getTime() - startDate.getTime();
                int vaildMinute = 1000 * 60 * 5;
                if (subTime < vaildMinute){
                	error.error("["+zdbh+"]-["+startTime+"="+clXc.getXcJssj()+"]行程时长小于5分钟，不存储");
                	return;
                }
            } catch (ParseException e1) {
                error.error("日期转换异常",e1);
            }
            List<ClXc> entity = xcService.findByEntity(clXc);
            if(CollectionUtils.isEmpty(entity)) {
                xcService.saveEntity(clXc);
                redisTemplate.delete("start_end," + zdbh + "xc" + startTime);
            }else{
                error.info("行程已存在，不存储");
            }
        }
    }


    /**
     * 百度新的纠偏接口
     */
    public String newGuiJiJiuPian(String zdbh, String startTime, String endTime){

        gpsSJInfo gpsSJInfo = new gpsSJInfo();
        gpsSJInfo.setStartTime(startTime);
        gpsSJInfo.setEndTime(endTime);
        gpsSJInfo.setZdbh(zdbh);
        List<ClGpsLs> gpsLs = gpsLsService.getGpsLs(gpsSJInfo);
        List<PointListBean> listBeans = new ArrayList<>();
        gpsLs.stream().forEach(clGpsLs -> {
            PointListBean pointListBean = new PointListBean();
            pointListBean.setCoord_type_input("bd09ll");
            pointListBean.setDirection(clGpsLs.getFxj().doubleValue());
            pointListBean.setLatitude(clGpsLs.getBdwd().doubleValue());
            pointListBean.setLongitude(clGpsLs.getBdjd().doubleValue());
            pointListBean.setLoc_time(clGpsLs.getCjsj().getTime() / 1000);
            pointListBean.setSpeed(Double.parseDouble(clGpsLs.getYxsd()));
            listBeans.add(pointListBean);
        });
        String point = GuiJIApi.trackPoint(listBeans);
        NewTrackPointReturn newTrackPointReturn = JsonUtil.toBean(point, NewTrackPointReturn.class);
        if(newTrackPointReturn.getDistance() <= distance){ // 里程小于100 m 过滤
            return null;
        }
        List<Clyy> yyList = new ArrayList<>();
        List<Point> points = newTrackPointReturn.getPoints() ;
        if(CollectionUtils.isNotEmpty(points)) {
            points.forEach(point1 -> {
                Clyy clyy = new Clyy();
                clyy.setDirection(point1.getDirection() + "");
                clyy.setZdbh(zdbh);
                clyy.setLatitude(BigDecimal.valueOf(point1.getLatitude()));
                clyy.setLongitude(BigDecimal.valueOf(point1.getLongitude()));
                clyy.setSpeed(BigDecimal.valueOf(point1.getSpeed()));
                clyy.setLoc_time(parse(point1.getLoc_time()));
                yyList.add(clyy);
            });
        }

        String start_end = yyList.get(0).getLongitude() + "-" + yyList.get(0).getLatitude() + "," + yyList.get(yyList.size()-1).getLongitude()+"-"+yyList.get(yyList.size()-1).getLatitude();
        if(StringUtils.equals( yyList.get(0).getLongitude() + "-" + yyList.get(0).getLatitude() , yyList.get(yyList.size()-1).getLongitude()+"-"+yyList.get(yyList.size()-1).getLatitude())){ // 开始点位和结束点位相同 ，不存储
            return null;
        }
        clYyService.saveBatch(yyList);

        return start_end;

    }





    /**
     * 存储百度鹰眼GPS
     * @param zdbh
     * @param startTime
     * @param endTime
     */
    public String guiJiJiuPian(String zdbh, long startTime, long endTime) {

        TrackJiuPian guijis = new TrackJiuPian();
        guijis.setAk(GuiJIApi.AK);
        guijis.setService_id(GuiJIApi.SERVICE_ID);
        guijis.setEntity_name(zdbh);
        guijis.setIs_processed("1");
        // 查询 去燥 ，抽希 ， 绑路 的坐标点
        guijis.setProcess_option("need_denoise=1,need_vacuate=1,need_mapmatch=1,transport_mode=driving");
        guijis.setSupplement_mode("driving");
        guijis.setSort_type("asc");
        guijis.setCoord_type_output("bd09ll");
        guijis.setEnd_time(String.valueOf(endTime / 1000));
        guijis.setStart_time(String.valueOf(startTime / 1000));
        guijis.setPage_size("5000");
        // 查询 小时内的轨迹坐标点
        TrackPointsForReturn points = GuiJIApi.getPoints(guijis, GuiJIApi.getPointsURL);
        if(points.getDistance() <= distance){ // 里程小于100 m 过滤
            return null;
        }
        List<TrackPointsForReturn.Point> points2 = points.getPoints();
        if (CollectionUtils.isNotEmpty(points2)) {
            List<Clyy> yyList = new ArrayList<>();

            for (TrackPointsForReturn.Point point : points2) {
                Clyy clyy = new Clyy();
                clyy.setDirection(point.getDirection() + "");
                clyy.setLatitude(BigDecimal.valueOf(point.getLatitude()));
                clyy.setLoc_time(parse(point.getLoc_time()));
                clyy.setLongitude(BigDecimal.valueOf(point.getLongitude()));
                clyy.setSpeed(BigDecimal.valueOf(point.getSpeed()));
                clyy.setZdbh(zdbh);
                List<Clyy> clyys = clYyService.findByEntity(clyy);
                if(CollectionUtils.isEmpty(clyys)) {
                    yyList.add(clyy);
                }
            }
            String start_end = yyList.get(0).getLongitude() + "-" + yyList.get(0).getLatitude() + "," + yyList.get(yyList.size()-1).getLongitude()+"-"+yyList.get(yyList.size()-1).getLatitude();
            if(StringUtils.equals( yyList.get(0).getLongitude() + "-" + yyList.get(0).getLatitude() , yyList.get(yyList.size()-1).getLongitude()+"-"+yyList.get(yyList.size()-1).getLatitude())){ // 开始点位和结束点位相同 ，不存储
                return null;
            }
            if(CollectionUtils.isEmpty(yyList)){
                return null;
            }
            clYyService.saveBatch(yyList);
            return start_end + "," + points.getDistance() + "," + yyList.get(yyList.size()-1).getLoc_time();
        } else {
            return null;
        }

    }

    /**
     * 存储原始轨迹点
     */
    public String saveGps(String zdbh,String startTime, String endTime){

        SimpleCondition simpleCondition = new SimpleCondition(ClGpsLs.class);
        simpleCondition.and().andBetween(ClGpsLs.InnerColumn.cjsj.name(),startTime,endTime);
        simpleCondition.eq(ClGpsLs.InnerColumn.zdbh.name(),zdbh);
        List<Clyy> yyList = new ArrayList<>();
        List<ClGpsLs> gpsLsList = gpsLsService.findByCondition(simpleCondition);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        gpsLsList.stream().forEach(clGpsLs -> {
            Clyy clyy = new Clyy();
            clyy.setDirection(clGpsLs.getFxj()+"");
            clyy.setLatitude(clGpsLs.getBdwd());
            clyy.setLoc_time(simpleDateFormat.format(clGpsLs.getCjsj()));
            clyy.setLongitude(clGpsLs.getBdjd());
            clyy.setSpeed(new BigDecimal(clGpsLs.getYxsd()));
            clyy.setZdbh(zdbh);
            yyList.add(clyy);
        });
        /*if(StringUtils.equals( yyList.get(0).getLongitude() + "-" + yyList.get(0).getLatitude() , yyList.get(yyList.size()-1).getLongitude()+"-"+yyList.get(yyList.size()-1).getLatitude())){ // 开始点位和结束点位相同 ，不存储
            return null;
        }*/
        String start_end = yyList.get(0).getLongitude() + "-" + yyList.get(0).getLatitude() + "," + yyList.get(yyList.size()-1).getLongitude()+"-"+yyList.get(yyList.size()-1).getLatitude();
        clYyService.saveBatch(yyList);
        return start_end;
    }



    public String parse(long time) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Long aLong = time * 1000;
        Date date = new Date(aLong);
        String format = simpleDateFormat.format(date);
        return format;
    }


}
