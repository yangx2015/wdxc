package com.ldz.biz.module.service;


import com.ldz.biz.module.model.ClXc;
import com.ldz.sys.base.BaseService;
import com.ldz.util.bean.ApiResponse;

public interface XcService extends BaseService<ClXc,String> {


    ApiResponse<String> saveEntity(ClXc clXc);
}
