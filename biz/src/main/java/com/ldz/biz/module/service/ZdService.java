package com.ldz.biz.module.service;

import com.ldz.util.bean.ApiResponse;
import com.ldz.sys.base.BaseService;
import com.ldz.biz.module.model.ClZd;

import java.util.List;

public interface ZdService extends BaseService<ClZd,String>{
    ApiResponse<String> saveEntity(ClZd entity);

    List<ClZd> getByXlId(String xlId);
    List<ClZd> getByXlIds(List<String> xlIds);

    void setStationOrder(ClZd station);
}
