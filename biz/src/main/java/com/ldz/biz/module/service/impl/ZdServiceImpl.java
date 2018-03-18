package com.ldz.biz.module.service.impl;

import com.ldz.util.bean.ApiResponse;
import com.ldz.util.bean.SimpleCondition;
import com.ldz.sys.base.BaseServiceImpl;
import com.ldz.biz.module.mapper.ClZdMapper;
import com.ldz.biz.module.model.ClXlzd;
import com.ldz.biz.module.model.ClZd;
import com.ldz.biz.module.service.XlzdService;
import com.ldz.biz.module.service.ZdService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.common.Mapper;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class ZdServiceImpl extends BaseServiceImpl<ClZd,String> implements ZdService{
    @Autowired
    private ClZdMapper entityMapper;
    @Autowired
    private XlzdService xlzdService;

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
}
