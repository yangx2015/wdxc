package com.ldz.biz.module.service;

import com.ldz.util.bean.ApiResponse;
import com.ldz.sys.base.BaseService;
import com.ldz.biz.module.model.ClPb;

public interface PbService extends BaseService<ClPb,String>{
    ApiResponse<String> saveEntity(ClPb entity);
}
