package com.ldz.wechat.service;

import com.ldz.sys.base.BaseService;
import com.ldz.util.bean.ApiResponse;
import com.ldz.wechat.model.ClXlzd;

public interface XlzdService extends BaseService<ClXlzd,String>{
    ApiResponse<String> saveEntity(ClXlzd entity);
    ApiResponse<String> updateEntity(ClXlzd entity);
}
