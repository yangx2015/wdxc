package com.ldz.wechat.service;

import com.ldz.sys.base.BaseService;
import com.ldz.util.bean.ApiResponse;
import com.ldz.wechat.model.ClCd;

public interface CdService extends BaseService<ClCd,String> {
    ApiResponse<String> saveEntity(ClCd entity);
    
    ApiResponse<String> updateEntity(ClCd entity);
}
