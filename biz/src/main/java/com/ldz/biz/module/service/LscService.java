package com.ldz.biz.module.service;

import com.ldz.util.bean.ApiResponse;
import com.ldz.sys.base.BaseService;
import com.ldz.biz.module.model.ClLsc;

public interface LscService extends BaseService<ClLsc,String>{
    ApiResponse<String> saveEntity(ClLsc entity);
}
