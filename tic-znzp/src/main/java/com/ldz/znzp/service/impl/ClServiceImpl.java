package com.ldz.znzp.service.impl;

import com.ldz.geo.bean.GeoModel;
import com.ldz.geo.util.GeoUtil;
import com.ldz.util.bean.ApiResponse;
import com.ldz.util.bean.SimpleCondition;
import com.ldz.util.commonUtil.DateUtils;
import com.ldz.util.commonUtil.JsonUtil;
import com.ldz.util.commonUtil.SnowflakeIdWorker;
import com.ldz.util.exception.RuntimeCheck;
import com.ldz.util.gps.DistanceUtil;
import com.ldz.util.gps.Gps;
import com.ldz.util.gps.PositionUtil;
import com.ldz.znzp.base.BaseServiceImpl;
import com.ldz.znzp.bean.GpsInfo;
import com.ldz.znzp.bean.ReportData;
import com.ldz.znzp.mapper.*;
import com.ldz.znzp.model.*;
import com.ldz.znzp.service.*;
import com.ldz.znzp.util.NettyUtil;
import io.netty.channel.Channel;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.geo.GeoResult;
import org.springframework.data.geo.GeoResults;
import org.springframework.data.redis.connection.RedisGeoCommands;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import tk.mybatis.mapper.common.Mapper;

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Slf4j
@Service
public class ClServiceImpl extends BaseServiceImpl<ClCl,String> implements ClService{
    @Autowired
    private ClClMapper entityMapper;
    @Autowired
    private ClPbMapper clPbMapper;
    @Autowired
    private ClZdMapper zdMapper;
    @Autowired
    private ClXlzdMapper xlzdMapper;
    @Autowired
    private ClClyxjlMapper clyxjlMapper;
    @Autowired
    private ZdService zdService;
    @Autowired
    private GpsService gpsService;
    @Autowired
    private XlService xlService;
    @Autowired
    private ZnzpService znzpService;
    @Autowired
    private NettyUtil nettyUtil;
    @Autowired
    private ClyxjlService clyxjlService;
    @Autowired
    public SnowflakeIdWorker idGenerator;
    @Autowired
    private XlzdService xlzdService;
    @Autowired
    private GeoUtil geoUtil;
    @Override
    protected Mapper<ClCl> getBaseMapper() {
        return entityMapper;
    }

    @Override
    protected Class<?> getEntityCls(){
        return ClCl.class;
    }

    @Override
    public ApiResponse<String> saveEntity(ClCl entity) {
        save(entity);
        return ApiResponse.saveSuccess();
    }

    @Override
    public ApiResponse<String> report(String tid,ClPb clPb,ClCl car,ClXl xl,ClClyxjl clClyxjl) {
        RuntimeCheck.ifNull(clClyxjl,"未找到车辆运行记录");
        RuntimeCheck.ifBlank(clPb.getXlId(),"未找到线路");
        List<ClZnzp> znzps = znzpService.getByXlId(clPb.getXlId());
        RuntimeCheck.ifEmpty(znzps,"未找到通道");
        List<String> zpIds = znzps.stream().map(ClZnzp::getZdbh).collect(Collectors.toList());

        ReportData reportData = new ReportData();
        reportData.setRouteId(xl.getId());
        reportData.setRouteName(xl.getXlmc());
        reportData.setDirect(clClyxjl.getYxfx());
        reportData.setStationNo(clClyxjl.getZdbh());
        reportData.setType(clClyxjl.getZt());
        reportData.setBus_plate(clClyxjl.getCphm());
        reportData.setDirect("up");
        Map<String,Object> channelMap = nettyUtil.getChannelByTids(zpIds);
        for (Map.Entry<String, Object> entry : channelMap.entrySet()) {
            reportData.setTid(entry.getKey());
            log.info(JsonUtil.toJson(reportData));
            writeResult((Channel)entry.getValue(),reportData);
        }
        return ApiResponse.success();
    }


