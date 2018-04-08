package com.ldz.biz.module.service;

import com.ldz.biz.module.model.ClXlzd;
import com.ldz.sys.base.BaseService;
import com.ldz.util.bean.ApiResponse;

public interface XlzdService extends BaseService<ClXlzd,String>{
    ApiResponse<String> saveEntity(ClXlzd entity);
    ApiResponse<String> updateEntity(ClXlzd entity);
}
