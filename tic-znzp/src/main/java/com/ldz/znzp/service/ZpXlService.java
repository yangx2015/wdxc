package com.ldz.znzp.service;

import com.ldz.util.bean.ApiResponse;
import com.ldz.znzp.base.BaseService;
import com.ldz.znzp.model.ClZpXl;

public interface ZpXlService extends BaseService<ClZpXl,String>{
    ApiResponse<String> saveEntity(ClZpXl entity);
}
