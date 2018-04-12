package com.ldz.wechat.service;

import com.ldz.sys.base.BaseService;
import com.ldz.util.bean.ApiResponse;
import com.ldz.wechat.model.ClXlcl;

public interface XlclService extends BaseService<ClXlcl,String>{
    ApiResponse<String> saveEntity(ClXlcl entity);
}
