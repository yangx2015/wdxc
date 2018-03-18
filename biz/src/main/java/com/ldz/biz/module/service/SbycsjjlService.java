package com.ldz.biz.module.service;

import com.ldz.util.bean.ApiResponse;
import com.ldz.sys.base.BaseService;
import com.ldz.biz.module.model.ClSbycsjjl;

public interface SbycsjjlService extends BaseService<ClSbycsjjl,String>{
    ApiResponse<String> saveEntity(ClSbycsjjl entity);
}
