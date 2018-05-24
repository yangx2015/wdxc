package com.ldz.job.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.BoundListOperations;
import org.springframework.stereotype.Service;

import com.ldz.job.mapper.ClGpsLsMapper;
import com.ldz.job.mapper.ClGpsMapper;
import com.ldz.job.model.ClCl;
import com.ldz.job.model.ClGps;
import com.ldz.job.model.ClGpsLs;
import com.ldz.job.service.GpsService;
import com.ldz.sys.base.BaseServiceImpl;
import com.ldz.util.bean.TrackJiuPian;
import com.ldz.util.bean.TrackPoint;
import com.ldz.util.bean.TrackPointsForReturn;
import com.ldz.util.bean.TrackPointsForReturn.Point;
import com.ldz.util.bean.YingyanResponse;
import com.ldz.util.commonUtil.JsonUtil;
import com.ldz.util.redis.RedisTemplateUtil;
import com.ldz.util.yingyan.GuiJIApi;

import tk.mybatis.mapper.common.Mapper;

@Service
public class GpsServiceImpl extends BaseServiceImpl<ClGps, String> implements GpsService {

	Logger errorLog = LoggerFactory.getLogger("error_info");
    Logger accessLog= LoggerFactory.getLogger("access_info");  

	@Autowired
	private ClGpsMapper entityMapper;
	@Autowired
	private RedisTemplateUtil redis;
	@Autowired
	private ClGpsLsMapper clgpslsMapper;

	@Override
	protected Mapper<ClGps> getBaseMapper() {
		return entityMapper;
	}

	@Override
	public void insetAndUpdate(ClGps entity) {

		boolean flag = ifExists("zdbh", entity.getZdbh());
		if (flag == true) {
			update(entity);
		} else {
			save(entity);
		}
	}

	@Override
	public void InsetRedisToDb(String zdbh) {

		String bean = (String) redis.boundValueOps(ClGps.class.getSimpleName() + zdbh).get();
		if (bean != null) {
			ClGps object = JsonUtil.toBean(bean, ClGps.class);
			// 将该终端的实时点位插入数据库中
			insetAndUpdate(object);
		}

		BoundListOperations<Object, Object> boundListOps = redis.boundListOps(ClGpsLs.class.getSimpleName() + zdbh);

		String index = (String) boundListOps.index(0);
		if (StringUtils.isNotEmpty(index)) {
			Long length = boundListOps.size();
			List<ClGpsLs> list = new ArrayList<>();
			for (int i = 0; i < length; i++) {
				String clgpsls = (String) boundListOps.rightPop();
				ClGpsLs gpssss = JsonUtil.toBean(clgpsls, ClGpsLs.class);
				
				list.add(gpssss);

			}

			clgpslsMapper.insertList(list);

			for (ClGpsLs clGpsLs : list) {
				TrackPoint changeModel = changeModel(clGpsLs);
				try {
					accessLog.debug("上传鹰眼的数据模型为:"+changeModel);
					YingyanResponse addPoint = GuiJIApi.addPoint(changeModel, GuiJIApi.addPointURL);
					accessLog.debug("成功上传鹰眼的点位:"+clGpsLs.getZdbh()+"状态为:"+addPoint);
				} catch (Exception e) {
					errorLog.error("上传鹰眼失败",e);
					continue;
				}
			}

		}
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
		tracktPoint.setDirection((int)Math.ceil(clgps.getFxj().doubleValue()));
		return tracktPoint;
	}

	/*
	 * public static TrackPointsForReturn guiJiJiuPian1(ClCl clcl) {
	 * 
	 * long endTiem = System.currentTimeMillis(); long startTime = endTiem - (1 * 60
	 * * 60 * 1000);
	 * 
	 * TrackJiuPian guijis = new TrackJiuPian(); guijis.setAk(GuiJIApi.AK);
	 * guijis.setService_id(GuiJIApi.SERVICE_ID);
	 * guijis.setEntity_name(clcl.getZdbh()); guijis.setProcess_option(
	 * "need_denoise=0,need_vacuate=0,need_mapmatch=1,transport_mode=driving");
	 * guijis.setSupplement_mode("driving"); guijis.setSort_type("asc");
	 * guijis.setCoord_type_output("bd09ll"); guijis.setStart_time("1526967180");
	 * guijis.setEnd_time("1526967360"); guijis.setIs_processed("1");
	 * 
	 * TrackPointsForReturn points = GuiJIApi.getPoints(guijis,
	 * GuiJIApi.getPointsURL); return points;
	 * 
	 * }
	 * 
	 * public static void main(String[] args) {
	 * 
	 * ClCl cl = new ClCl();
	 * 
	 * cl.setZdbh("865923030039405"); TrackPointsForReturn guiJiJiuPian =
	 * guiJiJiuPian1(cl); System.out.println(guiJiJiuPian); }
	 */

	@Override
	public void guiJiJiuPian(ClCl clcl) {

		long endTiem = System.currentTimeMillis();
		long startTime = endTiem - (60 * 60 * 1000);

		TrackJiuPian guijis = new TrackJiuPian();
		guijis.setAk(GuiJIApi.AK);
		guijis.setCoord_type_output("bd09ll");
		guijis.setEnd_time(String.valueOf(endTiem / 1000));
		guijis.setEntity_name(clcl.getZdbh());
		guijis.setIs_processed("1");
		guijis.setProcess_option(
				"need_denoise=0,need_vacuate=0,need_mapmatch=1,transport_mode=driving");
		guijis.setService_id(GuiJIApi.SERVICE_ID);
		guijis.setSort_type("asc");
		guijis.setStart_time(String.valueOf(startTime / 1000));
		guijis.setSupplement_mode("driving");

		TrackPointsForReturn points = GuiJIApi.getPoints(guijis, GuiJIApi.getPointsURL);
		List<Point> points2 = points.getPoints();
		if (CollectionUtils.isNotEmpty(points2)) {
			for (Point point : points2) {
				if (StringUtils.isNotEmpty(point.get_object_key())) {

					// 该 条数据的主键
					String id = point.get_object_key();

					ClGpsLs clgpsls = clgpslsMapper.selectByPrimaryKey(id);

					clgpsls.setGdjd(BigDecimal.valueOf(point.getLongitude()));
					clgpsls.setGdwd(BigDecimal.valueOf(point.getLatitude()));
					clgpslsMapper.updateByPrimaryKeySelective(clgpsls);
				}

			}

		} else {
			return;
		}

	}
	public static void main(String[] args) {
		
		
	}
}
