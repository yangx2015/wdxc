package com.ldz.wechat.service;

import com.ldz.sys.base.BaseService;
import com.ldz.util.bean.ApiResponse;
import com.ldz.wechat.model.ClLsdw;

public interface LsdwService extends BaseService<ClLsdw,String>{
    ApiResponse<String> saveEntity(ClLsdw entity);
}
