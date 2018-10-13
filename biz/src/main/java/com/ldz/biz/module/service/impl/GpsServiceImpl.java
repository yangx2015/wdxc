package com.ldz.biz.module.service.impl;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import com.google.common.eventbus.AsyncEventBus;
import com.google.common.eventbus.Subscribe;
import com.ldz.biz.bean.SendGpsEvent;
import com.ldz.biz.constant.DeviceStatus;
import com.ldz.biz.constant.EventType;
import com.ldz.biz.module.bean.GpsInfo;
import com.ldz.biz.module.bean.WebsocketInfo;
import com.ldz.biz.module.mapper.ClClMapper;
import com.ldz.biz.module.mapper.ClGpsMapper;
import com.ldz.biz.module.mapper.ClZdglMapper;
import com.ldz.biz.module.model.ClCl;
import com.ldz.biz.module.model.ClClyxjl;
import com.ldz.biz.module.model.ClDzwl;
import com.ldz.biz.module.model.ClGps;
import com.ldz.biz.module.model.ClGpsLs;
import com.ldz.biz.module.model.ClPb;
import com.ldz.biz.module.model.ClSbyxsjjl;
import com.ldz.biz.module.model.ClZdgl;
import com.ldz.biz.module.service.ClService;
import com.ldz.biz.module.service.ClyxjlService;
import com.ldz.biz.module.service.GpsService;
import com.ldz.biz.module.service.PbService;
import com.ldz.biz.module.service.ZdglService;
import com.ldz.sys.base.BaseServiceImpl;
import com.ldz.sys.base.LimitedCondition;
import com.ldz.sys.model.SysYh;
import com.ldz.util.bean.ApiResponse;
import com.ldz.util.bean.SimpleCondition;
import com.ldz.util.bean.TrackPoint;
import com.ldz.util.bean.YingyanResponse;
import com.ldz.util.commonUtil.JsonUtil;
import com.ldz.util.gps.DistanceUtil;
import com.ldz.util.gps.Gps;
import com.ldz.util.gps.PositionUtil;
import com.ldz.util.redis.RedisTemplateUtil;
import com.ldz.util.yingyan.GuiJIApi;

import lombok.extern.slf4j.Slf4j;
import tk.mybatis.mapper.common.Mapper;

@Slf4j
@Service
public class GpsServiceImpl extends BaseServiceImpl<ClGps, String> implements GpsService {

	Logger errorLog = LoggerFactory.getLogger("error_info");
    @Autowired
    private ClGpsMapper entityMapper;
    @Autowired
    private RedisTemplateUtil redis;
    @Autowired
    private ClClMapper clclmapper;
    @Autowired
    private ClService clService;
    @Autowired
    private ZdglService zdglservice;
    @Autowired
    private PbService pbService;
    @Autowired
    private ClyxjlService clyxjlService;
    @Autowired
    private ClZdglMapper zdglMapper;
    @Autowired
    private SimpMessagingTemplate websocket;

    AsyncEventBus eventBus = new AsyncEventBus(Executors.newFixedThreadPool(1));

    public GpsServiceImpl() {
        eventBus.register(this);
    }

    @Override
    protected Mapper<ClGps> getBaseMapper() {
        return entityMapper;
    }

    /**
     *
     * 如果eventType == null 是心跳包
     * 有eventType 记录事件表（此类型数据不多，可直接插入数据库）
     * 收到eventType == 80 表示离线
     * 如果终端有电子围栏，则判断是否在电子围栏之内，如果不在电子围栏，则记录异常事件
     * 判断与上一个点的距离，如果距离过近，则不记录gps
     *
     * websocket推送
     * 写轨迹段
     * @param gpsInfo
     * @return
     */
    @Override
    public ApiResponse<String> onReceiveGps(GpsInfo gpsInfo) {
        errorLog.error("gpsInfo:"+gpsInfo.toString());
        // 只要上传点位信息 且不为离线事件 则为在线状态
        if(!StringUtils.equals(gpsInfo.getEventType(),"80")) {
            redis.boundValueOps("offline-" + gpsInfo.getDeviceId()).set(1, 10, TimeUnit.MINUTES);
        }
        if (StringUtils.isEmpty(gpsInfo.getLatitude()) || StringUtils.isEmpty(gpsInfo.getLongitude())
                || StringUtils.isEmpty(gpsInfo.getDeviceId())) {
            return ApiResponse.fail("上传的数据中经度,纬度,或者终端编号为空");
        }

        boolean change = false;
        try{
            change = handleEvent(gpsInfo);
        }catch(Exception e){
        	errorLog.error("解析GPS事件异常", e);
        }
        saveVersionInfoToRedis(gpsInfo);
        if (!change) return ApiResponse.success();

        if (!gpsInfo.getLatitude().equals("-1") && !gpsInfo.getLongitude().equals("-1")) {
            // 只有已录入的设备才上传到鹰眼
            if (isDeviceExist(gpsInfo.getDeviceId())){
                eventBus.post(new SendGpsEvent(gpsInfo));
            }
        }
        
        return ApiResponse.success();
    }

