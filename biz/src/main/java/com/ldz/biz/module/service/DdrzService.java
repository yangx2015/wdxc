package com.ldz.biz.module.service;

import com.ldz.biz.module.model.ClDdrz;
import com.ldz.sys.base.BaseService;
import com.ldz.util.bean.ApiResponse;

import java.util.List;

public interface DdrzService extends BaseService<ClDdrz,String>{
    ApiResponse<String> saveEntity(ClDdrz entity);

    List<ClDdrz> getOrderList(String orderId);
}
