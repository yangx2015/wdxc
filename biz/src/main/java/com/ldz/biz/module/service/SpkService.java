package com.ldz.biz.module.service;

import com.ldz.biz.module.bean.GpsInfo;
import com.ldz.biz.module.model.ClSpk;
import com.ldz.sys.base.BaseService;
import com.ldz.util.bean.ApiResponse;

public interface SpkService extends BaseService<ClSpk,String>{

    ApiResponse<String> saveEntity(ClSpk entity);

    ApiResponse<String> updateEntity(ClSpk entity);

    /*
     * 对tic-server提供上传云视屏库接口
     *
     *
     */
    ApiResponse<String> saveSpk(GpsInfo entity);
}
