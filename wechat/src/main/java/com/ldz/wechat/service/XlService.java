package com.ldz.wechat.service;

import java.util.List;

import com.ldz.sys.base.BaseService;
import com.ldz.util.bean.ApiResponse;
import com.ldz.wechat.model.ClXl;

public interface XlService extends BaseService<ClXl,String>{
    ApiResponse<String> saveEntity(ClXl entity);
    ApiResponse<String> updateEntity(ClXl entity);
    List<ClXl> getByZdId(String zdId);
}
