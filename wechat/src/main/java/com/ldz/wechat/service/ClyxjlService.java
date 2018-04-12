package com.ldz.wechat.service;

import com.ldz.sys.base.BaseService;
import com.ldz.util.bean.ApiResponse;
import com.ldz.wechat.model.ClClyxjl;

public interface ClyxjlService extends BaseService<ClClyxjl,String>{
    ApiResponse<String> saveEntity(ClClyxjl entity);
}
