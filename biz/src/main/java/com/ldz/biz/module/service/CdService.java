package com.ldz.biz.module.service;

import com.ldz.biz.module.model.ClCd;
import com.ldz.sys.base.BaseService;
import com.ldz.util.bean.ApiResponse;

public interface CdService extends BaseService<ClCd,String> {
    ApiResponse<String> saveEntity(ClCd entity);
}
