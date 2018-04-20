package com.ldz.biz.module.service;

import com.ldz.biz.module.model.ClGpsLs;
import com.ldz.sys.base.BaseService;
import com.ldz.util.bean.ApiResponse;

public interface GpsLsService extends BaseService<ClGpsLs,String>{
    ApiResponse<String> saveEntity(ClGpsLs entity);
    
}
