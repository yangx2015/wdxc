package com.ldz.biz.module.service;

import java.util.List;

import com.ldz.biz.module.bean.DdClModel;
import com.ldz.biz.module.model.ClZd;
import com.ldz.sys.base.BaseService;
import com.ldz.util.bean.ApiResponse;

public interface ZdService extends BaseService<ClZd,String>{
    ApiResponse<String> saveEntity(ClZd entity);
    ApiResponse<String> updateEntity(ClZd entity);
    List<ClZd> getByXlId(String xlId);
    List<ClZd> getByXlIds(List<String> xlIds);

    void setStationOrder(ClZd station);

    /**
     * 获取线路下站点和车辆的信息
     * @param xlId  线路ID
     * @return
     */
    ApiResponse<List<DdClModel>> getBySiteVehicleList(String xlId);
}
