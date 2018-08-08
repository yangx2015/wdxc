package com.ldz.biz.module.service.impl;

import com.google.common.eventbus.AsyncEventBus;
import com.google.common.eventbus.Subscribe;
import com.ldz.biz.bean.SendGpsEvent;
import com.ldz.biz.constant.DeviceStatus;
import com.ldz.biz.constant.EventType;
import com.ldz.biz.module.bean.GpsInfo;
import com.ldz.biz.module.bean.WebsocketInfo;
import com.ldz.biz.module.mapper.ClClMapper;
import com.ldz.biz.module.mapper.ClGpsMapper;
import com.ldz.biz.module.model.*;
import com.ldz.biz.module.service.*;
import com.ldz.sys.base.BaseServiceImpl;
import com.ldz.sys.base.LimitedCondition;
import com.ldz.util.bean.ApiResponse;
import com.ldz.util.bean.SimpleCondition;
import com.ldz.util.bean.TrackPoint;
import com.ldz.util.bean.YingyanResponse;
import com.ldz.util.commonUtil.DateUtils;
import com.ldz.util.commonUtil.JsonUtil;
import com.ldz.util.gps.DistanceUtil;
import com.ldz.util.gps.Gps;
import com.ldz.util.gps.PositionUtil;
import com.ldz.util.redis.RedisTemplateUtil;
import com.ldz.util.yingyan.GuiJIApi;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import tk.mybatis.mapper.common.Mapper;

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

@Slf4j
@Service
public class GpsServiceImpl extends BaseServiceImpl<ClGps, String> implements GpsService {

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
        // 只要上传点位信息 则为在线状态
        redis.boundValueOps("offline-"+gpsInfo.getDeviceId()).set(1,10,TimeUnit.MINUTES);
        if (StringUtils.isEmpty(gpsInfo.getLatitude()) || StringUtils.isEmpty(gpsInfo.getLongitude())
                || StringUtils.isEmpty(gpsInfo.getDeviceId())) {
            return ApiResponse.fail("上传的数据中经度,纬度,或者终端编号为空");
        }

        if (!gpsInfo.getLatitude().equals("-1") && !gpsInfo.getLongitude().equals("-1")) {
            eventBus.post(new SendGpsEvent(gpsInfo));
        }
        handleEvent(gpsInfo);

