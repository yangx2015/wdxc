package com.ldz.biz.module.service;

import com.ldz.util.bean.ApiResponse;
import com.ldz.sys.base.BaseService;
import com.ldz.biz.module.model.ClXlcl;

public interface XlclService extends BaseService<ClXlcl,String>{
    ApiResponse<String> saveEntity(ClXlcl entity);
}
