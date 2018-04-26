package com.ldz.znzp.service.impl;

import com.ldz.util.bean.ApiResponse;
import com.ldz.util.bean.SimpleCondition;
import com.ldz.util.commonUtil.JsonUtil;
import com.ldz.util.commonUtil.SnowflakeIdWorker;
import com.ldz.util.gps.DistanceUtil;
import com.ldz.util.gps.Gps;
import com.ldz.znzp.base.BaseServiceImpl;
import com.ldz.znzp.bean.GpsInfo;
import com.ldz.znzp.bean.ReportData;
import com.ldz.znzp.mapper.*;
import com.ldz.znzp.model.*;
import com.ldz.znzp.service.*;
import com.ldz.znzp.util.NettyUtil;
import io.netty.channel.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.common.Mapper;

import java.math.BigDecimal;
import java.util.*;
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
        ReportData reportData = new ReportData();
        reportData.setRouteId(xl.getId());
        reportData.setRouteName(xl.getXlmc());

        // 获取车辆运行记录
        if (clClyxjl != null){
            reportData.setDirect(clClyxjl.getYxfx());
            reportData.setStationNo(clClyxjl.getZdbh());
            reportData.setType(clClyxjl.getZt());
            reportData.setBus_plate(clClyxjl.getCphm());
            List<ClZnzp> znzps = znzpService.findEq(ClZnzp.InnerColumn.zdId,clClyxjl.getZdId());
            if (znzps.size() != 0){
                reportData.setTid(znzps.get(0).getZdbh());
            }
        }
        log.info(reportData.toString());
        List<Channel> channels = nettyUtil.getChannelList(tid);
//        if (channel == null) return;
        writeResult(channels,reportData);
        return ApiResponse.success(JsonUtil.toJson(reportData));
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
        log.info("转换前-经度："+gpsInfo.getLongitude()+",纬度："+gpsInfo.getLatitude());
        ClGps clGps = gpsService.changeCoordinates(gpsInfo);
        log.info("转换后-经度："+clGps.getBdjd()+",纬度："+clGps.getBdwd());

        // 获取车辆运行记录
        Date now = new Date();
        // 获取当前站点
        boolean hasRecord = record != null;
        ClZd currentStation = null;
        String zt = null;
        Gps gps = new Gps(clGps.getBdjd().doubleValue(),clGps.getBdwd().doubleValue());
        if (record == null){
            log.info("没有运行记录");
            currentStation = findCurrentZd(gps,car,pb);
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
            log.info("已有运行记录："+record.toString());
            String stationId = record.getZdId();
            currentStation = zdService.findById(stationId);
            double distance = DistanceUtil.getShortDistance(currentStation.getJd(),currentStation.getWd(),clGps.getBdjd().doubleValue(),clGps.getBdwd().doubleValue());
            log.info("distance:"+distance);
            if (distance < currentStation.getFw()){
                if (gpsInfo.getEventType().equals("80")){
                    zt = "off";
                }else{
                    zt = "inStation"; // 进站
                }
            }else{
                currentStation = findCurrentZd(gps,car,pb);
            }
        }
        log.info("zt:"+zt);
        if (zt == null){
            if (gpsInfo.getEventType().equals("80")){
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
        log.info("zt:"+zt);
        currentStation.setXlId(route.getId());
        zdService.setStationOrder(currentStation);
        record.setZdbh(currentStation.getRouteOrder());
        record.setZdmc(currentStation.getMc());
        record.setZdId(currentStation.getId());
        record.setJd(clGps.getBdjd());
        record.setWd(clGps.getBdwd());
        record.setZt(zt);
        if (hasRecord){
            clyxjlMapper.updateByPrimaryKeySelective(record);
        }else{
            clyxjlMapper.insertSelective(record);
        }
        if (gpsInfo.getEventType().equals("80")){
            return ApiResponse.fail("设备已离线");
        }
        return ApiResponse.success();
    }

    private List<ClZd> getStationList(ClPb pb){
        // 获取车辆当天线路信息
        String xlId = pb.getXlId();
        // 获取线路站点
        SimpleCondition condition = new SimpleCondition(ClXlzd.class);
        condition.eq(ClXlzd.InnerColumn.xlId,xlId);
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
        currentZdId = "1";
        Gps currentGps = new Gps(jd.doubleValue(),wd.doubleValue());
        ClZd currentZd = zdMapper.selectByPrimaryKey(currentZdId);
        if (currentZd == null)return null;
        Gps stationGps = new Gps(currentZd.getJd(),currentZd.getWd());
        double distance = DistanceUtil.getShortDistance(currentGps,stationGps);
        if (distance < currentZd.getFw()){ // 如果在站点范围之内，则这就是当前站点
            return currentZd;
        }else{ // 如果不在站点范围之内，查找最近的站点
            return findCurrentZd(currentGps,car,pb);
        }
    }

    @Override
    public ApiResponse<String> report(String tid) {
        ClCl car = getByDeviceId(tid);
        if (car == null)return ApiResponse.fail("未找到车辆");
        ClPb pb = getCarPb(car.getClId());
        if (pb == null)return ApiResponse.fail("未找到车辆排班");
        ClXl route = xlService.getByCarId(car.getClId());
        if (route == null)return ApiResponse.fail("未找到车辆线路");
        List<ClClyxjl> clClyxjls = clyxjlService.findEq(ClClyxjl.InnerColumn.clId,car.getClId());
        return report(tid,pb,car,route,clClyxjls.size() == 0 ? null : clClyxjls.get(0));
    }

    @Override
    public ClZd findCurrentZd(Gps currentGps,ClCl car,ClPb pb){
        List<ClZd> stations = getStationList(pb);
        if (stations == null)return null;
        if (stations.size() == 1)return stations.get(0);
        Map<String,ClZd> stationMap = stations.stream().collect(Collectors.toMap(ClZd::getId,p->p));

        Map<String,Double> distanceMap = new HashMap<>();
        for (ClZd station : stations) {
            Gps gps = new Gps(station.getJd(),station.getWd());
            Double d = DistanceUtil.getShortDistance(currentGps,gps);
            // 如果距离小于站点范围，则直接返回当前站点
            if (d < station.getFw()) return station;
            distanceMap.put(station.getId(),d);
        }
        List<Map.Entry<String,Double>> entryList = new ArrayList<>(distanceMap.entrySet());
        entryList.sort((o1, o2) -> {
            Double v1 = o1.getValue();
            Double v2 = o2.getValue();
            return new Double(v1 - v2).intValue();
        });
        String id0 = entryList.get(0).getKey();
        String id1 = entryList.get(1).getKey();
        ClZd station0 = stationMap.get(id0);
        ClZd station1 = stationMap.get(id1);
//        zdService.setStationOrders(station0,station1);
        return station0.getRouteOrder() < station1.getRouteOrder() ? station0 : station1;
    }

    @Override
    public ClPb getCarPb(String carId) {
        SimpleCondition condition = new SimpleCondition(ClPb.class);
        condition.eq(ClPb.InnerColumn.clId,carId);
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
        log.info("channels");
        for (Channel channel : channels) {
            log.info(channel.id().asShortText());
            writeResult(channel,reportData);
        }
    }
}
