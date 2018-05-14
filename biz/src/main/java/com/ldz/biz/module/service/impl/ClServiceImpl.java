package com.ldz.biz.module.service.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import com.ldz.biz.module.bean.ClClModel;
import com.ldz.biz.module.mapper.ClClMapper;
import com.ldz.biz.module.model.ClCl;
import com.ldz.biz.module.model.ClJsy;
import com.ldz.biz.module.service.ClService;
import com.ldz.biz.module.service.JsyService;
import com.ldz.sys.base.BaseServiceImpl;
import com.ldz.sys.base.LimitedCondition;
import com.ldz.sys.model.SysJg;
import com.ldz.sys.model.SysYh;
import com.ldz.sys.model.SysZdxm;
import com.ldz.sys.service.JgService;
import com.ldz.sys.service.ZdxmService;
import com.ldz.sys.util.RedisUtil;
import com.ldz.util.bean.ApiResponse;
import com.ldz.util.bean.SimpleCondition;
import com.ldz.util.exception.RuntimeCheck;

import tk.mybatis.mapper.common.Mapper;


@Service
public class ClServiceImpl extends BaseServiceImpl<ClCl, String> implements ClService {
	@Autowired
	private ClClMapper entityMapper;
	@Autowired
	private JgService jgService;
	@Autowired
	private ClService clService;
	@Autowired
	private JsyService jsyService;
	@Autowired
	private ZdxmService zdxmService;
	@Value("${deleteZnzpRedisKeyUrl:http://47.98.39.45:9888/api/deleteRedisKey}")
	private String deleteZnzpRedisKeyUrl;
	@Autowired
	private RedisUtil redisUtil;
	@Autowired
	private JdbcTemplate jdbcTemplate;
	@Override
	protected Mapper<ClCl> getBaseMapper() {
		return entityMapper;
	}

	@Override
	protected Class<?> getEntityCls() {
		return ClCl.class;
	}

	@Override
	public ClCl findByOrgCode(String code) {
		List<ClCl> jgs = findEq(ClCl.InnerColumn.clId, code);
		if (jgs.size() == 0)
			return null;
		return jgs.get(0);
	}

	@Override
	public List<ClCl> getOrgCarList(String orgCode) {
		List<ClCl> carList = clService.findEq(ClCl.InnerColumn.jgdm, orgCode);
		return carList;
	}

	@Override
	public ApiResponse<String> saveEntity(ClCl entity) {
		SysYh user = getCurrentUser();
		SysJg org = jgService.findByOrgCode(user.getJgdm());
		Date now = new Date();
		entity.setCjr(getOperateUser());
		entity.setClId(genId());
		entity.setCjsj(now);
		entity.setJgdm(user.getJgdm());
		entity.setJgmc(org.getJgmc());
		if (StringUtils.isNotBlank(entity.getSjId())) {
			ClJsy jsy = jsyService.findById(entity.getSjId());
			entity.setSjxm(jsy.getXm());
		}

		SimpleCondition condition = new SimpleCondition(SysZdxm.class);
		condition.eq(SysZdxm.InnerColumn.zdlmdm, "ZDCLK0041");
		condition.eq(SysZdxm.InnerColumn.zddm, entity.getZkl());
		List<SysZdxm> zdxms = zdxmService.findByCondition(condition);
		if (zdxms.size() == 0) {
			Short zkl = entity.getZkl();
			String zdCode = "ZDCLK0041";// 默认为载客量
			SysZdxm sysZdxm = new SysZdxm();
			sysZdxm.setZddm(zkl + "");// 字典代码不能为空
			sysZdxm.setZdlmdm(zdCode);// 字典类目代码不能为空
			sysZdxm.setZdmc(zkl + "");// 字典名称不能为空
			zdxmService.add(sysZdxm);
		}

		save(entity);
		redisUtil.deleteRedisKey(deleteZnzpRedisKeyUrl,"com.ldz.znzp.mapper.ClClMapper");
		return ApiResponse.saveSuccess();
	}

	@Override
	public ApiResponse<String> updateEntity(ClCl entity) {
		ClCl findById = findById(entity.getClId());
		RuntimeCheck.ifNull(findById, "未找到记录");
		entity.setXgr(getOperateUser());
		entity.setXgsj(new Date());
		update(entity);
		redisUtil.deleteRedisKey(deleteZnzpRedisKeyUrl,"com.ldz.znzp.mapper.ClClMapper");
		return ApiResponse.success();
	}

