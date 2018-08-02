package com.ldz.biz.module.service;


import com.ldz.biz.module.model.ClXc;
import com.ldz.sys.base.BaseService;
import com.ldz.util.bean.ApiResponse;

import java.util.List;
import java.util.Map;

public interface XcService extends BaseService<ClXc,String> {


    ApiResponse<String> saveEntity(ClXc clXc);

    ApiResponse<List<Map<String, Object>>> history(String zdbh, String startTime, String endTime);
}
