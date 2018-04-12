package com.ldz.wechat.service;

import com.ldz.sys.base.BaseService;
import com.ldz.util.bean.ApiResponse;
import com.ldz.wechat.model.ClZpXl;

public interface ZpXlService extends BaseService<ClZpXl,String>{
    ApiResponse<String> saveEntity(ClZpXl entity);
}