	@Override
	public ApiResponse<List<Map<String, Object>>> getVehicleTypeStatistics(String zxzt) {
		// 1、定义初始变量
		ApiResponse<List<Map<String, Object>>> result = new ApiResponse<List<Map<String, Object>>>();
		List<Map<String, Object>> retList = new ArrayList<Map<String, Object>>();
		SysYh user = getCurrentUser();
		String jgdm = user.getJgdm();// 机构ID
		zxzt = StringUtils.trim(zxzt);
		// 2、查询车辆信息
		List<ClClModel> clClList = null;
		if (StringUtils.isNotEmpty(zxzt)) {
			clClList = entityMapper.getVehicleTypeZxztStatistics(jgdm, zxzt);
		} else {
			clClList = entityMapper.getVehicleTypeStatistics(jgdm);
		}
		String cx = "";
		List<Map<String, Object>> childrenList = null;
		Map<String, Object> cxMap = null;// 车型map
		if (clClList != null && clClList.size() > 0) {
			for (ClClModel l : clClList) {
				if (!StringUtils.equals(cx, l.getCx())) {
					if (childrenList != null) {
						cxMap.put("children", childrenList);
						retList.add(cxMap);
					}
					childrenList = new ArrayList<Map<String, Object>>();
					cxMap = new HashMap<String, Object>();// 车型map
					cx = l.getCx();
					cxMap.put("expand", true);
					cxMap.put("title", l.getCxZtMc());
				}

				Map<String, Object> mapCenMap = new HashMap<String, Object>();// 坐标map
				mapCenMap.put("lng", l.getBdJd());
				mapCenMap.put("lat", l.getBdWd());

				Map<String, Object> childrenMap = new HashMap<String, Object>();// 车辆map
				childrenMap.put("mapCen", mapCenMap);
				childrenMap.put("clid", l.getClId());
				childrenMap.put("cx", l.getCx());
				childrenMap.put("title", l.getCph());
				childrenList.add(childrenMap);
			}
		}
		if (cxMap != null && childrenList != null) {
			cxMap.put("children", childrenList);
			retList.add(cxMap);
		}
		result.setResult(retList);
		return result;
	}

	@Override
	public ApiResponse<List<ClCl>> nianshen(ClCl car) {
		ApiResponse<List<ClCl>> apiResponse = new ApiResponse<>();
		long now = new Date().getTime();

		LimitedCondition condition = new LimitedCondition(ClCl.class);
		if (StringUtils.isNotEmpty(car.getCph())){
			condition.like(ClCl.InnerColumn.cph,car.getCph());
		}
		List<ClCl> cllist = entityMapper.selectByExample(condition);
		List<ClCl> cls= new ArrayList<>();
		/*cllist.stream().filter(s -> s.getNssj() != null).filter(s -> (now-s.getNssj().getTime()) < a);*/
		// 年审时间正序
		cllist = cllist.stream().filter(p->p.getNssj() != null).collect(Collectors.toList());
		cllist.sort(Comparator.comparing(ClCl::getNssj));
		for (ClCl clCl : cllist) {
			if (clCl.getNssj()!=null) {
				long nianshen= clCl.getNssj().getTime();
				long time = (nianshen-now)/(24 * 60 * 60 * 1000);
				if (time<90) {
					cls.add(clCl);
				}
			}

		}
		apiResponse.setResult(cls);
		return apiResponse;
	}