        saveVersionInfoToRedis(gpsInfo);
        return ApiResponse.success();
    }


    private void handleEvent(GpsInfo gpsInfo){
        String eventType = gpsInfo.getEventType();
        String deviceId = gpsInfo.getDeviceId();

        String newStatus = "";

        boolean statusChange = false;
        boolean positionChange = false;

        if (StringUtils.isNotEmpty(eventType)) {
            EventType type = EventType.toEmun(eventType);
            if (type == null){
                log.error("未知事件类型："+eventType);
                return;
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

        String sczt = gpsInfo.getSczt();
        if ("20".equals(sczt)){
            newStatus = DeviceStatus.OFFLINE.getCode();
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
                }
                // 比较redis(实时gps点位)历史数据和这次接收到的数据距离
                double shortDistance = DistanceUtil.getShortDistance(gps.getBdwd().doubleValue(),
                        gps.getBdjd().doubleValue(), newGps.getBdwd().doubleValue(), newGps.getBdjd().doubleValue());
                positionChange = shortDistance > 10;
            }
        }

        ClCl car = null;
        if (statusChange || positionChange){
            ClGpsLs gpsls = new ClGpsLs(newGps);
            gpsls.setId(genId());
            gpsls.setZdbh(deviceId);
            redis.boundListOps(ClGpsLs.class.getSimpleName() + deviceId).leftPush(JsonUtil.toJson(gpsls));
            // 更新存入redis(实时点位)
            redis.boundValueOps(ClGps.class.getSimpleName() + deviceId).set(JsonUtil.toJson(newGps));

            String xlId = "";
            List<ClCl> carList = clService.findEq(ClCl.InnerColumn.zdbh,gpsInfo.getDeviceId());
            if (carList.size() != 0){
                car = carList.get(0);
                ClPb pbExample = new ClPb();
                pbExample.setClId(car.getClId());
                ClPb pb = pbService.findOneByEntity(pbExample);
                if(pb != null) {
                    xlId = pb.getXlId();
                }
            }

            WebsocketInfo websocketInfo = changeSocketNew(gpsInfo, newGps, xlId);
            sendWebsocket(websocketInfo);
//            saveEvent(newGps,gpsInfo,car,eventType);
        }
        clXc(gpsInfo);
        saveClSbyxsjjl(gpsInfo, newGps, car);
    }

    /**
     * 如果在线状态发生改变，
     * 或者位置发生改变，
     * 就发送websocket到前台
     * @param websocketInfo
     */
    private void sendWebsocket(WebsocketInfo websocketInfo){
        String socket = JsonUtil.toJson(websocketInfo);
        log.info("推送前端的数据为" + socket);
        websocket.convertAndSend("/topic/sendgps-" + websocketInfo.getZdbh(), socket);
    }

    private void saveEvent(ClGps gps,GpsInfo gpsInfo,ClCl car,String eventType){
        ClSbyxsjjl clsbyxsjjl = new ClSbyxsjjl();
        if (car != null){
            clsbyxsjjl.setCph(car.getCph());
            clsbyxsjjl.setCx(car.getCx());
            clsbyxsjjl.setSjxm(car.getSjxm());
        }
        clsbyxsjjl.setCjsj(simpledate(gpsInfo.getStartTime()));
        clsbyxsjjl.setId(genId());
        clsbyxsjjl.setJd(gps.getBdjd());
        clsbyxsjjl.setJid(new BigDecimal(gpsInfo.getGpsjd()));
        clsbyxsjjl.setSjjb("10");
        clsbyxsjjl.setSjlx(eventType);
        clsbyxsjjl.setWd(gps.getBdwd());
        clsbyxsjjl.setYxfx(Double.valueOf(gpsInfo.getFxj()));
        clsbyxsjjl.setZdbh(gpsInfo.getDeviceId());
        redis.boundListOps(ClSbyxsjjl.class.getSimpleName()).leftPush(JsonUtil.toJson(clsbyxsjjl));
    }

    @Override
    public ApiResponse<String> filterAndSave(GpsInfo gpsinfo) {
        // 只要上传点位信息 则为在线状态
        redis.boundValueOps("offline-"+gpsinfo.getDeviceId()).set(1,10,TimeUnit.MINUTES);
        log.info("上传的gps信息:" + gpsinfo);
        if (StringUtils.isNotEmpty(gpsinfo.getEventType())) {
            if (StringUtils.equals(gpsinfo.getEventType(), EventType.OFFLINE.getCode())) {
                return handleOffline(gpsinfo);
            }
        }

        if (StringUtils.isEmpty(gpsinfo.getLatitude()) || StringUtils.isEmpty(gpsinfo.getLongitude())
                || StringUtils.isEmpty(gpsinfo.getDeviceId())) {
            return ApiResponse.fail("上传的数据中经度,纬度,或者终端编号为空");
        }

        if (!gpsinfo.getLatitude().equals("-1") && !gpsinfo.getLongitude().equals("-1")) {
            eventBus.post(new SendGpsEvent(gpsinfo));
        }
        clXc(gpsinfo);
        saveVersionInfoToRedis(gpsinfo);

        ClCl seleByZdbh = clclmapper.seleByZdbh(gpsinfo.getDeviceId());
        sbyxsjjl(gpsinfo, seleByZdbh);
        return justDoIt(gpsinfo, seleByZdbh);
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
            zdglservice.updateEntity(clZdgl);
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

    public ApiResponse<String> handleOffline(GpsInfo gpsinfo) {
        //移除掉存储的点火状态 熄火状态
        redis.boundValueOps("ignition" + gpsinfo.getDeviceId()).set(null);
        redis.boundValueOps("flameout" + gpsinfo.getDeviceId()).set(null);
        // 从redis(实时gps点位)里面取出历史数据
        String bean2 = (String) redis.boundValueOps(ClGps.class.getSimpleName() + gpsinfo.getDeviceId()).get();
        ClGps object2 = JsonUtil.toBean(bean2, ClGps.class);

        //非job发送80事件
        if (StringUtils.isEmpty(gpsinfo.getStartTime())) {
            return ApiResponse.fail("非job发送离线");
        }
        //job发送80事件
        String formatdate = formatdate(object2.getCjsj());
        if (StringUtils.equals(gpsinfo.getStartTime(), formatdate)) {
            //补发一次60事件
            ClSbyxsjjl clSbyxsjjl = new ClSbyxsjjl();
            clSbyxsjjl.setCjsj(simpledate(gpsinfo.getStartTime()));
            clSbyxsjjl.setId(genId());
            clSbyxsjjl.setJd(new BigDecimal(gpsinfo.getLongitude()));
            clSbyxsjjl.setWd(new BigDecimal(gpsinfo.getLatitude()));
            clSbyxsjjl.setJid(new BigDecimal(gpsinfo.getGpsjd()));
            clSbyxsjjl.setSjjb("10");
            clSbyxsjjl.setSjlx("60");
            clSbyxsjjl.setYxfx(Double.valueOf(gpsinfo.getFxj()));
            clSbyxsjjl.setZdbh(gpsinfo.getDeviceId());
            // clSbyxsjjlMapper.insertSelective(clSbyxsjjl);

             redis.boundListOps(ClSbyxsjjl.class.getSimpleName()).leftPush(JsonUtil.toJson(clSbyxsjjl));

            WebsocketInfo websocketInfo = changeSocket(gpsinfo, null, object2);
            String socket = JsonUtil.toJson(websocketInfo);
            log.info("推送前端的数据为" + socket);
            websocket.convertAndSend("/topic/sendgps-" + gpsinfo.getDeviceId(), socket);
            return ApiResponse.fail("job发送离线:两次离线时间一致");

        }
        // 记录事件
        ClSbyxsjjl clSbyxsjjl = new ClSbyxsjjl();
        clSbyxsjjl.setCjsj(simpledate(gpsinfo.getStartTime()));
        clSbyxsjjl.setId(genId());
        clSbyxsjjl.setJd(new BigDecimal(gpsinfo.getLongitude()));
        clSbyxsjjl.setWd(new BigDecimal(gpsinfo.getLatitude()));
        clSbyxsjjl.setJid(new BigDecimal(gpsinfo.getGpsjd()));
        clSbyxsjjl.setSjjb("10");
        clSbyxsjjl.setSjlx(EventType.OFFLINE.getCode());
        clSbyxsjjl.setYxfx(Double.valueOf(gpsinfo.getFxj()));
        clSbyxsjjl.setZdbh(gpsinfo.getDeviceId());
        // clSbyxsjjlMapper.insertSelective(clSbyxsjjl);
        redis.boundListOps(ClSbyxsjjl.class.getSimpleName() ).leftPush(JsonUtil.toJson(clSbyxsjjl));
        // 推送坐标去前端
        WebsocketInfo websocketInfo = changeSocket(gpsinfo, null, object2);
        String socket = JsonUtil.toJson(websocketInfo);
        log.info("推送前端的数据为" + socket);
        websocket.convertAndSend("/topic/sendgps-" + gpsinfo.getDeviceId(), socket);
        return ApiResponse.success();
    }


    public ApiResponse<String> justDoIt(GpsInfo gpsinfo, ClCl clcl) {
        // 获取redis(实时gps点位)里面数据
        String bean = (String) redis.boundValueOps(ClGps.class.getSimpleName() + gpsinfo.getDeviceId()).get();
        if (StringUtils.equals(gpsinfo.getLatitude(), "-1") || StringUtils.equals(gpsinfo.getLongitude(), "-1")) {
            if (StringUtils.isNotEmpty(bean)) {
                ClGps bean2 = JsonUtil.toBean(bean, ClGps.class);
                // 判断该点位是否携带类型,或者是何种类型分类存储
                saveClSbyxsjjl(gpsinfo, bean2, clcl);
                WebsocketInfo websocketInfo = changeSocket(gpsinfo, bean2, null);
                String socket = JsonUtil.toJson(websocketInfo);
                websocket.convertAndSend("/topic/sendgps-" + gpsinfo.getDeviceId(), socket);
                return ApiResponse.success("经纬度为-1的点位事件存储成功,并推送给前端" + JsonUtil.toJson(socket));
            }
            if (StringUtils.isEmpty(bean)) {
                return ApiResponse.fail(gpsinfo.getDeviceId() + "初始化失败该设备没有历史定位");
            }
        }

        ClGps entity = changeCoordinates(gpsinfo);
        // 判断该点位是否携带类型,或者是何种类型分类存储
        saveClSbyxsjjl(gpsinfo, entity, clcl);

        // 推送坐标去前端
        WebsocketInfo websocketInfo = changeSocket(gpsinfo, entity, null);
        String socket = JsonUtil.toJson(websocketInfo);
        log.info("推送前端的数据为" + socket);
        websocket.convertAndSend("/topic/sendgps-" + gpsinfo.getDeviceId(), socket);

        if (StringUtils.isEmpty(bean)) {
            redis.boundValueOps(ClGps.class.getSimpleName() + gpsinfo.getDeviceId()).set(JsonUtil.toJson(entity));
            return ApiResponse.success("初始化点位成功");
        }

        ClGps object2 = JsonUtil.toBean(bean, ClGps.class);

        // 比较redis(实时gps点位)历史数据和这次接收到的数据距离
        double shortDistance = DistanceUtil.getShortDistance(object2.getBdwd().doubleValue(),
                object2.getBdjd().doubleValue(), entity.getBdwd().doubleValue(), entity.getBdjd().doubleValue());

        // 两次距离大于10米才存入redis(存储历史gps点位)
        if (shortDistance < 10) {
            return ApiResponse.success("距离上一次点位太近,该点位不存储");
        }

        ClGpsLs gpsls = new ClGpsLs(genId(), entity.getZdbh(), entity.getCjsj(), entity.getJd(), entity.getWd(),
                entity.getGgjd(), entity.getGgwd(), entity.getBdjd(), entity.getBdwd(), entity.getGdjd(), entity.getGdwd(),
                entity.getLx(), entity.getDwjd(), entity.getFxj(), entity.getYxsd());

        redis.boundListOps(ClGpsLs.class.getSimpleName() + entity.getZdbh()).leftPush(JsonUtil.toJson(gpsls));
        // 更新存入redis(实时点位)
        redis.boundValueOps(ClGps.class.getSimpleName() + entity.getZdbh()).set(JsonUtil.toJson(entity));

        return ApiResponse.success("该点位redis实时更新,历史存储成功");
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
        clsbyxsjjl.setCph(clcl.getCph());
        clsbyxsjjl.setCx(clcl.getCx());
        if (StringUtils.isNotEmpty(clcl.getSjxm())) {
            clsbyxsjjl.setSjxm(clcl.getSjxm());
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
        ClZdgl zdgl = new ClZdgl();
        zdgl.setZdbh(entity.getDeviceId());
        zdgl.setXgsj(new Date());

        // 根据传入的sczt判断终段在线状态
        if (StringUtils.equals(entity.getSczt(), "10")) {
            zdgl.setZt("00");
            zdgl.setZxzt("00");
            zdglservice.insetAndUpdate(zdgl);
        }
        if (StringUtils.equals(entity.getSczt(), "20")) {
            zdgl.setZt("00");
            zdgl.setZxzt("10");
            zdglservice.insetAndUpdate(zdgl);
        }

        // 判断该点位是否在电子围栏里面
        ClDzwl judgePoint = JudgePoint(clgps, clcl);
        if (judgePoint != null) {
            clsbyxsjjl.setId(genId());
            clsbyxsjjl.setSjlx("70");
            clsbyxsjjl.setBz(judgePoint.getId());
            // clSbyxsjjlMapper.insertSelective(clsbyxsjjl);
            redis.boundListOps(ClSbyxsjjl.class.getSimpleName()).leftPush(JsonUtil.toJson(clsbyxsjjl));
            log.info("该点位不在电子围栏里面,事件表存储成功");
        }
        // 没有携带事件类型
        if (StringUtils.isEmpty(entity.getEventType())) {
            return null;
        }


        // 事件类型为点火
        if (StringUtils.equals(entity.getEventType(), "50") || StringUtils.equals(entity.getEventType(), "60") ) {
           // clsbyxsjjl.setId(genId());
            clsbyxsjjl.setSjjb("10");
           // clsbyxsjjl.setSjlx(entity.getEventType());
            /*clSbyxsjjlMapper.insertSelective(clsbyxsjjl);
            return clsbyxsjjl;*/
        }

      /*  // 事件类型为熄火
        if (StringUtils.equals(entity.getEventType(), "60")) {
            // clsbyxsjjl.setId(genId());
            clsbyxsjjl.setSjjb("10");
            // clsbyxsjjl.setSjlx(entity.getEventType());
           *//* clSbyxsjjlMapper.insertSelective(clsbyxsjjl);
            return clsbyxsjjl;*//*
        }*/
        // 其余异常类型
        clsbyxsjjl.setSjlx(entity.getEventType());
        clsbyxsjjl.setId(genId());
        redis.boundListOps(ClSbyxsjjl.class.getSimpleName()).leftPush(JsonUtil.toJson(clsbyxsjjl));
        // clSbyxsjjlMapper.insertSelective(clsbyxsjjl);
        return clsbyxsjjl;
    }

    @Override
    public WebsocketInfo changeSocket(GpsInfo gpsinfo, ClGps clpgs, ClGps gpsss) {
        ClCl seleByZdbh = clclmapper.seleClInfoByZdbh(gpsinfo.getDeviceId());
        // 通过终端id获取车辆信息
        WebsocketInfo info = new WebsocketInfo();

        if (clpgs != null) {
            if (StringUtils.isNotEmpty(gpsinfo.getSczt())) {
                if (StringUtils.equals(gpsinfo.getSczt(), "10")) {
                    if (StringUtils.isNotEmpty(gpsinfo.getEventType())) {
                        info.setEventType(gpsinfo.getEventType());
                    }
                    info.setZxzt("00");
                    info.setBdjd(clpgs.getBdjd().toString());
                    info.setBdwd(clpgs.getBdwd().toString());
                    info.setTime(simpledate(gpsinfo.getStartTime()));
                    info.setSpeed(clpgs.getYxsd());
                }
                if (StringUtils.equals(gpsinfo.getSczt(), "20")) {
                    info.setZxzt("10");
                    if (StringUtils.isNotEmpty(gpsinfo.getEventType())) {
                        info.setEventType(gpsinfo.getEventType());
                    }
                    info.setBdjd(clpgs.getBdjd().toString());
                    info.setBdwd(clpgs.getBdwd().toString());
                    info.setTime(simpledate(gpsinfo.getStartTime()));
                    info.setSpeed(clpgs.getYxsd());
                }
            }
        }
        if (gpsss != null) {
            if (StringUtils.isNotEmpty(gpsinfo.getEventType())) {
                if (StringUtils.equals(gpsinfo.getEventType(), EventType.OFFLINE.getCode())) {
                    info.setZxzt("20");
                    info.setBdjd(gpsss.getBdjd().toString());
                    info.setBdwd(gpsss.getBdwd().toString());
                    info.setTime(gpsss.getCjsj());
                    info.setSpeed(gpsss.getYxsd());
                }
            }
        }
        info.setClid(seleByZdbh.getClId());
        info.setCph(seleByZdbh.getCph());
        info.setZdbh(seleByZdbh.getZdbh());
        info.setSjxm(seleByZdbh.getSjxm());
        info.setCx(seleByZdbh.getCx());
        info.setSjxm(seleByZdbh.getSjxm());
        if (StringUtils.isNotEmpty(seleByZdbh.getObdCode())) {
            info.setObdId(seleByZdbh.getObdCode());
        }
        return info;
    }
    @Override
    public WebsocketInfo changeSocketNew(GpsInfo gpsinfo, ClGps clpgs, String xlId) {
        ClCl seleByZdbh = clclmapper.seleClInfoByZdbh(gpsinfo.getDeviceId());
        // 通过终端id获取车辆信息
        WebsocketInfo info = new WebsocketInfo();

        if (clpgs != null) {
            if (StringUtils.isNotEmpty(gpsinfo.getSczt())) {
                if (StringUtils.equals(gpsinfo.getSczt(), "10")) {
                    if (StringUtils.isNotEmpty(gpsinfo.getEventType())) {
                        info.setEventType(gpsinfo.getEventType());
                    }
                    info.setZxzt("00");
                    info.setBdjd(clpgs.getBdjd().toString());
                    info.setBdwd(clpgs.getBdwd().toString());
                    info.setTime(simpledate(gpsinfo.getStartTime()));
                    info.setSpeed(clpgs.getYxsd());
                }
                if (StringUtils.equals(gpsinfo.getSczt(), "20")) {
                    info.setZxzt("10");
                    if (StringUtils.isNotEmpty(gpsinfo.getEventType())) {
                        info.setEventType(gpsinfo.getEventType());
                    }
                    info.setBdjd(clpgs.getBdjd().toString());
                    info.setBdwd(clpgs.getBdwd().toString());
                    info.setTime(simpledate(gpsinfo.getStartTime()));
                    info.setSpeed(clpgs.getYxsd());
                }
            }
            if (StringUtils.isNotEmpty(gpsinfo.getEventType())) {
                if (StringUtils.equals(gpsinfo.getEventType(), EventType.OFFLINE.getCode())) {
                    info.setZxzt("20");
                    info.setBdjd(clpgs.getBdjd().toString());
                    info.setBdwd(clpgs.getBdwd().toString());
                    info.setTime(clpgs.getCjsj());
                    info.setSpeed(clpgs.getYxsd());
                }
            }
        }
        info.setClid(seleByZdbh.getClId());
        info.setCph(seleByZdbh.getCph());
        info.setZdbh(seleByZdbh.getZdbh());
        info.setSjxm(seleByZdbh.getSjxm());
        info.setCx(seleByZdbh.getCx());
        info.setSjxm(seleByZdbh.getSjxm());
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
        if (StringUtils.isNotEmpty(seleByZdbh.getObdCode())) {
            info.setObdId(seleByZdbh.getObdCode());
        }
        return info;
    }

    @Override
    public ApiResponse<List<WebsocketInfo>> inintGps() {
        ApiResponse<List<WebsocketInfo>> apiResponse = new ApiResponse<>();
        List<WebsocketInfo> list = new ArrayList<>();

        SimpleCondition condition = new SimpleCondition(ClCl.class);
        String cphLike = getRequestParamterAsString("cphLike");
        if (StringUtils.isNotEmpty(cphLike)) {
            condition.like(ClCl.InnerColumn.cph, cphLike);
        }

//        SysYh user = getCurrentUser();
//        condition.eq(ClCl.InnerColumn.jgdm, user.getJgdm());
        // 将终端编号,车辆信息缓存
        List<ClCl> selectAll = clclmapper.selectByExample(condition);
        Map<String, ClCl> zdbhClMap = selectAll.stream().filter(s -> StringUtils.isNotEmpty(s.getZdbh()))
                .collect(Collectors.toMap(ClCl::getZdbh, ClCl -> ClCl));


        // 获取终端状态
        condition = new LimitedCondition(ClZdgl.class);
//        condition.eq(ClZdgl.InnerColumn.jgdm, user.getJgdm());
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

    public String formatdate(Date date) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String format = simpleDateFormat.format(date);
        return format;
    }

    public long nowTime(Date time) {

        Date now = new Date();
        long time2 = now.getTime();
        long time3 = time.getTime();
        return time2 - time3;
    }

    @Override
    public void sbyxsjjl(GpsInfo info, ClCl clcl) {
        ClGps clgps = changeCoordinates(info);

        if (StringUtils.isNotEmpty(info.getEventType())) {

            if (StringUtils.equals(info.getEventType(), "50")) {
                //熄火状态的redis 设置为空
                redis.boundValueOps("flameout" + info.getDeviceId()).set(null);
                redis.boundValueOps("ignition" + info.getDeviceId()).set(clgps);
                return;
            }
            if (StringUtils.equals(info.getEventType(), "60")) {
                //点火状态的redis 设置为空
                redis.boundValueOps("flameout" + info.getDeviceId()).set(clgps);
                redis.boundValueOps("ignition" + info.getDeviceId()).set(null);
                return;
            }
        }


        if (StringUtils.equals(info.getSczt(), "10")) {

            //熄火状态的redis 设置为空
            redis.boundValueOps("flameout" + info.getDeviceId()).set(null);
            ClGps object = (ClGps) redis.boundValueOps("ignition" + info.getDeviceId()).get();

            if (ObjectUtils.isEmpty(object)) {
                //点火状态redis赋值
                redis.boundValueOps("ignition" + info.getDeviceId()).set(clgps);
                ClSbyxsjjl clsbyxsjjl = new ClSbyxsjjl();
                clsbyxsjjl.setCjsj(simpledate(info.getStartTime()));
                clsbyxsjjl.setCph(clcl.getCph());
                clsbyxsjjl.setCx(clcl.getCx());
                clsbyxsjjl.setId(genId());
                clsbyxsjjl.setJd(clgps.getBdjd());
                clsbyxsjjl.setJid(new BigDecimal(info.getGpsjd()));
                clsbyxsjjl.setSjjb("10");
                clsbyxsjjl.setSjlx("50");
                clsbyxsjjl.setSjxm(clcl.getSjxm());
                clsbyxsjjl.setWd(clgps.getBdwd());
                clsbyxsjjl.setYxfx(Double.valueOf(info.getFxj()));
                clsbyxsjjl.setZdbh(info.getDeviceId());
                // clSbyxsjjlMapper.insertSelective(clsbyxsjjl);
                 redis.boundListOps(ClSbyxsjjl.class.getSimpleName()).leftPush(JsonUtil.toJson(clsbyxsjjl));
                return;
            } else {
                return;
            }
        }

        if (StringUtils.equals(info.getSczt(), "20")) {
            //将点火设置为空
            redis.boundValueOps("ignition" + info.getDeviceId()).set(null);
            ClGps object = (ClGps) redis.boundValueOps("flameout" + info.getDeviceId()).get();
            if (ObjectUtils.isEmpty(object)) {
                //熄火状态的redis赋值
                redis.boundValueOps("flameout" + info.getDeviceId()).set(clgps);
                ClSbyxsjjl clsbyxsjjl = new ClSbyxsjjl();
                clsbyxsjjl.setCjsj(simpledate(info.getStartTime()));
                clsbyxsjjl.setCph(clcl.getCph());
                clsbyxsjjl.setCx(clcl.getCx());
                clsbyxsjjl.setId(genId());
                clsbyxsjjl.setJd(clgps.getBdjd());
                clsbyxsjjl.setJid(new BigDecimal(info.getGpsjd()));
                clsbyxsjjl.setSjjb("10");
                clsbyxsjjl.setSjlx("60");
                clsbyxsjjl.setSjxm(clcl.getSjxm());
                clsbyxsjjl.setWd(clgps.getBdwd());
                clsbyxsjjl.setYxfx(Double.valueOf(info.getFxj()));
                clsbyxsjjl.setZdbh(info.getDeviceId());
                // clSbyxsjjlMapper.insertSelective(clsbyxsjjl);
                redis.boundListOps(ClSbyxsjjl.class.getSimpleName()).leftPush(JsonUtil.toJson(clsbyxsjjl));
                return;
            } else {
                return;
            }
        }


    }


    /**
     * 记录行程的开始个结束
     * @param gpsInfo
     */
    private void clXc(GpsInfo gpsInfo){
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

        redis.boundValueOps("start_end," + zdbh + "xc"+ startTime).set(time);



    }


    public static void main(String[] args) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime preTime = LocalDateTime.parse("2018-08-07 11:31:12",formatter);
        LocalDateTime nowTime = LocalDateTime.parse("2018-08-07 11:31:08",formatter);
        System.out.println(nowTime.plusMinutes(5).compareTo(preTime ));
    }

}
