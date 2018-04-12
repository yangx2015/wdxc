package com.ldz.wechat.service;

import java.util.List;

import com.ldz.sys.base.BaseService;
import com.ldz.util.bean.ApiResponse;
import com.ldz.wechat.model.ClDzwl;

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
}
