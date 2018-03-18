package com.ldz.biz.module.service;

import com.ldz.biz.module.model.ClXl;
import com.ldz.sys.base.BaseService;
import com.ldz.util.bean.ApiResponse;

import java.util.List;

public interface XlService extends BaseService<ClXl,String>{
    ApiResponse<String> saveEntity(ClXl entity);

    List<ClXl> getByZdId(String zdId);
}