    public ApiResponse<String> updateGpsNew(GpsInfo gpsInfo, ClPb pb,ClCl car,ClXl route, ClClyxjl record){

        if(ObjectUtils.isEmpty(route)){
            return ApiResponse.notFound("未找到车辆线路");
        }
        // 判断当前线路站点是否缓存至redis
        if(!geoUtil.hasKey(route.getId()+"_stations")){
            initXlZd(route.getId());
        }
        // 判断设备在线状态
        String zt = "running";
        Date now = new Date();
        if ("80".equals(gpsInfo.getEventType())
                || "20".equals(gpsInfo.getSczt())
                || gpsInfo.getLatitude().equals("-1")
                || gpsInfo.getLongitude().equals("-1")
                || StringUtils.isEmpty(gpsInfo.getLongitude())
                || StringUtils.isEmpty(gpsInfo.getLatitude())
                ){
            zt = "off";
            if (record == null){
                log.info("gps为 -1");
                record = new ClClyxjl();
                record.setCjsj(now);
                record.setClId(car.getClId());
                record.setCphm(car.getCph());
                record.setId(""+idGenerator.nextId());
                record.setZt(zt);
                if(route != null){
                    record.setXlId(route.getId());
                    record.setXlmc(route.getXlmc());
                }
                clyxjlMapper.insertSelective(record);
            }else{
                record.setZt(zt);
                clyxjlMapper.updateByPrimaryKeySelective(record);
            }
            return ApiResponse.success("设备已离线");
        }

        // 经纬度转换
        ClGps clGps = gpsService.changeCoordinates(gpsInfo);
//        Gps gps = new Gps(Double.parseDouble(String.valueOf(clGps.getBdjd().doubleValue())),Double.parseDouble(gpsInfo.getLongitude()));
        Gps gps = new Gps(clGps.getBdjd().doubleValue(),clGps.getBdwd().doubleValue());
        // 获取当前站点
        boolean hasRecord = record != null && StringUtils.isNotEmpty(record.getZdId());
        ClZd currentStation = null;
        zt = null;
        if (record == null){
            log.info("没有运行记录");
            currentStation = findCurrentZd("",gps,car,pb);
            record = new ClClyxjl();
            record.setCjsj(now);
            record.setClId(car.getClId());
            record.setCphm(car.getCph());
            record.setId(""+idGenerator.nextId());
            if(route != null){
                record.setXlId(route.getId());
                record.setXlmc(route.getXlmc());
            }
        }else {
            if(route != null){
                record.setXlId(route.getId());
                record.setXlmc(route.getXlmc());
            }
            log.info("已有运行记录："+record.toString());
            String stationId = record.getZdId();
            currentStation = zdService.findById(stationId);
            double distance = DistanceUtil.getShortDistance(currentStation.getJd(),currentStation.getWd(),clGps.getBdjd().doubleValue(),clGps.getBdwd().doubleValue());
            log.info("distance:"+distance);
            if (distance < currentStation.getFw()){
                if ("80".equals(gpsInfo.getEventType())){
                    zt = "off";
                }else{
                    zt = "inStation"; // 进站
                }
            }else{
                currentStation = findCurrentZd(record.getZdId(),gps,car,pb);
            }
        }
        if (zt == null){
            if ("80".equals(gpsInfo.getEventType())){
                zt = "off";
            }else{
                double distance = DistanceUtil.getLongDistance(currentStation.getJd(),currentStation.getWd(),clGps.getBdjd().doubleValue(),clGps.getBdwd().doubleValue());
                log.info("distance:"+distance);
                if (distance < currentStation.getFw()){
                    zt = "inStation"; // 进站
                }else{
                    zt = "runing"; // 正常
                }
            }
        }

        currentStation.setXlId(route.getId());
        zdService.setStationOrder(currentStation);
        record.setZdbh(currentStation.getRouteOrder());
        record.setZdmc(currentStation.getMc());
        record.setZdId(currentStation.getId());
        record.setCjsj(now);
        record.setJd(clGps.getBdjd());
        record.setWd(clGps.getBdwd());
        record.setZt(zt);
		//2018年9月20日。新增设备ID
        record.setSzdbh(gpsInfo.getDeviceId());
        if (hasRecord){
            clyxjlMapper.updateByPrimaryKeySelective(record);
        }else{
            clyxjlMapper.insertSelective(record);
        }
        if ("80".equals(gpsInfo.getSczt()) || "20".equals(gpsInfo.getSczt())){
            return ApiResponse.success("设备已离线");
        }

        return ApiResponse.success();
    }


    /**
     * 初始化每条线路的站点
     */
    public void initXlZd(String xlId){
        List<ClZd> clZds = zdService.getByXlId(xlId); // 根据线路id 获取到当前线路的线路站点
        List<GeoModel> geoModels = new ArrayList<>(clZds.size());
        // 缓存至redis
        for (ClZd zd : clZds) {
            GeoModel geoModel = zdToGeoModel(zd);
            geoModels.add(geoModel);
        }
        geoUtil.add(xlId + "_stations", geoModels, 1, TimeUnit.DAYS);
    }

