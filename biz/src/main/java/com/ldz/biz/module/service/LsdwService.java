package com.ldz.biz.module.service;

import com.ldz.util.bean.ApiResponse;
import com.ldz.sys.base.BaseService;
import com.ldz.biz.module.model.ClLsdw;

public interface LsdwService extends BaseService<ClLsdw,String>{
    ApiResponse<String> saveEntity(ClLsdw entity);
}
