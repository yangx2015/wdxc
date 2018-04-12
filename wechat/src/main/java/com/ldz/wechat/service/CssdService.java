package com.ldz.wechat.service;

import java.util.List;

import com.ldz.sys.base.BaseService;
import com.ldz.util.bean.ApiResponse;
import com.ldz.wechat.model.ClCssd;

public interface CssdService extends BaseService<ClCssd,String>{
    ApiResponse<String> saveEntity(ClCssd entity);

    ApiResponse<String> updateEntity(ClCssd entity);

    ApiResponse<List<ClCssd>> getByCx(String cx);
}
