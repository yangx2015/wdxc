package com.ldz.wechat.service;

import com.ldz.sys.base.BaseService;
import com.ldz.util.bean.ApiResponse;
import com.ldz.wechat.bean.gpsSJInfo;
import com.ldz.wechat.model.ClGpsLs;

public interface GpsLsService extends BaseService<ClGpsLs,String>{
    ApiResponse<String> saveEntity(ClGpsLs entity);
    
    ApiResponse<String[]> getZdbhAllLsGps(gpsSJInfo info);
}
