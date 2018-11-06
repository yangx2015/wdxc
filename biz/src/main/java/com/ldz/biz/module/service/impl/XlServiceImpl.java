package com.ldz.biz.module.service.impl;

import com.ldz.biz.module.mapper.ClXlMapper;
import com.ldz.biz.module.mapper.ClXlzdMapper;
import com.ldz.biz.module.mapper.ClZdMapper;
import com.ldz.biz.module.model.ClXl;
import com.ldz.biz.module.model.ClXlzd;
import com.ldz.biz.module.model.ClZd;
import com.ldz.biz.module.service.XlService;
import com.ldz.biz.module.service.XlzdService;
import com.ldz.sys.base.BaseServiceImpl;
import com.ldz.sys.model.SysJg;
import com.ldz.sys.model.SysYh;
import com.ldz.sys.service.JgService;
import com.ldz.sys.util.RedisUtil;
import com.ldz.util.bean.ApiResponse;
import com.ldz.util.bean.SimpleCondition;
import com.ldz.util.exception.RuntimeCheck;
import com.ldz.util.gps.BaiduWebApi;
import com.ldz.util.gps.bean.RouteMatrixBean;
import com.ldz.util.gps.bean.RouteMatrixResult;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.common.Mapper;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class XlServiceImpl extends BaseServiceImpl<ClXl, String> implements XlService {
	@Autowired
	private ClXlMapper entityMapper;
	@Autowired
	private JgService jgService;
	@Autowired
	private XlzdService xlzdService;
	@Autowired
	private XlService xlService;
	@Autowired
	private ClXlzdMapper xlzdMapper;
	@Autowired
	private ClZdMapper clzdMapper;
	@Value("${znzpurl}")
	private String deleteZnzpRedisKeyUrl;
	@Autowired
	private RedisUtil redisUtil;

	@Autowired
	private StringRedisTemplate redisDao;



	@Override
	protected Mapper<ClXl> getBaseMapper() {
		return entityMapper;
	}

	@Override
	protected Class<?> getEntityCls() {
		return ClXl.class;
	}

	@Override
	public ApiResponse<String> saveEntity(ClXl entity) {
		SysYh user = getCurrentUser();
		SysJg org = jgService.findByOrgCode(user.getJgdm());
		Date now = new Date();
		entity.setCjr(getOperateUser());
		entity.setCjsj(now);
		entity.setJgdm(user.getJgdm());
		entity.setJgmc(org.getJgmc());
		entity.setId(genId());
		save(entity);
		if (StringUtils.isNotBlank(entity.getZdIds())) {
			List<String> stationIds = Arrays.asList(entity.getZdIds().split(","));
			saveRouterStations(entity, stationIds);
		}
		redisUtil.deleteRedisKey(deleteZnzpRedisKeyUrl + "/deleteRedisKey","com.ldz.znzp.mapper.ClXlMapper");
		return ApiResponse.saveSuccess();
	}

	private void saveRouterStations(ClXl router, List<String> stationIds) {
		SimpleCondition condition = new SimpleCondition(ClXlzd.class);
		condition.eq(ClXlzd.InnerColumn.xlId, router.getId());
		xlzdMapper.deleteByExample(condition);



		// 获取站点信息
		List<ClZd> allClzd = clzdMapper.getAllClzd(stationIds);

		String operator = getOperateUser();
		Date now = new Date();
		if(allClzd!=null&&allClzd.size()>0){
			Map<String,ClZd> zdMap = allClzd.stream().collect(Collectors.toMap(ClZd::getId,p->p));
			List<ClXlzd> xlzds = new ArrayList<>(allClzd.size());
			int m =1;
			String lastZdId="";
			for(String zdId:stationIds){
				if(zdMap.get(zdId)!=null){
					Short yjdzsj=null;
					if(m>1){
						try {
							List<String> qidian = Arrays.asList(zdMap.get(zdId).getWd() + "," + zdMap.get(zdId).getJd());
							List<String> zhongdian = Arrays.asList(zdMap.get(lastZdId).getWd() + "," + zdMap.get(lastZdId).getJd());
							String baiDuTime = getBaiDuTime(qidian, zhongdian);
							yjdzsj=getMinutes(baiDuTime);
						}catch (Exception e){
							e.printStackTrace();
						}
					}
					ClXlzd xlzd = new ClXlzd();
					xlzd.setCjr(operator);
					xlzd.setCjsj(now);
					xlzd.setFx(router.getYxfs());
					xlzd.setXh((short) m);
					xlzd.setXlId(router.getId());
					xlzd.setZdId(zdMap.get(zdId).getId());
					xlzd.setId(genId());
					xlzd.setZt("00");
					xlzd.setYjdzsj(yjdzsj);
					xlzds.add(xlzd);
					m++;
					lastZdId=zdId;
				}
			}
			xlzdMapper.insertList(xlzds);
		}

	}

	@Override
	public ApiResponse<String> updateEntity(ClXl entity) {
		ClXl findById = findById(entity.getId());
		RuntimeCheck.ifNull(findById, "未找到记录");
		entity.setXgr(getOperateUser());
		entity.setXgsj(new Date());
		update(entity);

		if (StringUtils.isNotBlank(entity.getZdIds())) {
			List<String> stationIds = Arrays.asList(entity.getZdIds().split(","));
			saveRouterStations(entity, stationIds);
		}

		try {
//			- ZNZP_XL_{{xlId}}           BIZ 修改、删除线路时后，需要清除     ``
//			- ZNZP_XLZD_{{xlId}}
			redisDao.delete("ZNZP_XL_"+entity.getId());
			redisDao.delete("ZNZP_XLZD_"+entity.getId());
		}catch (Exception e){e.printStackTrace();}
		redisUtil.deleteRedisKey(deleteZnzpRedisKeyUrl + "/deleteRedisKey","com.ldz.znzp.mapper.ClXlMapper");
		return ApiResponse.success();
	}

	@Override
	public List<ClXl> getByZdId(String zdId) {
		List<ClXlzd> xlzds = xlzdService.findEq(ClXlzd.InnerColumn.zdId, zdId);
		if (xlzds.size() == 0)
			return new ArrayList<>();
		List<String> xlIds = xlzds.stream().map(ClXlzd::getXlId).collect(Collectors.toList());
		return xlService.findIn(ClXl.InnerColumn.id, xlIds);
	}

	private short getMinutes(String s){
		int index = s.length() - 2;
		String unit = s.substring(index);
		String num = s.substring(0,index);
		switch(unit){
			case "分钟":
				return new Short(num);
			case "小时":
				return new Short(""+Integer.parseInt(num) * 60);
			default:
				return 0;
		}
	}

	// 获取百度经纬度之间驾车时间
	public String getBaiDuTime(List<String> qidian, List<String> zd) {
		String time = null;
		RouteMatrixBean routematrix = BaiduWebApi.routematrix(qidian, zd, "driving");

		List<RouteMatrixResult> result = routematrix.getResult();
		for (RouteMatrixResult routeMatrixResult : result) {
			Map<String, String> duration = routeMatrixResult.getDuration();
			time = duration.get("text");
		}

		return time;

	}

	/*
	 * public static void main(String[] args) {
	 *
	 * List<String> qidian = new ArrayList<>(); String q =
	 * "30.5307693887,114.3244618270"; qidian.add(q); List<String> zhongdian = new
	 * ArrayList<>(); String z = "30.5582816319,114.2131314340"; zhongdian.add(z);
	 * RouteMatrixBean routematrix = BaiduWebApi.routematrix(qidian, zhongdian,
	 * "driving"); List<RouteMatrixResult> result = routematrix.getResult(); for
	 * (RouteMatrixResult routeMatrixResult : result) { Map<String, String> duration
	 * = routeMatrixResult.getDuration(); System.out.println(duration.get("text"));
	 * }
	 *
	 * }
	 */

	public static void main(String[] args) {
        int m =1;
		List<String> strings = new ArrayList<>();

		strings.add("香蕉");
		strings.add("苹果");
		strings.add("橘子");
		strings.add("火龙果");

		List<String> dsd = new ArrayList<>();
		for (int i = 0; i < strings.size(); i++) {
			if (i != 0) {
				++m;
				dsd.add(strings.get(i)+"第"+m+"次");
			} else {
				dsd.add(strings.get(i)+"第一次");
			}
		}

		System.out.println(dsd);
	}
}
