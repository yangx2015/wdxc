package com.ldz.wechat.service;

import java.util.List;

import com.ldz.sys.base.BaseService;
import com.ldz.util.bean.ApiResponse;
import com.ldz.wechat.bean.gpsSJInfo;
import com.ldz.wechat.model.ClSbyxsjjl;

public interface SbyxsjjlService extends BaseService<ClSbyxsjjl,String>{
    ApiResponse<String> saveEntity(ClSbyxsjjl entity);
    ApiResponse<List<ClSbyxsjjl>> historyTrajectory(gpsSJInfo gpssjinfo);
}
