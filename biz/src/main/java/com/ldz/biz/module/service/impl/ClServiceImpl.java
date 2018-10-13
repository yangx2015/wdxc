package com.ldz.biz.module.service.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.joda.time.DateTime;
import org.joda.time.Years;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import com.google.common.collect.Lists;
import com.ldz.biz.module.bean.ClClModel;
import com.ldz.biz.module.bean.SafedrivingModel;
import com.ldz.biz.module.mapper.ClClMapper;
import com.ldz.biz.module.model.ClCl;
import com.ldz.biz.module.model.ClClyxjl;
import com.ldz.biz.module.model.ClJsy;
import com.ldz.biz.module.model.ClZdgl;
import com.ldz.biz.module.service.ClService;
import com.ldz.biz.module.service.ClyxjlService;
import com.ldz.biz.module.service.JsyService;
import com.ldz.biz.module.service.ZdglService;
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
	@Value("${znzpurl}")
	private String deleteZnzpRedisKeyUrl;
	@Autowired
	private ZdglService zdglService;
	@Autowired
	private ClyxjlService clyxjlService;
	@Autowired
	private RedisUtil redisUtil;
	@Autowired
	private JdbcTemplate jdbcTemplate;
	private static final String QZBF = "强制报废"; // 强制报废
	@Override
	protected Mapper<ClCl> getBaseMapper() {
		return entityMapper;
	}

	@Override
	protected Class<?> getEntityCls() {
		return ClCl.class;
	}
