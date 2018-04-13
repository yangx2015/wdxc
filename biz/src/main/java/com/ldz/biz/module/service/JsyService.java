package com.ldz.biz.module.service;

import com.ldz.biz.module.model.ClJsy;
import com.ldz.sys.base.BaseService;
import com.ldz.sys.model.SysYh;
import com.ldz.util.bean.ApiResponse;

import java.util.List;

public interface JsyService extends BaseService<ClJsy,String>{
    ApiResponse<String> saveEntity(ClJsy entity);
    ApiResponse<String> updateEntity(ClJsy entity);

    ApiResponse<List<ClJsy>> notBindList(SysYh user);
}
