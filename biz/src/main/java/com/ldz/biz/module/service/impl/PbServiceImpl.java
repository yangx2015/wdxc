package com.ldz.biz.module.service.impl;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ldz.biz.module.bean.PbClXlmodel;
import com.ldz.biz.module.bean.PbInfo;
import com.ldz.biz.module.bean.XbXlPb;
import com.ldz.biz.module.mapper.ClClMapper;
import com.ldz.biz.module.mapper.ClPbMapper;
import com.ldz.biz.module.mapper.PbInfoMapper;
import com.ldz.biz.module.model.ClCl;
import com.ldz.biz.module.model.ClPb;
import com.ldz.biz.module.service.PbService;
import com.ldz.sys.base.BaseServiceImpl;
import com.ldz.sys.exception.RuntimeCheck;
import com.ldz.sys.model.SysJg;
import com.ldz.sys.model.SysYh;
import com.ldz.sys.service.JgService;
import com.ldz.util.bean.ApiResponse;

import tk.mybatis.mapper.common.Mapper;

@Service
public class PbServiceImpl extends BaseServiceImpl<ClPb, String> implements PbService {
	@Autowired
	private ClPbMapper entityMapper;
	@Autowired
	private JgService jgService;
	@Autowired
	private PbInfoMapper pbinfomapper;
	@Autowired
	private ClClMapper clclmapper;

	@Override
	protected Mapper<ClPb> getBaseMapper() {
		return entityMapper;
	}

	@Override
	protected Class<?> getEntityCls() {
		return ClPb.class;
	}

	@Override
	public ApiResponse<String> saveEntity(ClPb entity) {
		SysYh user = getCurrentUser();
		SysJg org = jgService.findByOrgCode(user.getJgdm());
		Date now = new Date();
		entity.setCjr(getOperateUser());
		entity.setClId(genId());
		entity.setCjsj(now);
		entity.setJgdm(user.getJgdm());
		entity.setJgmc(org.getJgmc());
		save(entity);
		return ApiResponse.saveSuccess();
	}

	@Override
	public ApiResponse<String> updateEntity(ClPb entity) {
		ClPb findById = findById(entity.getId());
		RuntimeCheck.ifNull(findById, "未找到记录");
		entity.setXgr(getOperateUser());
		entity.setXgj(new Date());
		update(entity);
		return ApiResponse.success();
	}

	@Override
	public ApiResponse<List<PbInfo>> getPbInfo(PbClXlmodel pbclxlmodel) {
		
		ApiResponse<List<PbInfo>> pbinflist = new ApiResponse<>();
		// 获取该日车辆已排班信息
		List<PbInfo> selectBydate = pbinfomapper.selectBydate(pbclxlmodel);

		pbinflist.setResult(selectBydate);

		return pbinflist;
	}

	@Override
	public ApiResponse<List<XbXlPb>> getAllPbInfo(PbClXlmodel pbclxlmodel) {

		ApiResponse<List<XbXlPb>> pbinflist = new ApiResponse<>();
		// 获取指定线路下面的排班信息
		List<XbXlPb> selectXbPb = pbinfomapper.selectXbPb(pbclxlmodel);

		for (XbXlPb xbXlPb : selectXbPb) {

			if (StringUtils.isNotEmpty(xbXlPb.getClidlist())) {
				// 获取车辆id集合
				List<String> clidlist = Arrays.asList(xbXlPb.getClidlist().split(","));
				List<ClCl> allClInfo = clclmapper.getAllClInfo(clidlist);
				if (StringUtils.isNotEmpty(pbclxlmodel.getClcx())) {
					// 过滤指定车型的车辆信息
					List<ClCl> collect = allClInfo.stream().filter(s -> s.getCx().equals(pbclxlmodel.getClcx()))
							.collect(Collectors.toList());
					xbXlPb.setClList(collect);
				}else {
					xbXlPb.setClList(allClInfo);
				}
				
			}

		}

		pbinflist.setResult(selectXbPb);

		return pbinflist;
	}
}
