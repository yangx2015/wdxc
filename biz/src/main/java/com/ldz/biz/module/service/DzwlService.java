package com.ldz.biz.module.service;

import com.ldz.util.bean.ApiResponse;
import com.ldz.sys.base.BaseService;
import com.ldz.biz.module.model.ClDzwl;

import java.util.List;

public interface DzwlService extends BaseService<ClDzwl,String>{
    ApiResponse<String> saveEntity(ClDzwl entity);
    ApiResponse<String> updateEntity(ClDzwl entity);

    /**
     * 设置车辆电子围栏
     * @param clId
     * @param wlIds
     * @return
     */
    ApiResponse<String> setCarDzwl(String clId, List<String> wlIds);

    ApiResponse<String> setCarsDzwl(String carIds, String wlid);
}
