package com.ldz.wechat.service;

import com.ldz.sys.base.BaseService;
import com.ldz.util.bean.ApiResponse;
import com.ldz.wechat.model.ClSgwj;

public interface SgwjService extends BaseService<ClSgwj,String>{
    ApiResponse<String> saveEntity(ClSgwj entity);
}
