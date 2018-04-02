package com.ldz.biz.module.service;

import com.ldz.util.bean.ApiResponse;
import com.ldz.sys.base.BaseService;

import java.util.List;

import com.ldz.biz.module.bean.gpsSJInfo;
import com.ldz.biz.module.model.ClSbyxsjjl;

public interface SbyxsjjlService extends BaseService<ClSbyxsjjl,String>{
    ApiResponse<String> saveEntity(ClSbyxsjjl entity);
    ApiResponse<List<ClSbyxsjjl>> historyTrajectory(gpsSJInfo gpssjinfo);
}
