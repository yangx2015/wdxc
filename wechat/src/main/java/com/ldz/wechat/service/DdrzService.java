package com.ldz.wechat.service;

import java.util.List;

import com.ldz.sys.base.BaseService;
import com.ldz.util.bean.ApiResponse;
import com.ldz.wechat.model.ClDdrz;

public interface DdrzService extends BaseService<ClDdrz,String>{
    ApiResponse<String> saveEntity(ClDdrz entity);

    List<ClDdrz> getOrderList(String orderId);
}