    private boolean isDeviceExist(String deviceId){
        long c = zdglservice.countEq(ClZdgl.InnerColumn.zdbh.name(),deviceId);
        return c > 0;
    }


    private boolean handleEvent(GpsInfo gpsInfo){
        String eventType = gpsInfo.getEventType();
        String deviceId = gpsInfo.getDeviceId();

        String newStatus = "";

        boolean change = false;
        boolean statusChange = false;
        boolean positionChange = false;

        if (StringUtils.isNotEmpty(eventType)) {
            EventType type = EventType.toEmun(eventType);
            if (type == null){
                log.error("未知事件类型："+eventType);
                return false;
            }
            switch (type){
                case IGNITION:
                    newStatus = DeviceStatus.IGNITION.getCode();
                    break;
                case FLAMEOUT:
                    newStatus = DeviceStatus.FLAMEOUT.getCode();
                    break;
                case OFFLINE:
                    newStatus = DeviceStatus.OFFLINE.getCode();
                    break;
                default:
            }
        }

        ClGps newGps = changeCoordinates(gpsInfo);
        errorLog.error("newGps:"+newGps.toString());

        String sczt = gpsInfo.getSczt();
        if ("20".equals(sczt)){
            newStatus = DeviceStatus.FLAMEOUT.getCode();
        }else if ("10".equals(sczt)){
            newStatus = DeviceStatus.IGNITION.getCode();
        }
        String gpsJson = (String) redis.boundValueOps(ClGps.class.getSimpleName() + deviceId).get();
        if (StringUtils.isEmpty(gpsJson)){
            statusChange = true;
        }else{
            ClGps gps = JsonUtil.toBean(gpsJson, ClGps.class);
            if (gps == null){
                statusChange = true;
            }else{
                if (!newStatus.equals(gps.getStatus())){
                    statusChange = true;
                }else if (gps.getBdwd() == null || gps.getBdjd() == null){
                    statusChange = true;
                }else{
                	// 比较redis(实时gps点位)历史数据和这次接收到的数据距离
                    double shortDistance = DistanceUtil.getShortDistance(gps.getBdwd().doubleValue(),
                            gps.getBdjd().doubleValue(), newGps.getBdwd().doubleValue(), newGps.getBdjd().doubleValue());
                    positionChange = shortDistance > 10;
                }
            }
        }

        ClCl car = null;
        List<ClCl> carList = clService.findEq(ClCl.InnerColumn.zdbh,gpsInfo.getDeviceId());
        if (carList.size() != 0) {
            car = carList.get(0);
        }
        if (statusChange || positionChange){
            change = true;
            ClGpsLs gpsls = new ClGpsLs(newGps);
            newGps.setStatus(newStatus);
            gpsls.setId(genId());
            gpsls.setZdbh(deviceId);
            redis.boundListOps(ClGpsLs.class.getSimpleName() + deviceId).leftPush(JsonUtil.toJson(gpsls));
            // 更新存入redis(实时点位)
            redis.boundValueOps(ClGps.class.getSimpleName() + deviceId).set(JsonUtil.toJson(newGps));

            String xlId = "";
            if (car != null){
                SimpleCondition condition = new SimpleCondition(ClPb.class);
                condition.eq(ClPb.InnerColumn.clId,car.getClId());
                List<ClPb> pbList = pbService.findByCondition(condition);
                if (pbList.size() != 0){
                    ClPb pb = pbList.get(0);
                    if(pb != null) {
                        xlId = pb.getXlId();
                    }
                }
            }
            if (newGps.getBdjd() == null){
            	newGps.setBdjd(new BigDecimal(-1));
            }
            if (newGps.getBdwd() == null ){
            	newGps.setBdwd(new BigDecimal(-1));
            }
            WebsocketInfo websocketInfo = changeSocketNew(gpsInfo, newGps, xlId);
            sendWebsocket(websocketInfo);
        }
        clXc(gpsInfo);
        saveClSbyxsjjl(gpsInfo, newGps, car);
        return change;
    }