    private GeoModel zdToGeoModel(ClZd zd){
        GeoModel model = new GeoModel();
        model.setName(zd.getId());
        // 将站点的百度坐标转换为 wgs84
        Gps gps = PositionUtil.bd09_To_Gcj02(zd.getWd(), zd.getJd());
        model.setLng(gps.getWgLon());
        model.setLat(gps.getWgLat());
        return model;
    }


    @Override
    public ApiResponse<String> updateGps(GpsInfo gpsInfo, ClPb pb,ClCl car,ClXl route, ClClyxjl record) {// 站点存储百度位置
        // 30.5411624384,114.3167198864（原始点,谷歌地球）
        // 30.5371683904,114.3242740669（原始点,谷歌地球）
        /**
         * 谷歌地图：30.5288716874,114.4212491503
         * 百度地图：30.5345399945,114.4278552156
         * 腾讯高德：30.5288493080,114.4212341309
         * 图吧地图：30.5271335880,114.4230130709
         * 谷歌地球：30.5311635880,114.4157130709
         * 北纬N30°31′52.19″ 东经E114°24′56.57″
         */

        // 原始gps转百度gps
        Date now = new Date();
        if ("80".equals(gpsInfo.getEventType())
                || "20".equals(gpsInfo.getSczt())
                || gpsInfo.getLatitude().equals("-1")
                || gpsInfo.getLongitude().equals("-1")
                || StringUtils.isEmpty(gpsInfo.getLongitude())
                || StringUtils.isEmpty(gpsInfo.getLatitude())
                ){
            if (record != null){
                record.setZt("off");
                clyxjlMapper.updateByPrimaryKeySelective(record);
            }
            return ApiResponse.success("设备已离线");
        }

        ClGps clGps = gpsService.changeCoordinates(gpsInfo);
    	// 获取车辆运行记录
        // 获取当前站点
        boolean hasRecord = record != null && StringUtils.isNotEmpty(record.getZdId());
        ClZd currentStation = null;
        String zt = null;
        Gps gps = new Gps(clGps.getBdjd().doubleValue(),clGps.getBdwd().doubleValue());
        if (record == null){
            currentStation = findCurrentZd("",gps,car,pb);
            record = new ClClyxjl();
            record.setCjsj(now);
            record.setClId(car.getClId());
            record.setCphm(car.getCph());
            record.setId(""+idGenerator.nextId());
            if(route != null){
                record.setXlId(route.getId());
                record.setXlmc(route.getXlmc());
            }
        }else {
            if(route != null){
                record.setXlId(route.getId());
                record.setXlmc(route.getXlmc());
            }
            String stationId = record.getZdId();
            currentStation = zdService.findById(stationId);
            
            double distance = DistanceUtil.getShortDistance(currentStation.getJd(),currentStation.getWd(),clGps.getBdjd().doubleValue(),clGps.getBdwd().doubleValue());
            if (distance < currentStation.getFw()){
                if ("80".equals(gpsInfo.getEventType())){
                    zt = "off";
                }else{
                    zt = "inStation"; // 进站
                }
            }else{
                int threshold=5;
                try {
                    Date last=record.getCjsj();//上一个点的创建时间
                    Date startTime=DateUtils.getDate(gpsInfo.getStartTime(),"yyyy-MM-dd HH:mm:ss");//开始时间
                    long diff = (startTime.getTime() - last.getTime())/ 1000 / 60;
                    if(diff<2){
                        threshold=1;
                    }
                }catch (Exception e){}
                currentStation = findCurrentZd(record.getZdId(),gps,car,pb,threshold);
            }
        }
        if (zt == null){
            if ("80".equals(gpsInfo.getEventType())){
                zt = "off";
            }else{
                double distance = DistanceUtil.getLongDistance(currentStation.getJd(),currentStation.getWd(),clGps.getBdjd().doubleValue(),clGps.getBdwd().doubleValue());
                log.info("distance:"+distance);
                if (distance < currentStation.getFw()){
                    zt = "inStation"; // 进站
                }else{
                    zt = "runing"; // 正常
                }
            }
        }
        
        currentStation.setXlId(route.getId());
        zdService.setStationOrder(currentStation);
        record.setZdbh(currentStation.getRouteOrder());
        record.setZdmc(currentStation.getMc());
        record.setZdId(currentStation.getId());
        record.setCjsj(new Date());
        record.setJd(clGps.getBdjd());
        record.setWd(clGps.getBdwd());
        record.setZt(zt);
        //2018年9月20日。新增设备ID
        record.setSzdbh(gpsInfo.getDeviceId());
        if (hasRecord){
            clyxjlMapper.updateByPrimaryKeySelective(record);
        }else{
            clyxjlMapper.insertSelective(record);
        }
        if ("80".equals(gpsInfo.getSczt()) || "20".equals(gpsInfo.getSczt())){
            return ApiResponse.success("设备已离线");
        }
        
        return ApiResponse.success();
    }

