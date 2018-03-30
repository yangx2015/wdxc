package com.ldz.biz.module.service.impl;

import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ldz.sys.exception.RuntimeCheck;
import com.ldz.biz.module.mapper.ClZdMapper;
import com.ldz.biz.module.model.ClXlzd;
import com.ldz.biz.module.model.ClZd;
import com.ldz.biz.module.service.XlzdService;
import com.ldz.biz.module.service.ZdService;
import com.ldz.sys.base.BaseServiceImpl;
import com.ldz.sys.model.SysJg;
import com.ldz.sys.model.SysYh;
import com.ldz.sys.service.JgService;
import com.ldz.util.bean.ApiResponse;
import com.ldz.util.bean.SimpleCondition;

import tk.mybatis.mapper.common.Mapper;

@Service
public class ZdServiceImpl extends BaseServiceImpl<ClZd,String> implements ZdService{
    @Autowired
    private ClZdMapper entityMapper;
    @Autowired
    private XlzdService xlzdService;
    @Autowired
    private JgService jgService;
    @Override
    protected Mapper<ClZd> getBaseMapper() {
        return entityMapper;
    }

    @Override
    protected Class<?> getEntityCls(){
        return ClZd.class;
    }

    @Override
    public ApiResponse<String> saveEntity(ClZd entity) {
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
    public List<ClZd> getByXlId(String xlId) {
        return getByXlIds(Collections.singletonList(xlId));
    }

    @Override
    public List<ClZd> getByXlIds(List<String> xlIds) {
        List<ClXlzd> xlzds = xlzdService.findIn(ClXlzd.InnerColumn.xlId,xlIds);
        List<String> routeIds = xlzds.stream().map(ClXlzd::getZdId).collect(Collectors.toList());
        List<ClZd> stations = findIn(ClZd.InnerColumn.id,routeIds);
        Map<String,String> zdXlIdsMap = xlzds.stream().collect(Collectors.toMap(ClXlzd::getZdId,ClXlzd::getXlId));
        for (ClZd station : stations) {
            station.setXlId(zdXlIdsMap.get(station.getId()));
        }
        return findIn(ClZd.InnerColumn.id,routeIds);
    }

    @Override
    public void setStationOrder(ClZd station) {
        if (StringUtils.isEmpty(station.getId()) || StringUtils.isEmpty(station.getXlId())){
            return;
        }
        SimpleCondition condition = new SimpleCondition(ClXlzd.class);
        condition.eq(ClXlzd.InnerColumn.zdId,station.getId());
        condition.eq(ClXlzd.InnerColumn.xlId,station.getXlId());
        List<ClXlzd> xlzds = xlzdService.findByCondition(condition);
        if (xlzds.size() == 0)return;
        station.setRouteOrder(xlzds.get(0).getXh());
    }

	@Override
	public ApiResponse<String> updateEntity(ClZd entity) {
		    ClZd findById = findById(entity.getId());
	        RuntimeCheck.ifNull(findById,"未找到记录");
	        entity.setXgr(getOperateUser());
	        entity.setXgsj(new Date());
	        update(entity);
		return ApiResponse.success();
	}
}