    /**
     * 如果在线状态发生改变，
     * 或者位置发生改变，
     * 就发送websocket到前台
     * @param websocketInfo
     */
    private void sendWebsocket(WebsocketInfo websocketInfo){
        errorLog.error("sendWebsocket websocketInfo:"+websocketInfo.toString());
        String socket = JsonUtil.toJson(websocketInfo);
        log.info("推送前端的数据为" + socket);
        websocket.convertAndSend("/topic/sendgps-" + websocketInfo.getZdbh(), socket);
    }


    private void saveVersionInfoToRedis(GpsInfo gpsInfo) {
        if (StringUtils.isEmpty(gpsInfo.getCmdParams()) || !gpsInfo.getCmdParams().contains("versionCode")) {
            return;
        }
        Map<String, Object> map = JsonUtil.toMap(gpsInfo.getCmdParams());
        if (map == null) {
            return;
        }
        if (!map.containsKey("versionCode") || !map.containsKey("versionName")) {
            return;
        }
        // 根据设备号查找
        ClZdgl clZdgl = zdglservice.findById(gpsInfo.getDeviceId());
        String versionCode = map.get("versionCode").toString();
        String versionName = map.get("versionName").toString();
        if(StringUtils.isBlank(clZdgl.getVersion()) || !StringUtils.equals(versionCode + "-" + versionName,clZdgl.getVersion())){
            clZdgl.setVersion(versionCode + "-" + versionName);
            zdglMapper.updateByPrimaryKeySelective(clZdgl);
        }
        redis.boundValueOps("versionInfo-" + gpsInfo.getDeviceId()).set(versionCode + "-" + versionName);
    }

    @Subscribe
    public void sendGps(SendGpsEvent event) {
        ClGps entity = changeCoordinates(event.getGpsInfo());
        ClGpsLs gpsls = new ClGpsLs(genId(), entity.getZdbh(), entity.getCjsj(), entity.getJd(), entity.getWd(),
                entity.getGgjd(), entity.getGgwd(), entity.getBdjd(), entity.getBdwd(), entity.getGdjd(), entity.getGdwd(),
                entity.getLx(), entity.getDwjd(), entity.getFxj(), entity.getYxsd());
        YingyanResponse addPoints = GuiJIApi.addPoint(changeModel(gpsls), GuiJIApi.addPointURL);
        log.info(addPoints.toString());
    }


    public TrackPoint changeModel(ClGpsLs clgps) {
        TrackPoint tracktPoint = new TrackPoint();
        tracktPoint.set_object_key(clgps.getYxsd());
        tracktPoint.setAk(GuiJIApi.AK);
        tracktPoint.setCoord_type_input("bd09ll");
        tracktPoint.setEntity_name(clgps.getZdbh());
        tracktPoint.setLatitude(clgps.getBdwd());
        tracktPoint.setLoc_time((clgps.getCjsj().getTime()) / 1000);
        tracktPoint.setLongitude(clgps.getBdjd());
        tracktPoint.setService_id(GuiJIApi.SERVICE_ID);
        tracktPoint.setSpeed(Double.valueOf(clgps.getYxsd()));
        tracktPoint.setDirection((int) Math.ceil(clgps.getFxj().doubleValue()));
        return tracktPoint;
    }


