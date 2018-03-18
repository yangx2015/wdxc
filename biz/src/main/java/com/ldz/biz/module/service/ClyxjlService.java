package com.ldz.biz.module.service;

import com.ldz.util.bean.ApiResponse;
import com.ldz.sys.base.BaseService;
import com.ldz.biz.module.model.ClClyxjl;

public interface ClyxjlService extends BaseService<ClClyxjl,String>{
    ApiResponse<String> saveEntity(ClClyxjl entity);
}
