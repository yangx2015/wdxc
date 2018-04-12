package com.ldz.wechat.service;

import java.util.List;

import com.ldz.sys.base.BaseService;
import com.ldz.util.bean.ApiResponse;
import com.ldz.wechat.model.ClCl;

public interface ClService extends BaseService<ClCl,String>{
    ClCl findByOrgCode(String code);
    List<ClCl> getOrgCarList(String orgCode);
    ApiResponse<String> saveEntity(ClCl entity);
    ApiResponse<String> updateEntity(ClCl entity);
    
}