//车辆删除后，同步移除
	@Override
	public void remove(String id) {
		int i=getBaseMapper().deleteByPrimaryKey(id);
		if(i==1){
//			移除 车辆运行记录表中的记录
			ClClyxjl clClyxjl=new ClClyxjl();
			clClyxjl.setClId(id);//车辆ID
			clyxjlService.remove(clClyxjl);
		}
	}

	@Override
	public void remove(ClCl entity) {
		int i=getBaseMapper().delete(entity);
		if(i==1){
//			移除 CL_CLYXJL
			ClClyxjl clClyxjl=new ClClyxjl();
			clClyxjl.setClId(entity.getClId());//车辆ID
			clyxjlService.remove(clClyxjl);
		}
	}

	@Override
	public ApiResponse<String> removeIds(String[] ids) {
		for (String id : ids) {
			int i=getBaseMapper().deleteByPrimaryKey(id);
			if(i==1){
//			移除 CL_CLYXJL
				ClClyxjl clClyxjl=new ClClyxjl();
				clClyxjl.setClId(id);//车辆ID
				clyxjlService.remove(clClyxjl);
			}
		}
		return ApiResponse.deleteSuccess();
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
	
	/**
     * 计算车辆年审时间
     * @param entity
     * @return
     */
    public Date getNsrq(ClCl entity, boolean nextYear){
    	Date nsrq = null;
	    /*
		  	车辆类型为小型汽车的车辆，且使用性质为非营运的车辆：距注册日期6年以内的每2年检验1次；超过6年的，每年检验1次；超过15年的，每6个月检验1次。
			车辆类型为中型汽车、大型汽车的车辆，且使用性质为非营运的车辆：距注册日期10年以内每年检验1次；超过10年的，每6个月检验1次。
			使用性质为营运的车辆：距注册日期5年以内每年检验1次；超过5年的，每6个月检验1次。超过10年的状态为“需强制报废”。
		*/
		//车辆初次登记日期
		DateTime ccdjrqDate = DateTime.now().withMillis(entity.getCcdjrq().getTime());
		DateTime currentDate = DateTime.now();
		if (nextYear){
			currentDate = DateTime.now().withMillis(entity.getNssj().getTime());
		}
		//计算初登日期和当前时间相差多少年
		int year = Years.yearsBetween(ccdjrqDate, currentDate).getYears();
		if (year == 0){
			year = 1;
		}
		if ("10".equals(entity.getCx())){
			List<DateTime> dateLists = Lists.newArrayList();
			//计算出小车的6年内年审日期
			for (int i=0; i<3; i++){
				int step = (i + 1) * 2;
				dateLists.add(ccdjrqDate.plusYears(step));
			}
			//小型汽车年审
			if (year < 6){
				for (int i=0; i<dateLists.size(); i++){
					DateTime tmp = dateLists.get(i);
					if (tmp.isAfter(currentDate)){
						nsrq = tmp.toDate();
						break;
					}
				}
			}else{
				//超过6年小于15年的每1年检验1次
				DateTime tmp = ccdjrqDate.plusYears(year).dayOfMonth().withMaximumValue();
				if (tmp.isAfter(currentDate)){
					nsrq = tmp.toDate();
				}else{
					nsrq = tmp.plusYears(1).toDate();
				}
			}
		}else{
			//非小型汽车年审
			DateTime tmp = ccdjrqDate.plusYears(year).dayOfMonth().withMaximumValue();
			if (tmp.isAfter(currentDate)){
				nsrq = tmp.toDate();
			}else{
				nsrq = tmp.plusYears(1).toDate();
			}
		}
		
		return nsrq;
    }
    
    @Override
    public ApiResponse<String> nextNssjYear(String id) {
    	ClCl exist = this.findById(id);
    	if (exist == null){
    		return ApiResponse.fail("车辆信息不存在！");
    	}else if (exist.getNssj() == null){
    		exist.setNssj(getNsrq(exist, false));
    	}else{
    		exist.setNssj(getNsrq(exist, true));
    	}
    	
    	update(exist);
    	return ApiResponse.success("年审日期更新成功！");
    }

	@Override
	public ApiResponse<String> saveEntity(ClCl entity) {
		SysYh user = getCurrentUser();
		SysJg org = jgService.findByOrgCode(user.getJgdm());
		RuntimeCheck.ifBlank(entity.getCph(), "请先输入车牌号码");
		RuntimeCheck.ifTrue(entity.getCph().length() < 7, "请输入正确的车牌号码");
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
		//计算车辆年审日期
		if (entity.getCcdjrq() != null){
			//因为前天传到后台的Date类型天数下标是从0开始的，所以后台需要补一天
			entity.setCcdjrq(DateTime.now().withMillis(entity.getCcdjrq().getTime()).plusDays(1).toDate());
			entity.setNssj(getNsrq(entity, false));
		}
		if (entity.getBxsj() != null){
			//因为前天传到后台的Date类型天数下标是从0开始的，所以后台需要补一天
			entity.setBxsj(DateTime.now().withMillis(entity.getBxsj().getTime()).plusDays(1).toDate());
		}

		SimpleCondition condition = new SimpleCondition(SysZdxm.class);
		condition.eq(SysZdxm.InnerColumn.zdlmdm, "ZDCLK0041");
		condition.eq(SysZdxm.InnerColumn.zddm, entity.getZkl());
		List<SysZdxm> zdxms = zdxmService.findByCondition(condition);
		if (CollectionUtils.isEmpty(zdxms)) {
			Short zkl = entity.getZkl();
			String zdCode = "ZDCLK0041";// 默认为载客量
			SysZdxm sysZdxm = new SysZdxm();
			sysZdxm.setZddm(zkl + "");// 字典代码不能为空
			sysZdxm.setZdlmdm(zdCode);// 字典类目代码不能为空
			sysZdxm.setZdmc(zkl + "");// 字典名称不能为空
			zdxmService.add(sysZdxm);
		}

		save(entity);
		redisUtil.deleteRedisKey(deleteZnzpRedisKeyUrl + "/deleteRedisKey","com.ldz.znzp.mapper.ClClMapper");
		return ApiResponse.saveSuccess();
	}

	@Override
	public ApiResponse<String> updateEntity(ClCl entity) {
		ClCl findById = findById(entity.getClId());
		RuntimeCheck.ifNull(findById, "未找到记录");
		entity.setXgr(getOperateUser());
		entity.setXgsj(new Date());
		if (entity.getBxsj() != null){
			//因为前天传到后台的Date类型天数下标是从0开始的，所以后台需要补一天
			entity.setBxsj(DateTime.now().withMillis(entity.getBxsj().getTime()).plusDays(1).toDate());
		}
		//如果前台车辆初登日期发生了变化，则重新计算车辆年审日期
		if (entity.getCcdjrq() != null){
			entity.setCcdjrq(DateTime.now().withMillis(entity.getCcdjrq().getTime()).plusDays(1).toDate());
			if (entity.getCcdjrq().getTime() != findById.getCcdjrq().getTime()){
				entity.setNssj(getNsrq(entity, false));
			}
		}
		update(entity);
		redisUtil.deleteRedisKey(deleteZnzpRedisKeyUrl + "/deleteRedisKey","com.ldz.znzp.mapper.ClClMapper");
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
					childrenList = new ArrayList<>();
					cxMap = new HashMap<>();// 车型map
					cx = l.getCx();
					cxMap.put("expand", true);
					cxMap.put("title", l.getCxZtMc());
				}

				Map<String, Object> mapCenMap = new HashMap<>();// 坐标map
				mapCenMap.put("lng", l.getBdJd());
				mapCenMap.put("lat", l.getBdWd());

				Map<String, Object> childrenMap = new HashMap<>();// 车辆map
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
	public ApiResponse<Map<String, Object>> carAccStatistics(Integer days,String type) {
		SysYh user = getCurrentUser();
		String jgdm = user.getJgdm();
		Calendar now = Calendar.getInstance();
		if (days == null)days = -3;
		if (days > 0)days = -(days-1);
		now.add(Calendar.DATE, days);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd"); // 设置时间格式
		String weekStart = sdf.format(now.getTime());
		String dateRange = " and cjsj> to_date('"+weekStart+"','yyyy-mm-dd') ";
		String sql = "SELECT T1.sjxm,T1.cph,T2.speedupCount,t3.speedownCount,t4.wheelCount,t5.overspeedCount  FROM CL_CL t1 \n" +
				"  LEFT JOIN (select ZDBH ,count(SJLX) as speedupCount FROM CL_SBYXSJJL  t WHERE t.SJLX='10' "+dateRange+" GROUP BY zdbh) t2 on T1.ZDBH=T2.ZDBH\n" +
				"  LEFT JOIN (select ZDBH ,count(SJLX) as speedownCount FROM CL_SBYXSJJL  t WHERE t.SJLX='20' "+dateRange+"  GROUP BY zdbh) t3 on T1.ZDBH=T3.ZDBH\n" +
				"  LEFT JOIN (select ZDBH ,count(SJLX) as wheelCount FROM CL_SBYXSJJL  t WHERE t.SJLX='30' "+dateRange+"  GROUP BY zdbh) t4 on T1.ZDBH=T4.ZDBH\n" +
				"  LEFT JOIN (select ZDBH ,count(SJLX) as overspeedCount FROM CL_SBYXSJJL  t WHERE t.SJLX='40' "+dateRange+"  GROUP BY zdbh) t5 on T1.ZDBH=T5.ZDBH "+
				"  where t1.jgdm like '"+jgdm+"%' ";
		List result = jdbcTemplate.queryForList(sql);
		List<SafedrivingModel> list = new ArrayList<>(result.size());
		for (Object o : result) {
			Map<String,Object> map = (Map<String, Object>) o;
			SafedrivingModel model = new SafedrivingModel();
			model.setCph((String) map.get("cph"));
			model.setOverspeedCount(map.get("overspeedCount") == null ? 0 : Integer.parseInt(map.get("overspeedCount").toString()));
			model.setSpeedownCount(map.get("speedownCount") == null ? 0 : Integer.parseInt(map.get("speedownCount").toString()));
			model.setSpeedupCount(map.get("speedupCount") == null ? 0 : Integer.parseInt(map.get("speedupCount").toString()));
			model.setWheelCount(map.get("wheelCount") == null ? 0 : Integer.parseInt(map.get("wheelCount").toString()));
			model.setSjxm((String) map.get("sjxm"));

			if ("aqjs".equals(type)){
				int total = model.getSpeedupCount() +
						model.getSpeedupCount() +
						model.getWheelCount() +
						model.getOverspeedCount();
				model.setTotal(total);
			}
			list.add(model);
		}

		if ("aqjs".equals(type)){
			list.sort(Comparator.comparingInt(SafedrivingModel::getTotal).reversed());
			if (list.size()>10){
				list = list.subList(0,10);
			}
		}else if ("cstj".equals(type)){
			list.sort(Comparator.comparingInt(SafedrivingModel::getOverspeedCount).reversed());
			if (list.size()>10){
				list = list.subList(0,10);
			}
		}

		List<String> carNumberList = new ArrayList<>(list.size());
		List<Object> speedUpCountList = new ArrayList<>(list.size());
		List<Object> speedDownCountList = new ArrayList<>(list.size());
		List<Object> wheelCountList = new ArrayList<>(list.size());
		List<Object> overSpeedCountList = new ArrayList<>(list.size());
		List<String> driverNames = new ArrayList<>(list.size());
		for (SafedrivingModel model : list) {
			String carNumber = model.getCph();
			String driverName = model.getSjxm();
			carNumberList.add(carNumber);
			driverNames.add(driverName);

			speedUpCountList.add(model.getSpeedupCount());
			speedDownCountList.add(model.getSpeedownCount());
			wheelCountList.add(model.getWheelCount());
			overSpeedCountList.add(model.getOverspeedCount());
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

		map.put("driverNames",driverNames);
		map.put("carNumbers",carNumberList);
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

	@Override
	public ApiResponse<String> unbindDevice(String carId) {
		RuntimeCheck.ifBlank(carId,"请选择车辆");
		ClCl car = entityMapper.selectByPrimaryKey(carId);
		RuntimeCheck.ifNull(car,"未找到车辆");
		car.setZdbh(null);
		entityMapper.updateByPrimaryKey(car);
		return ApiResponse.success();
	}

	@Override
	public ApiResponse<String> unbindDriver(String carId) {
		RuntimeCheck.ifBlank(carId,"请选择车辆");
		ClCl car = entityMapper.selectByPrimaryKey(carId);
		RuntimeCheck.ifNull(car,"未找到车辆");
		car.setSjxm(null);
		car.setSjId(null);
		entityMapper.updateByPrimaryKey(car);
		return ApiResponse.success();
	}

	@Override
	public ApiResponse<String> bindDriver(String carId, String driverId) {
		RuntimeCheck.ifBlank(carId,"请选择车辆");
		RuntimeCheck.ifBlank(driverId,"请选择驾驶员");
		ClCl car = entityMapper.selectByPrimaryKey(carId);
		RuntimeCheck.ifNull(car,"未找到车辆");
		ClJsy driver = jsyService.findById(driverId);
		RuntimeCheck.ifNull(driver,"未找到驾驶员");

		car.setSjxm(driver.getXm());
		car.setSjId(driverId);
		entityMapper.updateByPrimaryKeySelective(car);
		return ApiResponse.success();
	}

	@Override
	public ApiResponse<String> bindDevice(String carId, String devcieId) {
		RuntimeCheck.ifBlank(carId,"请选择车辆");
		RuntimeCheck.ifBlank(devcieId,"请选择终端");
		ClCl car = entityMapper.selectByPrimaryKey(carId);
		RuntimeCheck.ifNull(car,"未找到车辆");
		ClZdgl device = zdglService.findById(devcieId);
		RuntimeCheck.ifNull(device,"未找到终端");

		car.setZdbh(devcieId);
		entityMapper.updateByPrimaryKeySelective(car);
		return ApiResponse.success();
	}

	public static int differentDaysByMillisecond(Date date1,Date date2)
	    {
	        int days = (int) ((date2.getTime() - date1.getTime()) / (1000*3600*24));
	        return days;
	    }

}
