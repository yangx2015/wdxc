package com.ldz.biz.module.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ldz.biz.module.mapper.ClXlMapper;
import com.ldz.biz.module.mapper.ClXlzdMapper;
import com.ldz.biz.module.model.ClXl;
import com.ldz.biz.module.model.ClXlzd;
import com.ldz.biz.module.service.XlService;
import com.ldz.biz.module.service.XlzdService;
import com.ldz.sys.base.BaseServiceImpl;
import com.ldz.sys.exception.RuntimeCheck;
import com.ldz.sys.model.SysJg;
import com.ldz.sys.model.SysYh;
import com.ldz.sys.service.JgService;
import com.ldz.util.bean.ApiResponse;
import com.ldz.util.bean.SimpleCondition;
import com.ldz.util.gps.BaiduWebApi;
import com.ldz.util.gps.bean.RouteMatrixBean;
import com.ldz.util.gps.bean.RouteMatrixResult;

import tk.mybatis.mapper.common.Mapper;

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
		return ApiResponse.saveSuccess();
	}

	private void saveRouterStations(ClXl router, List<String> stationIds) {
		SimpleCondition condition = new SimpleCondition(ClXlzd.class);
		condition.eq(ClXlzd.InnerColumn.xlId, router.getId());
		xlzdMapper.deleteByExample(condition);
		
         //获取站点信息
		
		
		/*
		String operator = getOperateUser();
		Date now = new Date();
		int i = 0;
		List<ClXlzd> xlzds = new ArrayList<>(stationIds.size());
		for (String stationId : stationIds) {
			
			ClXlzd xlzd = new ClXlzd();
			
			xlzd.setCjr(operator);
			xlzd.setCjsj(now);
			xlzd.setFx(router.getYxfs());
			xlzd.setXh(new Short("" + (++i)));
			xlzd.setXlId(router.getId());
			xlzd.setZdId(stationId);
			xlzd.setId(genId());
			// xlzd.setZt(Dict.CommonStatus.VALID.getCode());
			xlzd.setZt("00");
			xlzds.add(xlzd);
		}*/
		
		/*xlzdMapper.insertList(xlzds);*/
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

	public static void main(String[] args) {
         
		List<String> qidian = new ArrayList<>();
        String q="30.5307693887,114.3244618270";
        qidian.add(q);
		List<String> zhongdian = new ArrayList<>();
        String z="30.5582816319,114.2131314340";
        zhongdian.add(z);
		RouteMatrixBean routematrix = BaiduWebApi.routematrix(qidian, zhongdian, "driving");
		System.out.println(routematrix);
		List<RouteMatrixResult> result = routematrix.getResult();
		System.out.println(result);
		for (RouteMatrixResult routeMatrixResult : result) {
			Map<String, String> duration = routeMatrixResult.getDuration();
			for (Object o : duration.keySet()) {
				System.out.println("key=" + o + " value=" + duration.get(o));
			}
		}

	}
}
