package com.ldz.wechat.service;

import com.ldz.sys.base.BaseService;
import com.ldz.util.bean.ApiResponse;
import com.ldz.wechat.model.ClSbycsjjl;

public interface SbycsjjlService extends BaseService<ClSbycsjjl,String>{
    ApiResponse<String> saveEntity(ClSbycsjjl entity);
}