    @Override
    public ClDzwl JudgePoint(ClGps gps, ClCl clcl) {


        List<ClDzwl> seleByZdbh = clcl.getClDzwl();

        if (CollectionUtils.isEmpty(seleByZdbh)) {
            log.info("该终端暂未设置电子围栏");
            return null;
        }

        for (ClDzwl CL : seleByZdbh) {
            String latlngs = CL.getDlxxzb();
            String[] arrays = latlngs.split(";");
            List<Gps> areas = new ArrayList<Gps>();
            for (int i = 0; i < arrays.length; i++) {
                areas.add(new Gps(Double.parseDouble(arrays[i].split(",")[0]),
                        Double.parseDouble(arrays[i].split(",")[1])));
            }
            ArrayList<Double> polygonXA = new ArrayList<Double>();
            ArrayList<Double> polygonYA = new ArrayList<Double>();
            for (int i = 0; i < areas.size(); i++) {
                Gps area = areas.get(i);
                polygonXA.add(area.getWgLon());
                polygonYA.add(area.getWgLat());
            }
            // 判断位置点是否在电子围栏内
            Boolean flag = PositionUtil.isPointInPolygon(gps.getBdwd().doubleValue(),
                    gps.getBdjd().doubleValue(), polygonXA, polygonYA);

            if (flag == false) {

                return CL;
            }
        }

        return null;
    }

    @Override
    public ClGps changeCoordinates(GpsInfo entity) {
        ClGps clGps = new ClGps();
        if (entity.getLatitude() != null) {
            if ("-1".equals(entity.getLatitude())) return clGps;
            clGps.setWd(new BigDecimal(entity.getLatitude()));
        }
        if (entity.getLongitude() != null) {
            if ("-1".equals(entity.getLongitude())) return clGps;
            clGps.setJd(new BigDecimal(entity.getLongitude()));
        }
        // 设备记录时间
        if (StringUtils.isNotEmpty(entity.getStartTime())) {
            clGps.setCjsj(simpledate(entity.getStartTime()));
        }
        if (entity.getGpsjd() != null && entity.getGpsjd().length() <= 3) {
            clGps.setDwjd(Short.valueOf(entity.getGpsjd()));
        }
        if (entity.getFxj() != null) {
            clGps.setFxj(new BigDecimal(entity.getFxj()));
        }
        clGps.setZdbh(entity.getDeviceId());
        if (entity.getSpeed() != null) {
            clGps.setYxsd(String.valueOf(entity.getSpeed()));
        }
        // 将收到的gps转换成火星坐标系(谷歌)
        Gps gps84_To_Gcj02 = PositionUtil.gps84_To_Gcj02(clGps.getWd().doubleValue(), clGps.getJd().doubleValue());
        if (gps84_To_Gcj02 == null){
            log.error("outOfChina:",entity.toString());
            return clGps;
        }
        // 将火星系坐标转换成百度坐标
        Gps gcj02_To_Bd09 = PositionUtil.gcj02_To_Bd09(gps84_To_Gcj02.getWgLat(), gps84_To_Gcj02.getWgLon());
        // 保存gps对象
        clGps.setBdjd(new BigDecimal(gcj02_To_Bd09.getWgLon()));
        clGps.setBdwd(new BigDecimal(gcj02_To_Bd09.getWgLat()));
        clGps.setGgjd(new BigDecimal(gps84_To_Gcj02.getWgLon()));
        clGps.setGgwd(new BigDecimal(gps84_To_Gcj02.getWgLat()));
        return clGps;
    }

