package com.ldz.biz.module.service;

import com.ldz.biz.module.model.ClCl;
import com.ldz.sys.base.BaseService;
import com.ldz.util.bean.ApiResponse;

import java.util.List;

public interface ClService extends BaseService<ClCl,String>{
    ClCl findByOrgCode(String code);
    List<ClCl> getOrgCarList(String orgCode);
    ApiResponse<String> saveEntity(ClCl entity);
    ApiResponse<String> updateEntity(ClCl entity);
    
}
