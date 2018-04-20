package com.ldz.biz.module.service.impl;

import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;

import com.ldz.biz.module.bean.GpsInfo;
import com.ldz.biz.module.mapper.ClClMapper;
import com.ldz.biz.module.mapper.ClCssdMapper;
import com.ldz.biz.module.model.ClCl;
import com.ldz.biz.module.model.ClCssd;
import com.ldz.biz.module.service.CssdService;
import com.ldz.sys.base.BaseServiceImpl;
import com.ldz.sys.base.LimitedCondition;
import com.ldz.sys.exception.RuntimeCheck;
import com.ldz.sys.model.SysJg;
import com.ldz.sys.model.SysYh;
import com.ldz.sys.service.JgService;
import com.ldz.util.bean.ApiResponse;
import com.ldz.util.commonUtil.HttpUtil;
import com.ldz.util.commonUtil.JsonUtil;

import tk.mybatis.mapper.common.Mapper;

@Service
public class CssdServiceImpl extends BaseServiceImpl<ClCssd, String> implements CssdService {
	@Autowired
	private ClCssdMapper entityMapper;
	@Autowired
	private JgService jgService;
	@Autowired
	private ClClMapper clclmapper;

	@Override
	protected Mapper<ClCssd> getBaseMapper() {
		return entityMapper;
	}

	@Override
	protected Class<?> getEntityCls() {
		return ClCssd.class;
	}

	@Override
	public ApiResponse<String> saveEntity(ClCssd entity) {
		SysYh user = getCurrentUser();
		SysJg org = jgService.findByOrgCode(user.getJgdm());
		Date now = new Date();
		entity.setCjr(getOperateUser());
		entity.setCjsj(now);
		entity.setId(genId());
		entity.setJgdm(user.getJgdm());
		entity.setJgmc(org.getJgmc());
		save(entity);
		return ApiResponse.saveSuccess();
	}

	@Override
	public ApiResponse<String> updateEntity(ClCssd entity) {
		ClCssd oldRecord = findById(entity.getId());
		RuntimeCheck.ifNull(oldRecord, "未找到记录");
		entity.setXgr(getOperateUser());
		entity.setXgsj(new Date());
		update(entity);
		return ApiResponse.success();
	}

	@Override
	public ApiResponse<List<ClCssd>> getByCx(String cx) {
		// 自带机构筛选条件
		LimitedCondition condition = new LimitedCondition(ClCssd.class);
		condition.eq(ClCssd.InnerColumn.cx, cx);
		List<ClCssd> cssds = findByCondition(condition);
		return ApiResponse.success(cssds);
	}

	@Override
	public ApiResponse<String> setCssds(String cphs, String csz) {
		SysYh user = getCurrentUser();
		SysJg org = jgService.findByOrgCode(user.getJgdm());
		// 将车牌号,车辆信息缓存
		List<ClCl> selectAll = clclmapper.selectAll();
		Map<String, ClCl> clmap = selectAll.stream().collect(Collectors.toMap(ClCl::getCph, ClCl -> ClCl));

		List<String> cphss=Arrays.asList(cphs.split(","));
		
		for (String cph : cphss) {
			GpsInfo gpsInfo = new GpsInfo();
			ClCssd cssd = new ClCssd();

			ClCl clCl = clmap.get(cph);
			gpsInfo.setDeviceId(clCl.getZdbh());
			gpsInfo.setCmdType("01");
			gpsInfo.setCmd(csz);
			senZl(gpsInfo);

			cssd.setCjr(getOperateUser());
			cssd.setCjsj(new Date());
			cssd.setCph(cph);
			cssd.setId(genId());
			cssd.setJgdm(user.getJgdm());
			cssd.setJgmc(org.getJgmc());
			cssd.setSdsx(Short.valueOf(csz));
			insetAndUpdate(cssd);

		}
		return ApiResponse.success();
	}

	@Override
	public void insetAndUpdate(ClCssd entity) {

		boolean flag = ifExists("cph", entity.getCph());
		if (flag == true) {
			update(entity);
		} else {
			save(entity);
		}

	}

	@SuppressWarnings("unchecked")
	@Override
	public ApiResponse<String> senZl(GpsInfo info) {
		String url = "http://47.98.39.45:8080/tic-server/api/push/carcmd";
		String postEntity = JsonUtil.toJson(info);
		String result = "";
		ApiResponse<String> apiResponse = null;
		try {
			Map<String, String> postHeaders = new HashMap<String, String>();
			postHeaders.put("Content-Type", MediaType.APPLICATION_JSON_VALUE);
			result = HttpUtil.postJson(url, postHeaders, postEntity);
			apiResponse = (ApiResponse<String>) JsonUtil.toBean(result, ApiResponse.class);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return apiResponse;
	}

}