	@Override
	public ApiResponse<Map<String, Object>> carAccStatistics() {
//		SysYh user = getCurrentUser();
//		String jgdm = user.getJgdm();
		Date now = new Date();
		String weekStart = convertWeekDate(now);
		String jgdm = "100";
//		String dateRange = " and cjsj> to_date('"+weekStart+"','yyyy-mm-dd') ";
		String dateRange = "  ";
		String sql = "SELECT T1.sjxm,T1.cph,T2.speedupCount,t3.speedownCount,t4.wheelCount,t5.overspeedCount  FROM CL_CL t1 \n" +
				"  LEFT JOIN (select ZDBH ,count(SJLX) as speedupCount FROM CL_SBYXSJJL  t WHERE t.SJLX='10' "+dateRange+" GROUP BY zdbh) t2 on T1.ZDBH=T2.ZDBH\n" +
				"  LEFT JOIN (select ZDBH ,count(SJLX) as speedownCount FROM CL_SBYXSJJL  t WHERE t.SJLX='20' "+dateRange+"  GROUP BY zdbh) t3 on T1.ZDBH=T3.ZDBH\n" +
				"  LEFT JOIN (select ZDBH ,count(SJLX) as wheelCount FROM CL_SBYXSJJL  t WHERE t.SJLX='30' "+dateRange+"  GROUP BY zdbh) t4 on T1.ZDBH=T4.ZDBH\n" +
				"  LEFT JOIN (select ZDBH ,count(SJLX) as overspeedCount FROM CL_SBYXSJJL  t WHERE t.SJLX='40' "+dateRange+"  GROUP BY zdbh) t5 on T1.ZDBH=T5.ZDBH "+
				"  where t1.jgdm like '"+jgdm+"%' ";
		List result = jdbcTemplate.queryForList(sql);
		List<String> carNumberList = new ArrayList<>(result.size());
		List<Object> speedUpCountList = new ArrayList<>(result.size());
		List<Object> speedDownCountList = new ArrayList<>(result.size());
		List<Object> wheelCountList = new ArrayList<>(result.size());
		List<Object> overSpeedCountList = new ArrayList<>(result.size());
		for (Object o : result) {
			Map<String,Object> map = (Map<String, Object>) o;
			String carNumber = (String) map.get("cph");
			carNumberList.add(carNumber);
			speedUpCountList.add(map.get("speedupCount"));
			speedDownCountList.add(map.get("speedownCount"));
			wheelCountList.add(map.get("wheelCount"));
			overSpeedCountList.add(map.get("overspeedCount"));
		}

		Map<String,Object> map = new HashMap<>();

		Map<String,Object> speedUpMap = new HashMap<>();
		speedUpMap.put("name","急加速");
		speedUpMap.put("yAxis",speedUpCountList);

		Map<String,Object> wheelMap = new HashMap<>();
		wheelMap.put("name","急转弯");
		wheelMap.put("yAxis",wheelCountList);


		Map<String,Object> breakMap = new HashMap<>();
		breakMap.put("name","急刹车");
		breakMap.put("yAxis",speedDownCountList);


		Map<String,Object> overSpeedMap = new HashMap<>();
		overSpeedMap.put("name","超速");
		overSpeedMap.put("yAxis",overSpeedCountList);

		map.put("xAxis",carNumberList);
		map.put("speedUpMap",speedUpMap);
		map.put("wheelMap",wheelMap);
		map.put("breakMap",breakMap);
		map.put("overSpeedMap",overSpeedMap);
		return ApiResponse.success(map);
	}


	private static String convertWeekDate(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);

		// 判断要计算的日期是否是周日，如果是则减一天计算周六的，否则会出问题，计算到下一周去了

		int dayWeek = cal.get(Calendar.DAY_OF_WEEK);// 获得当前日期是一个星期的第几天

		if (1 == dayWeek) {

			cal.add(Calendar.DAY_OF_MONTH, -1);

		}

		cal.setFirstDayOfWeek(Calendar.MONDAY);// 设置一个星期的第一天，按中国的习惯一个星期的第一天是星期一

		int day = cal.get(Calendar.DAY_OF_WEEK);// 获得当前日期是一个星期的第几天

		cal.add(Calendar.DATE, cal.getFirstDayOfWeek() - day);// 根据日历的规则，给当前日期减去星期几与一个星期第一天的差值

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd"); // 设置时间格式
		return sdf.format(cal.getTime()); // 周一时间

	}

	public static void main(String[] args) {

	}
	@Override
	public ApiResponse<Map<String, Integer>> getnianshen() {
		//获取当前登陆用户
		SysYh user = getCurrentUser();
		ClCl clCl= new ClCl();
		clCl.setJgdm(user.getJgdm());

		List<ClCl> cllist = entityMapper.select(clCl);


		int thirty=0;
		int sixty=0;
		int ninety=0;
		Date now = new Date();
		for (ClCl cl : cllist) {
			if (cl.getNssj()==null) {
				continue;
			}
			Date nssj = cl.getNssj();
			int cha = differentDaysByMillisecond(now,nssj);
			if (cha<=30) {
				thirty++;
			}
			if (cha>30&&cha<=60) {
				sixty++;
			}
			if (cha>60&&cha<=90) {
				ninety++;
			}

		}
		ApiResponse<Map<String, Integer>> apiResponse = new ApiResponse<>();
		Map<String, Integer> map = new HashMap<>();
		map.put("30", thirty);
		map.put("60", sixty);
		map.put("90", ninety);
		apiResponse.setResult(map);
		return apiResponse;
	}

	 public static int differentDaysByMillisecond(Date date1,Date date2)
	    {
	        int days = (int) ((date2.getTime() - date1.getTime()) / (1000*3600*24));
	        return days;
	    }

}
