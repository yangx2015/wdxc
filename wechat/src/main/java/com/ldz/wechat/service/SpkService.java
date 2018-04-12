package com.ldz.wechat.service;

import com.ldz.sys.base.BaseService;
import com.ldz.util.bean.ApiResponse;
import com.ldz.wechat.bean.GpsInfo;
import com.ldz.wechat.model.ClSpk;

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
