package com.ldz.biz.module.service.impl;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import com.ldz.biz.module.bean.GpsInfo;
import com.ldz.biz.module.bean.websocketInfo;
import com.ldz.biz.module.mapper.ClClMapper;
import com.ldz.biz.module.mapper.ClGpsMapper;
import com.ldz.biz.module.mapper.ClSbyxsjjlMapper;
import com.ldz.biz.module.model.ClCl;
import com.ldz.biz.module.model.ClDzwl;
import com.ldz.biz.module.model.ClGps;
import com.ldz.biz.module.model.ClGpsLs;
import com.ldz.biz.module.model.ClSbyxsjjl;
import com.ldz.biz.module.model.ClZdgl;
import com.ldz.biz.module.service.GpsService;
import com.ldz.biz.module.service.ZdglService;
import com.ldz.sys.base.BaseServiceImpl;
import com.ldz.util.bean.ApiResponse;
import com.ldz.util.commonUtil.JsonUtil;
import com.ldz.util.gps.DistanceUtil;
import com.ldz.util.gps.Gps;
import com.ldz.util.gps.PositionUtil;
import com.ldz.util.redis.RedisTemplateUtil;

import lombok.extern.slf4j.Slf4j;
import tk.mybatis.mapper.common.Mapper;

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
	private ClSbyxsjjlMapper clSbyxsjjlMapper;
	@Autowired
	private ZdglService zdglservice;

	@Autowired
	private SimpMessagingTemplate websocket;

	@Override
	protected Mapper<ClGps> getBaseMapper() {
		return entityMapper;
	}

	@Override
	public ApiResponse<String> filterAndSave(GpsInfo gpsinfo) {

		log.info("上传的gps信息:" + gpsinfo);

		// 将原始点位抓换
		ClGps entity = changeCoordinates(gpsinfo);
		if (entity == null) {
			return ApiResponse.fail("传入的数据有问题");
		}

		// 判断该点位是否携带类型,或者是何种类型分类存储
		ClSbyxsjjl saveClSbyxsjjl = saveClSbyxsjjl(gpsinfo, entity);
		if (saveClSbyxsjjl != null) {
			log.info("该点位属于特殊点位,事件类型为:" + saveClSbyxsjjl.getSjlx());
		}

		// 判断redis(实时gps点位)里面是否存在历史gps数据
		String bean = (String) redis.boundValueOps(ClGps.class.getSimpleName() + entity.getZdbh()).get();
		if (StringUtils.isEmpty(bean)) {
			redis.boundValueOps(ClGps.class.getSimpleName() + entity.getZdbh()).set(JsonUtil.toJson(entity));

			// 初始化点位时 推送坐标到前端
			websocket.convertAndSend("/topic/sendgps", JsonUtil.toJson(changeSocket(gpsinfo, entity)));

			return ApiResponse.success("初始化点位成功");
		}

		// 从redis(实时gps点位)里面取出历史数据
		String bean2 = (String) redis.boundValueOps(ClGps.class.getSimpleName() + entity.getZdbh()).get();
		ClGps object2 = JsonUtil.toBean(bean2, ClGps.class);

		// 比较redis(实时gps点位)历史数据和这次接收到的数据距离
		double shortDistance = DistanceUtil.getShortDistance(object2.getBdwd().doubleValue(),
				object2.getBdjd().doubleValue(), entity.getBdwd().doubleValue(), entity.getBdjd().doubleValue());

		// 两次距离大于10米才存入redis(存储历史gps点位)
		if (shortDistance < 10) {
			return ApiResponse.success("距离上一次点位太近,该点位不存储");
		}
		ClGpsLs gpsls = new ClGpsLs(genId(), entity.getZdbh(), entity.getCjsj(), entity.getJd(), entity.getWd(),
				entity.getGgjd(), entity.getGgwd(), entity.getBdjd(), entity.getBdwd(), entity.getGdjd(),
				entity.getGdwd(), entity.getLx(), entity.getDwjd(), entity.getFxj(), entity.getYxsd());

		redis.boundListOps(ClGpsLs.class.getSimpleName() + entity.getZdbh()).leftPush(JsonUtil.toJson(gpsls));
		// 更新存入redis(实时点位)
		redis.boundValueOps(ClGps.class.getSimpleName() + entity.getZdbh()).set(JsonUtil.toJson(entity));

		// 推送坐标去前端
		String socket = JsonUtil.toJson(changeSocket(gpsinfo, entity));
		log.info("推送前端的数据为" + socket);
		websocket.convertAndSend("/topic/sendgps", socket);

		return ApiResponse.success("该点位redis实时更新,历史存储成功");
	}

	@Override
	public ClDzwl JudgePoint(GpsInfo gps) {

		ClGps changeCoordinates2 = changeCoordinates(gps);

		List<ClDzwl> seleByZdbh = clclmapper.seleByZdbh(gps.getDeviceId()).getClDzwl();

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
			Boolean flag = PositionUtil.isPointInPolygon(changeCoordinates2.getBdjd().doubleValue(),
					changeCoordinates2.getBdwd().doubleValue(), polygonXA, polygonYA);

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
			clGps.setWd(new BigDecimal(entity.getLatitude()));
		}
		if (entity.getLongitude() != null) {
			clGps.setJd(new BigDecimal(entity.getLongitude()));
		}
		// 设备记录时间
		clGps.setCjsj(simpledate(entity.getStartTime()));

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
	public ClSbyxsjjl saveClSbyxsjjl(GpsInfo entity, ClGps clgps) {
		// 封装设备事件记录表表
		ClSbyxsjjl clsbyxsjjl = new ClSbyxsjjl();
		clsbyxsjjl.setJd(clgps.getBdjd());
		clsbyxsjjl.setWd(clgps.getBdwd());
		clsbyxsjjl.setCjsj(new Date());
		if (entity.getGpsjd() != null) {
			clsbyxsjjl.setJid(new BigDecimal(entity.getGpsjd()));
		}
		if (entity.getFxj() != null) {
			clsbyxsjjl.setYxfx(new Double(entity.getFxj()));
		}
		clsbyxsjjl.setZdbh(entity.getDeviceId());
		clsbyxsjjl.setSjjb("20");
		// 封装设备终端管理
		ClZdgl zdgl = new ClZdgl();
		zdgl.setZdbh(entity.getDeviceId());
		zdgl.setXgsj(new Date());

		// 判断该点位是否在电子围栏里面
		ClDzwl judgePoint = JudgePoint(entity);
		if (judgePoint != null) {
			clsbyxsjjl.setId(genId());
			clsbyxsjjl.setSjlx("70");
			clsbyxsjjl.setBz(judgePoint.getId());
			clSbyxsjjlMapper.insertSelective(clsbyxsjjl);
			log.info("该点位不在电子围栏里面,事件表存储成功");
		}
		// 没有携带事件类型
		if (StringUtils.isEmpty(entity.getEventType())) {
			return null;
		}

		// 事件类型为离线
		if (StringUtils.equals(entity.getEventType(), "80")) {
			clsbyxsjjl.setId(genId());
			clsbyxsjjl.setSjlx(entity.getEventType());
			clSbyxsjjlMapper.insertSelective(clsbyxsjjl);
			zdgl.setZt("10");
			zdgl.setZxzt("20");
			zdglservice.insetAndUpdate(zdgl);
			return clsbyxsjjl;
		}

		// 事件类型为点火
		if (StringUtils.equals(entity.getEventType(), "50")) {
			clsbyxsjjl.setId(genId());
			clsbyxsjjl.setSjjb("10");
			clsbyxsjjl.setSjlx(entity.getEventType());
			clSbyxsjjlMapper.insertSelective(clsbyxsjjl);
			zdgl.setZt("00");
			zdgl.setZxzt("00");
			zdglservice.insetAndUpdate(zdgl);
			return clsbyxsjjl;
		}

		// 事件类型为熄火
		if (StringUtils.equals(entity.getEventType(), "60")) {
			clsbyxsjjl.setId(genId());
			clsbyxsjjl.setSjjb("10");
			clsbyxsjjl.setSjlx(entity.getEventType());
			clSbyxsjjlMapper.insertSelective(clsbyxsjjl);
			zdgl.setZt("00");
			zdgl.setZxzt("10");
			zdglservice.insetAndUpdate(zdgl);
			return clsbyxsjjl;
		}
		// 其余异常类型
		clsbyxsjjl.setSjlx(entity.getEventType());
		clsbyxsjjl.setId(genId());
		clSbyxsjjlMapper.insertSelective(clsbyxsjjl);
		return clsbyxsjjl;
	}

	@Override
	public websocketInfo changeSocket(GpsInfo gpsinfo, ClGps clpgs) {
		ClCl seleByZdbh = clclmapper.seleClInfoByZdbh(gpsinfo.getDeviceId());
		// 通过终端id获取车辆信息
		websocketInfo info = new websocketInfo();
		info.setBdjd(clpgs.getBdjd());
		info.setBdwd(clpgs.getBdwd());
		info.setEventType(gpsinfo.getEventType());
		info.setClid(seleByZdbh.getClId());
		info.setCph(seleByZdbh.getCph());
		info.setSpeed(clpgs.getYxsd());
		info.setTime(clpgs.getCjsj());
		info.setZdbh(gpsinfo.getDeviceId());
		info.setCx(seleByZdbh.getCx());
		info.setSjxm(seleByZdbh.getSjxm());
		if (!StringUtils.isEmpty(seleByZdbh.getObdCode())) {
			info.setObdId(seleByZdbh.getObdCode());
		}
		return info;
	}

	@Override
	public ApiResponse<List<websocketInfo>> inintGps() {
		ApiResponse<List<websocketInfo>> apiResponse = new ApiResponse<>();
		List<websocketInfo> list = new ArrayList<>();

		// 将终端编号,车辆信息缓存
		List<ClCl> selectAll = clclmapper.selectAll();
		Map<String, ClCl> clmap = selectAll.stream().filter(s -> StringUtils.isNotEmpty(s.getZdbh()))
				.collect(Collectors.toMap(ClCl::getZdbh, ClCl -> ClCl));

		// 获取每条设备的最新一条的数据
		List<ClSbyxsjjl> gpsInit = clSbyxsjjlMapper.gpsInit();

		List<ClZdgl> zds = zdglservice.findAll();

		List<String> lostZD = new ArrayList<>();

		for (ClZdgl clZdgl : zds) {
			if (StringUtils.equals(clZdgl.getZxzt(), "20")) {
				lostZD.add(clZdgl.getZdbh());
			}

		}
  if (CollectionUtils.isNotEmpty(gpsInit)) {
		for (ClSbyxsjjl clSbyxsjjl : gpsInit) {
			if (StringUtils.isNotEmpty(clSbyxsjjl.getZdbh())) {
				ClCl clCl = clmap.get(clSbyxsjjl.getZdbh());
				if (clCl != null) {
					if (lostZD.contains(clSbyxsjjl.getZdbh())) {
						websocketInfo websocketInfo = new websocketInfo();
						websocketInfo.setBdjd(clSbyxsjjl.getJid());
						websocketInfo.setBdwd(clSbyxsjjl.getWd());
						websocketInfo.setClid(clCl.getClId());
						websocketInfo.setCph(clCl.getCph());
						websocketInfo.setEventType("80");
						websocketInfo.setTime(clSbyxsjjl.getCjsj());
						websocketInfo.setZdbh(clSbyxsjjl.getZdbh());
						websocketInfo.setCx(clCl.getCx());
						list.add(websocketInfo);
					} else {
						websocketInfo websocketInfo = new websocketInfo();
						websocketInfo.setBdjd(clSbyxsjjl.getJid());
						websocketInfo.setBdwd(clSbyxsjjl.getWd());
						websocketInfo.setClid(clCl.getClId());
						websocketInfo.setCph(clCl.getCph());
						websocketInfo.setEventType(clSbyxsjjl.getSjlx());
						websocketInfo.setTime(clSbyxsjjl.getCjsj());
						websocketInfo.setZdbh(clSbyxsjjl.getZdbh());
						websocketInfo.setCx(clCl.getCx());
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

	public static void main(String[] args) {

	}
}
