package com.ldz.biz.module.service;

import com.ldz.biz.module.bean.gpsSJInfo;
import com.ldz.biz.module.model.ClGpsLs;
import com.ldz.sys.base.BaseService;
import com.ldz.util.bean.ApiResponse;

import java.util.List;

public interface GpsLsService extends BaseService<ClGpsLs,String>{
    ApiResponse<String> saveEntity(ClGpsLs entity);

    List<ClGpsLs> getGpsLs(gpsSJInfo gpsSJInfo);

}
