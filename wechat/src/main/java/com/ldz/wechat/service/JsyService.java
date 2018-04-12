package com.ldz.wechat.service;

import com.ldz.sys.base.BaseService;
import com.ldz.util.bean.ApiResponse;
import com.ldz.wechat.model.ClJsy;

public interface JsyService extends BaseService<ClJsy,String>{
    ApiResponse<String> saveEntity(ClJsy entity);
    ApiResponse<String> updateEntity(ClJsy entity);
}
