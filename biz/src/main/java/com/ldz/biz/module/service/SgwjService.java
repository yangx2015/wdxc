package com.ldz.biz.module.service;

import com.ldz.util.bean.ApiResponse;
import com.ldz.sys.base.BaseService;
import com.ldz.biz.module.model.ClSgwj;

public interface SgwjService extends BaseService<ClSgwj,String>{
    ApiResponse<String> saveEntity(ClSgwj entity);
}
