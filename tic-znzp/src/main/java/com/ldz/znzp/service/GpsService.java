package com.ldz.znzp.service;

import com.ldz.znzp.base.BaseService;
import com.ldz.util.bean.ApiResponse;
import com.ldz.znzp.model.ClGps;

public interface GpsService extends BaseService<ClGps,String>{
    ApiResponse<String> saveEntity(ClGps entity);
}