    @Override
    public ClSbyxsjjl saveClSbyxsjjl(GpsInfo entity, ClGps clgps, ClCl clcl) {
        // 封装设备事件记录表表
        ClSbyxsjjl clsbyxsjjl = new ClSbyxsjjl();
        clsbyxsjjl.setJd(clgps.getBdjd());
        clsbyxsjjl.setWd(clgps.getBdwd());
        if (clcl != null){
            clsbyxsjjl.setCph(clcl.getCph());
            clsbyxsjjl.setCx(clcl.getCx());
            if (StringUtils.isNotEmpty(clcl.getSjxm())) {
                clsbyxsjjl.setSjxm(clcl.getSjxm());
            }
        }else{  // 设备没有绑定车辆时，默认保存终端信息，后续绑定车辆后可根据终端信息替换
            clsbyxsjjl.setCph(entity.getDeviceId());
        }
        // 获取设备的记录时间
        if (StringUtils.isNotEmpty(entity.getStartTime())) {
            clsbyxsjjl.setCjsj(simpledate(entity.getStartTime()));
        }

        if (StringUtils.isNotEmpty(entity.getGpsjd())) {
            clsbyxsjjl.setJid(new BigDecimal(entity.getGpsjd()));
        }
        if (StringUtils.isNotEmpty(entity.getFxj())) {
            clsbyxsjjl.setYxfx(new Double(entity.getFxj()));
        }
        clsbyxsjjl.setZdbh(entity.getDeviceId());
        clsbyxsjjl.setSjjb("20");
        // 封装设备终端管理
        ClZdgl zdgl = zdglservice.findById(entity.getDeviceId());
        if (zdgl != null){
            // 根据传入的sczt判断终段在线状态
            if (StringUtils.equals(entity.getSczt(), "10")) {
                if(!StringUtils.equals(zdgl.getZt(),"00") || !StringUtils.equals(zdgl.getZxzt(),"00")){
                    zdgl.setZt("00");
                    zdgl.setZxzt("00");
                    zdglservice.update(zdgl);
                }
            }else if (StringUtils.equals(entity.getSczt(), "20")) {
                if(!StringUtils.equals(zdgl.getZt(),"00") || !StringUtils.equals(zdgl.getZxzt(),"10")) {
                    zdgl.setZt("00");
                    zdgl.setZxzt("10");
                    zdglservice.update(zdgl);
                }
            }
        }


        // 判断该点位是否在电子围栏里面
        if (clcl != null){
            ClDzwl judgePoint = JudgePoint(clgps, clcl);
            if (judgePoint != null) {
                clsbyxsjjl.setId(genId());
                clsbyxsjjl.setSjlx("70");
                clsbyxsjjl.setBz(judgePoint.getId());
                redis.boundListOps(ClSbyxsjjl.class.getSimpleName()).leftPush(JsonUtil.toJson(clsbyxsjjl));
                log.info("该点位不在电子围栏里面,事件表存储成功");
            }
        }

        // 没有携带事件类型
        if (StringUtils.isEmpty(entity.getEventType())) {
            return null;
        }


        // 事件类型为点火
        if (StringUtils.equals(entity.getEventType(), "50") || StringUtils.equals(entity.getEventType(), "60") ) {
            clsbyxsjjl.setSjjb("10");
        }


        // 其余异常类型
        clsbyxsjjl.setSjlx(entity.getEventType());
        clsbyxsjjl.setId(genId());
        //判断同一个终端同一个事件类型是不是在1分钟内出现过多次，如果是就可以忽略，避免同一个事件被记录多次
        boolean isExpired = true;
        String redisKey = ClSbyxsjjl.class.getSimpleName() + "-" + entity.getEventType();
        String existEvent = (String)redis.boundValueOps(redisKey).get();
        if (StringUtils.isNotBlank(existEvent)){
        	org.joda.time.format.DateTimeFormatter format = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss"); 
        	DateTime nowDate = DateTime.parse(entity.getStartTime(), format);
        	isExpired = DateTime.parse(existEvent, format).plusMinutes(1).isBefore(nowDate);
        }
        
        if (isExpired){
        	redis.boundValueOps(redisKey).set(entity.getStartTime(), 1, TimeUnit.MINUTES);
            redis.boundListOps(ClSbyxsjjl.class.getSimpleName()).leftPush(JsonUtil.toJson(clsbyxsjjl));
        }
        
        return clsbyxsjjl;
    }

