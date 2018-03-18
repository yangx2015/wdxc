package com.ldz.biz.module.service;

import com.ldz.util.bean.ApiResponse;
import com.ldz.sys.base.BaseService;
import com.ldz.biz.module.model.ClDd;

public interface DdService extends BaseService<ClDd,String>{
    ApiResponse<String> saveEntity(ClDd entity);
}
