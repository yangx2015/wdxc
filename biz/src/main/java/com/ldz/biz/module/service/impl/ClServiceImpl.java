package com.ldz.biz.module.service.impl;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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