    private List<ClZd> getStationList(ClPb pb){
        // 获取车辆当天线路信息
        String xlId = pb.getXlId();
        // 获取线路站点
        SimpleCondition condition = new SimpleCondition(ClXlzd.class);
        condition.eq(ClXlzd.InnerColumn.xlId,xlId);
        condition.setOrderByClause(ClXlzd.InnerColumn.xh.asc());
        List<ClXlzd> xlzds = xlzdMapper.selectByExample(condition);
        if (xlzds.size() == 0){
            return null;
        }
        Map<String,Short> stationXhMap = xlzds.stream().collect(Collectors.toMap(ClXlzd::getZdId,ClXlzd::getXh));

        List<String> stationIds = xlzds.stream().map(ClXlzd::getZdId).collect(Collectors.toList());
        condition = new SimpleCondition(ClZd.class);
        condition.in(ClZd.InnerColumn.id,stationIds);
        List<ClZd> stations = zdMapper.selectByExample(condition);
        for (ClZd station : stations) {
            station.setXlId(xlId);
            Short xh = stationXhMap.get(station.getId());
            if (xh != null)station.setRouteOrder(xh);
        }

        return stations;
    }

    @Override
    public ClZd getCurrentZd(BigDecimal jd,BigDecimal wd,ClCl car,String currentZdId,ClPb pb){
        // 判断是否有当前站点信息
        // 如果有当前站点，再判断与当前站点距离
        Gps currentGps = new Gps(jd.doubleValue(),wd.doubleValue());
        ClZd currentZd = zdMapper.selectByPrimaryKey(currentZdId);
        if (currentZd == null)return null;
        Gps stationGps = new Gps(currentZd.getJd(),currentZd.getWd());
        double distance = DistanceUtil.getShortDistance(currentGps,stationGps);
        if (distance < currentZd.getFw()){ // 如果在站点范围之内，则这就是当前站点
            return currentZd;
        }else{ // 如果不在站点范围之内，查找最近的站点
            return findCurrentZd(currentZdId,currentGps,car,pb);
        }
    }