    @Override
    public WebsocketInfo changeSocketNew(GpsInfo gpsinfo, ClGps clpgs, String xlId) {
        errorLog.error("changeSocketNew gpsinfo:"+gpsinfo.toString());
        ClCl seleByZdbh = clclmapper.seleClInfoByZdbh(gpsinfo.getDeviceId());
        // 通过终端id获取车辆信息
        WebsocketInfo info = new WebsocketInfo();

        if (clpgs != null) {
            info.setSpeed(clpgs.getYxsd());
            if (StringUtils.isNotEmpty(gpsinfo.getSczt())) {
                if (StringUtils.equals(gpsinfo.getSczt(), "10")) {
                    if (StringUtils.isNotEmpty(gpsinfo.getEventType())) {
                        info.setEventType(gpsinfo.getEventType());
                    }
                    info.setZxzt("00");
                    info.setBdjd(clpgs.getBdjd().toString());
                    info.setBdwd(clpgs.getBdwd().toString());
                    info.setTime(simpledate(gpsinfo.getStartTime()));
                }
                if (StringUtils.equals(gpsinfo.getSczt(), "20")) {
                    info.setZxzt("10");
                    if (StringUtils.isNotEmpty(gpsinfo.getEventType())) {
                        info.setEventType(gpsinfo.getEventType());
                    }
                    info.setBdjd(clpgs.getBdjd().toString());
                    info.setBdwd(clpgs.getBdwd().toString());
                    info.setTime(simpledate(gpsinfo.getStartTime()));
                }
            }
            if (StringUtils.isNotEmpty(gpsinfo.getEventType())) {
                if (StringUtils.equals(gpsinfo.getEventType(), EventType.OFFLINE.getCode())) {
                    info.setZxzt("20");
                    info.setBdjd(clpgs.getBdjd().toString());
                    info.setBdwd(clpgs.getBdwd().toString());
                    info.setTime(clpgs.getCjsj());
                }
            }
        }
        if(seleByZdbh !=null) {
            info.setXlId(xlId);
            info.setClid(seleByZdbh.getClId());
            info.setCph(seleByZdbh.getCph());
            info.setZdbh(seleByZdbh.getZdbh());
            info.setSjxm(seleByZdbh.getSjxm());
            info.setCx(seleByZdbh.getCx());
            info.setSjxm(seleByZdbh.getSjxm());
        }
        Date today = new Date();
        today.setHours(0);
        today.setMinutes(0);
        today.setSeconds(0);
        SimpleCondition simpleCondition = new SimpleCondition(ClClyxjl.class);
        simpleCondition.eq(ClClyxjl.InnerColumn.zdbh,gpsinfo.getDeviceId());
        simpleCondition.gte(ClClyxjl.InnerColumn.cjsj,today);
        List<ClClyxjl> clyxjls = clyxjlService.findByCondition(simpleCondition);
        if (clyxjls.size() != 0){
            ClClyxjl clyxjl = clyxjls.get(0);
            info.setStationNumber(clyxjl.getZdbh());
        }
        if (!ObjectUtils.isEmpty(seleByZdbh) && StringUtils.isNotEmpty(seleByZdbh.getObdCode())) {
            info.setObdId(seleByZdbh.getObdCode());
        }
        return info;
    }

    @Override
    public ApiResponse<List<WebsocketInfo>> inintGps() {
        ApiResponse<List<WebsocketInfo>> apiResponse = new ApiResponse<>();
        List<WebsocketInfo> list = new ArrayList<>();
        SysYh currentUser = getCurrentUser();
        SimpleCondition condition = new SimpleCondition(ClCl.class);
        String cphLike = getRequestParamterAsString("cphLike");
        if (StringUtils.isNotEmpty(cphLike)) {
            condition.like(ClCl.InnerColumn.cph, cphLike);
        }


        // 将终端编号,车辆信息缓存
        List<ClCl> selectAll = clclmapper.selectByExample(condition);
        Map<String, ClCl> zdbhClMap = selectAll.stream().filter(s -> StringUtils.isNotEmpty(s.getZdbh()))
                .collect(Collectors.toMap(ClCl::getZdbh, ClCl -> ClCl));


        // 获取终端状态
        condition = new LimitedCondition(ClZdgl.class);
        condition.startWith(ClZdgl.InnerColumn.jgdm,currentUser.getJgdm());
        List<ClZdgl> zds = zdglservice.findByCondition(condition);


        // 获取实时点位gps信息
        List<String> deviceIds = zds.stream().map(ClZdgl::getZdbh).collect(Collectors.toList());
        condition = new SimpleCondition(ClGps.class);
        condition.in(ClGps.InnerColumn.zdbh,deviceIds);
        List<ClGps> gpsInit = entityMapper.selectByExample(condition);
        // 将终端状态数据缓存
        Map<String, ClZdgl> zdglmap = zds.stream().filter(s -> StringUtils.isNotEmpty(s.getZdbh()))
                .collect(Collectors.toMap(ClZdgl::getZdbh, ClZdgl -> ClZdgl));

        // 获取不在线的终端id集合
        List<String> lostZD = new ArrayList<>();

        for (ClZdgl clZdgl : zds) {
            if (StringUtils.equals(clZdgl.getZxzt(), "20")) {
                lostZD.add(clZdgl.getZdbh());
            }

        }

        if (CollectionUtils.isNotEmpty(gpsInit)) {
            for (ClGps clgps : gpsInit) {
                if (StringUtils.isNotEmpty(clgps.getZdbh())) {
                    ClCl clCl = zdbhClMap.get(clgps.getZdbh());
                    if (clCl != null) {
                        WebsocketInfo websocketInfo = new WebsocketInfo();
                        websocketInfo.setBdjd(clgps.getBdjd().toString());
                        websocketInfo.setBdwd(clgps.getBdwd().toString());
                        websocketInfo.setClid(clCl.getClId());
                        websocketInfo.setCph(clCl.getCph());
                        websocketInfo.setTime(clgps.getCjsj());
                        websocketInfo.setZdbh(clgps.getZdbh());
                        websocketInfo.setCx(clCl.getCx());
                        websocketInfo.setSjxm(clCl.getSjxm());
                        websocketInfo.setSpeed(clgps.getYxsd());
                        if (StringUtils.isNotEmpty(clCl.getObdCode())) {
                            websocketInfo.setObdId(clCl.getObdCode());
                        }


                        if (lostZD.contains(clgps.getZdbh())) {
                            websocketInfo.setZxzt("20");
                            websocketInfo.setLxsc(nowTime(clgps.getCjsj()));
                            list.add(websocketInfo);
                        } else {
                            websocketInfo.setZxzt(zdglmap.get(clgps.getZdbh()).getZxzt());
                            list.add(websocketInfo);
                        }
                    }
                }
            }
        }
        apiResponse.setResult(list);
        return apiResponse;
    }

