package com.ldz.biz.module.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ldz.biz.module.bean.GpsInfo;
import com.ldz.biz.module.mapper.ClClMapper;
import com.ldz.biz.module.mapper.ClGpsMapper;
import com.ldz.biz.module.mapper.ClSbyxsjjlMapper;
import com.ldz.biz.module.model.ClDzwl;
import com.ldz.biz.module.model.ClGps;
import com.ldz.biz.module.model.ClGpsLs;
import com.ldz.biz.module.model.ClSbyxsjjl;
import com.ldz.biz.module.service.GpsService;
import com.ldz.sys.base.BaseServiceImpl;
import com.ldz.util.bean.ApiResponse;
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

	@Override
	protected Mapper<ClGps> getBaseMapper() {
		return entityMapper;
	}

	@Override
	public ApiResponse<String> filterAndSave(GpsInfo gpsinfo) {
		
		// 将原始点位抓换
		ClGps entity = changeCoordinates(gpsinfo);
		
		// 判断该点位是否携带类型,或者是何种类型分类存储
		ClSbyxsjjl saveClSbyxsjjl = saveClSbyxsjjl(gpsinfo);
		if (saveClSbyxsjjl != null) {
			log.info("该点位属于特殊点位,事件类型为:"+saveClSbyxsjjl.getSjlx());
		}
		
		// 判断redis(实时gps点位)里面是否存在历史gps数据
		ClGps object = (ClGps) redis.boundHashOps("GPSDW").get(entity.getZdbh());
		if (object == null) {
			redis.boundHashOps("GPSDW").put(entity.getZdbh(), entity);
			return ApiResponse.success("初始化点位成功");
		}

		

		// 从redis(实时gps点位)里面取出历史数据
		ClGps object2 = (ClGps) redis.boundHashOps("GPSDW").get(entity.getZdbh());

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

		redis.boundListOps(entity.getZdbh()).leftPush(gpsls);
		// 更新存入redis(实时点位)
		redis.boundHashOps("GPSDW").put(entity.getZdbh(), entity);

		return ApiResponse.success("该点位redis实时更新,历史存储成功");
	}

	@Override
	public ClDzwl JudgePoint(GpsInfo changeCoordinates) {

		ClGps changeCoordinates2 = changeCoordinates(changeCoordinates);
		
		List<ClDzwl> seleByZdbh = clclmapper.seleByZdbh(changeCoordinates.getDeviceId()).getClDzwl();
		
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
		clGps.setJd(new BigDecimal(entity.getLongitude()));
		clGps.setWd(new BigDecimal(entity.getLatitude()));
		clGps.setCjsj(new Date());
		if (entity.getGpsjd()!=null) {
			clGps.setDwjd(Short.valueOf(entity.getGpsjd()));
		}
		if (entity.getFxj()!=null) {
			clGps.setFxj(new BigDecimal(entity.getFxj()));
		}
		clGps.setZdbh(entity.getDeviceId());
		if (entity.getSpeed()!=null) {
			clGps.setYxsd(entity.getSpeed().toString());
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
	public ClSbyxsjjl saveClSbyxsjjl(GpsInfo entity) {

		List<String> sblx = Arrays.asList("10", "20");
		ClSbyxsjjl clsbyxsjjl = new ClSbyxsjjl();
		clsbyxsjjl.setId(genId());
		clsbyxsjjl.setJd(new BigDecimal(entity.getLongitude()));
		clsbyxsjjl.setWd(new BigDecimal(entity.getLatitude()));
		clsbyxsjjl.setCjsj(new Date());
		if (entity.getGpsjd()!=null) {
			clsbyxsjjl.setJid(new BigDecimal(entity.getGpsjd()));
		}
		if (entity.getFxj()!=null) {
			clsbyxsjjl.setYxfx(new Double(entity.getFxj()));
		}
		clsbyxsjjl.setZdbh(entity.getDeviceId());
		clsbyxsjjl.setSjjb("20");
		clsbyxsjjl.setBz("无");
		// 根据传入的数据是否携带事件类型
		if (StringUtils.isEmpty(entity.getEventType())) {

			// 判断点位是否出在电子围栏里面
			ClDzwl judgePoint = JudgePoint(entity);
			if (judgePoint != null) {
				clsbyxsjjl.setSjlx("70");
				clsbyxsjjl.setBz(judgePoint.getId());
				clSbyxsjjlMapper.ClSbyxsjj(clsbyxsjjl);
				return clsbyxsjjl;
			}
			// 如果在电子围栏里面则返回空
			return null;
		}

		// 如果事件类型不为空
		if (sblx.contains(entity.getEventType())) {
			clsbyxsjjl.setSjjb("10");
			clsbyxsjjl.setSjlx(entity.getEventType());
			clSbyxsjjlMapper.ClSbyxsjj(clsbyxsjjl);
			return clsbyxsjjl;
		}

		clsbyxsjjl.setSjlx(entity.getEventType());

		clSbyxsjjlMapper.ClSbyxsjj(clsbyxsjjl);
		return clsbyxsjjl;
	}

}