    @Override
    public ApiResponse<String> report(String tid) {
        ClCl car = getByDeviceId(tid);
        if (car == null)return ApiResponse.fail("未找到车辆");
        ClPb pb = getCarPb(car.getClId());
        if (pb == null)return ApiResponse.fail("未找到车辆排班");
        ClXl route = xlService.getByCarId(pb);
        if (route == null)return ApiResponse.fail("未找到车辆线路");

        String yxkssj =route.getYxkssj();//运行开始时间
        String yxjssj =route.getYxjssj();//运行结束时间
        if(StringUtils.isNotEmpty(yxkssj)&&StringUtils.isNotEmpty(yxjssj)){
            try {
                Date ksDate = DateUtils.getDate(DateUtils.getToday()+" "+yxkssj,"yyyy-MM-dd HH:mm");
                ksDate=new Date(ksDate.getTime() - 1000*60*5);//当前时间向前推5分钟
                Date jsDate= DateUtils.getDate(DateUtils.getToday()+" "+yxjssj,"yyyy-MM-dd HH:mm");
                jsDate= new Date(jsDate.getTime()+ 1000*60*30);//当前时间向后推30分钟
                //开始时间
                if(ksDate==null || ksDate.compareTo(new Date())>0 ||jsDate==null || new Date().compareTo(jsDate)>0){
                    return ApiResponse.fail("该线程还未开始运营");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        List<ClClyxjl> clClyxjls = clyxjlService.findEq(ClClyxjl.InnerColumn.clId,car.getClId());
        return report(tid,pb,car,route,clClyxjls.size() == 0 ? null : clClyxjls.get(0));
    }
    @Override
    public ClZd findCurrentZd(String prvStationId,Gps currentGps,ClCl car,ClPb pb){
        return findCurrentZd(prvStationId,currentGps,car,pb,5);
    }

    public ClZd findCurrentZd(String prvStationId,Gps currentGps,ClCl car,ClPb pb,long threshold){
        List<ClZd> stations = getStationList(pb);
        if (stations == null)return null;
        if (stations.size() == 1)return stations.get(0);
        Map<String,ClZd> stationMap = stations.stream().collect(Collectors.toMap(ClZd::getId,p->p));
//        log.error("1，上一站点["+prvStationId+"]:车辆ID"+car);
//        log.info("1，上一站点["+prvStationId+"]:车辆ID"+car);
        short prvStationOrder=0;
        if(StringUtils.isNotEmpty(prvStationId)){
            ClZd prvZd=stationMap.get(prvStationId);
        //         log.error("2，上一站点["+prvStationId+"]prvZd.toString():车辆ID"+car);
        //         log.info("2，上一站点["+prvStationId+"]prvZd.toString():车辆ID"+car);
            if(prvZd!=null){
                prvStationOrder=prvZd.getRouteOrder();
            }
            //         log.error("3，上一站点["+prvStationOrder+"]:车辆ID"+car);
            //         log.info("3，上一站点["+prvStationOrder+"]:车辆ID"+car);
        }
        Map<String,Double> distanceMap = new HashMap<>();
        short maxStationOrder = 0;
        for (ClZd station : stations) {
            //         log.info("4，上一站点["+prvStationOrder+"]:"+station.getRouteOrder()+"车辆ID"+car);

            //         log.info("4-1，上一站点["+prvStationOrder+"]"+(prvStationOrder==0?"真":"错")+"，"+(((stations.size()-2)<prvStationOrder)&&station.getRouteOrder()>=stations.size()-2)+"，"+(prvStationOrder>0&&prvStationOrder<=station.getRouteOrder()));
            //prvStationOrder==0 无历史坐标   (((stations.size()-2)<prvStationOrder)&&station.getRouteOrder()>=stations.size()-2) 当前站如果在最后了，只取最后两站
            if((prvStationOrder==0)||
                    (((stations.size()-2)<prvStationOrder)&&station.getRouteOrder()>stations.size()-2)||
                    (prvStationOrder>0&&(prvStationOrder<=station.getRouteOrder()&&prvStationOrder+threshold>=station.getRouteOrder()))
                    ){
            //         log.error("**********************:"+station.getRouteOrder()+" 车辆ID"+car);
                Gps gps = new Gps(station.getJd(),station.getWd());
                Double d = DistanceUtil.getShortDistance(currentGps,gps);
                // 如果距离小于站点范围，则直接返回当前站点
                if (d < station.getFw()) return station;
                if (station.getRouteOrder() > maxStationOrder){
                    maxStationOrder = station.getRouteOrder();
                }
                distanceMap.put(station.getId(),d);
                if(prvStationOrder==12||prvStationOrder==13){
                    log.error("**********************:"+prvStationOrder+"****"+station.getMc()+" 距离"+d);
                }

            }
        }
            //         log.info("5，上一站点["+prvStationOrder+"]:"+distanceMap.size());

        List<Map.Entry<String,Double>> entryList = new ArrayList<>(distanceMap.entrySet());
        entryList.sort((o1, o2) -> {
            Double v1 = o1.getValue();
            Double v2 = o2.getValue();
            return new Double(v1 - v2).intValue();
        });
        ClZd stationB = null;
        String id0 ="";
        String id1 ="";
        ClZd station0=null;
        ClZd station1=null;
        if(entryList.size()>2){
            id0 = entryList.get(0).getKey();
            id1 = entryList.get(1).getKey();
            station0 = stationMap.get(id0);
            station1 = stationMap.get(id1);
            log.error("**********************:"+entryList.get(0).getValue()+"  "+station0.getMc());
            log.error("**********************:"+entryList.get(1).getValue()+"  "+station1.getMc());
            // 如果比对站点包含
            if (station0.getRouteOrder() > station1.getRouteOrder()){
                log.error("**********************1:station0.getRouteOrder() > station1.getRouteOrder(){}",station0.getRouteOrder() > station1.getRouteOrder());
                stationB = station0;
            }else{
                log.error("**********************2:station0.getRouteOrder() > station1.getRouteOrder(){}",station0.getRouteOrder() > station1.getRouteOrder());
                stationB = station1;
            }
        }else{
            stationB=stationMap.get(entryList.get(0).getKey());
        }
        log.error("**********************:"+prvStationOrder+"****"+stationB.getMc()+" 距离");
        //         log.error("查找站点，上一站点["+prvStationId+"]:", stationB.toString());
        //         log.error("6,stationB.getRouteOrder():"+stationB.getRouteOrder() +"maxStationOrder"+maxStationOrder, stationB.getRouteOrder() == maxStationOrder);
        //         log.info("6,stationB.getRouteOrder():"+stationB.getRouteOrder() +"maxStationOrder"+maxStationOrder, stationB.getRouteOrder() == maxStationOrder);
        if (stationB.getRouteOrder() == maxStationOrder){
        //         log.info("6,最后一个站点我进来了");
            // 如果上一次站点是最后一个站点，并且状态为到站，则设置当前站点为离线
            if (prvStationId.equals(stationB.getId())){
                //         log.info("7,最后一个站点我进来了");
                // 获取线路站点
                SimpleCondition condition = new SimpleCondition(ClXlzd.class);
                condition.eq(ClXlzd.InnerColumn.xlId,pb.getXlId());
                condition.setOrderByClause(ClXlzd.InnerColumn.xh.asc());
                List<ClXlzd> xlzds = xlzdMapper.selectByExample(condition);
                //         log.info("8,查找出线路排班信息：线路ID"+pb.getXlId()+" 总计："+xlzds.size());
                if(xlzds!=null&&xlzds.size()>0){
                //         log.info("9,查找出线路排班信息：获取出结束站点的名称"+stationMap.get(xlzds.get(0).getZdId()).getMc());

                    return stationMap.get(xlzds.get(0).getZdId());
                }
                return stationB;
            }
        }
        if(entryList.size()>2){
            ClZd currentStation = station0.getRouteOrder() < station1.getRouteOrder() ? station0 : station1;
            return currentStation;
        }else{
            //         log.info("10、这是最后一个站点。站点名称："+stationB.getMc());
            return stationB;
        }
//        zdService.setStationOrders(station0,station1);

    }

    @Override
    public ClPb getCarPb(String carId)  {
        SimpleCondition condition = new SimpleCondition(ClPb.class);
        condition.eq(ClPb.InnerColumn.clId,carId);
        try {
            condition.eq(ClPb.InnerColumn.pbsj,DateUtils.getDateToday("yyyy-MM-dd"));
        } catch (ParseException e) {
            log.error("时间转换出错");
        }
        List<ClPb> clPbs = clPbMapper.selectByExample(condition);
        if (clPbs.size() == 0){
            return null;
        }
        return clPbs.get(0);
    }

    @Override
    public ClPb getCarPbByDeviceId(String deviceId) {
        // 获取车辆信息
        List<ClCl> cars = findEq(ClCl.InnerColumn.zdbh,deviceId);
        if (cars.size() == 0){
            return null;
        }
        return getCarPb(cars.get(0).getClId());
    }

    @Override
    public ClCl getByDeviceId(String deviceId) {
        // 获取车辆信息
        List<ClCl> cars = findEq(ClCl.InnerColumn.zdbh,deviceId);
        if (cars.size() == 0){
            return null;
        }
        return cars.get(0);
    }

    private void addStation(){
        ClZd station = new ClZd();
        station.setId("3");
        station.setJd(new Double("30.5371683904"));
        station.setWd(new Double("114.3242740669"));
        station.setFw(new Short("10"));
        station.setMc("name");
        zdMapper.insertSelective(station);

        ClXlzd xlzd = new ClXlzd();
        xlzd.setId("3");
        xlzd.setXh(new Short("3"));
        xlzd.setXlId("1");
        xlzd.setZdId("3");
        xlzdMapper.insertSelective(xlzd);



    }

    private void writeResult(Channel channel,ReportData reportData){
        /**
         *
         "command":"reporting",
         "tid":"1234567890ab",
         "tid":"1234567890ab",
         "routeName":"环2",
         "routeId":"123",
         "direct":"up",
         "type":"in",
         "stationNo":"7",
         */
        if (channel == null)return;
        nettyUtil.sendData(channel,reportData);
    }
    private void writeResult(List<Channel> channels,ReportData reportData){
        for (Channel channel : channels) {
            log.info(channel.id().asShortText());
            writeResult(channel,reportData);
        }
    }
}