    public Date simpledate(String date) {

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date2 = null;
        try {
            date2 = simpleDateFormat.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date2;
    }


    public long nowTime(Date time) {

        Date now = new Date();
        long time2 = now.getTime();
        long time3 = time.getTime();
        return time2 - time3;
    }



    /**
     * 记录行程的开始个结束
     * @param gpsInfo
     */
    private void clXc(GpsInfo gpsInfo){
        // 只有已录入的设备才上传到鹰眼
        if (!isDeviceExist(gpsInfo.getDeviceId())){
            return;
        }
        // 获取 gps 存储事件的 终端号 和 时间
        String time = gpsInfo.getStartTime();
        String startTime  = time; // 开始时间
        String zdbh = gpsInfo.getDeviceId();  // 终端编号
        String routeType = "1"; //0: 行程开始  1： 行程中 2 ： 行程结束
        if(StringUtils.equals(gpsInfo.getEventType(),"50")){
            routeType = "0";
        }else if(StringUtils.equals(gpsInfo.getEventType(),"60")) {
            routeType = "2";
        }
        String s = (String) redis.boundValueOps("CX_" + zdbh).get();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        // 判断 redis中的实时点位是否存在
        if(StringUtils.isNotBlank(s)){ // 实时点位为空 存储当前点位第一开始的点位
            // 更新 开始时间和结束时间的 key 值 （redis 过期事件只能发送 key , 需要用 key 作为业务数据）
            String[] times = s.split(",");
            String type = times[2];
            LocalDateTime preTime = LocalDateTime.parse(times[0],formatter);
            LocalDateTime nowTime = LocalDateTime.parse(time,formatter);
            if(preTime.plusMinutes(5).compareTo(nowTime) > 0 && !StringUtils.equals(type,"2")){ // 说明当前时间仍在行程中
                if(StringUtils.equals(routeType,"2") || StringUtils.equals(routeType,"1")){

                    // 更新实时点位时间
                    startTime = times[1];
                   /* //删除当前终端的开始结束
                    redis.delete("start_end," + zdbh +","+ startTime + "," +times[0] );*/

                }
            }
        }
        // 更新标记
        redis.boundValueOps("CX_"+zdbh).set(time + "," + startTime  + "," + routeType );  // 结束时间 + 开始时间 + 行程状态
        // 添加一条新的记录
        redis.boundValueOps("start_end," + zdbh +","+ startTime ).set("1",5,TimeUnit.MINUTES);

        redis.boundValueOps("start_end," + zdbh + "xc"+ startTime).set(time,10,TimeUnit.MINUTES);



    }


}
