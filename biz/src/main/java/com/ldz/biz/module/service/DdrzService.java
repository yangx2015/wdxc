package com.ldz.biz.module.service;

import com.ldz.util.bean.ApiResponse;
import com.ldz.sys.base.BaseService;
import com.ldz.biz.module.model.ClDdrz;

public interface DdrzService extends BaseService<ClDdrz,String>{
    ApiResponse<String> saveEntity(ClDdrz entity);
}
