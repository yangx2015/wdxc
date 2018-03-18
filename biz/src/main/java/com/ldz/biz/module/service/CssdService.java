package com.ldz.biz.module.service;

import com.ldz.util.bean.ApiResponse;
import com.ldz.sys.base.BaseService;
import com.ldz.biz.module.model.ClCssd;

import java.util.List;

public interface CssdService extends BaseService<ClCssd,String>{
    ApiResponse<String> saveEntity(ClCssd entity);

    ApiResponse<String> updateEntity(ClCssd entity);

    ApiResponse<List<ClCssd>> getByCx(String cx);
}
