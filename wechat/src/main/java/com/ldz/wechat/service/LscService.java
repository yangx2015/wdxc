package com.ldz.wechat.service;

import com.ldz.sys.base.BaseService;
import com.ldz.util.bean.ApiResponse;
import com.ldz.wechat.model.ClLsc;

public interface LscService extends BaseService<ClLsc,String>{
    ApiResponse<String> saveEntity(ClLsc entity);
}
