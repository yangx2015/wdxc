package com.ldz.biz.module.service;

import com.ldz.util.bean.ApiResponse;
import com.ldz.sys.base.BaseService;
import com.ldz.biz.module.model.ClJsy;

public interface JsyService extends BaseService<ClJsy,String>{
    ApiResponse<String> saveEntity(ClJsy entity);
}
