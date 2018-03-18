package com.ldz.biz.module.service;

import com.ldz.biz.module.model.ClCl;
import com.ldz.sys.base.BaseService;
import com.ldz.util.bean.ApiResponse;

public interface ClService extends BaseService<ClCl,String>{
    ApiResponse<String> saveEntity(ClCl entity);
}
