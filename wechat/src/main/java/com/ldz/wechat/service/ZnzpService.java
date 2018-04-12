package com.ldz.wechat.service;

import com.ldz.sys.base.BaseService;
import com.ldz.util.bean.ApiResponse;
import com.ldz.wechat.model.ClZnzp;

public interface ZnzpService extends BaseService<ClZnzp,String>{
    ApiResponse<String> saveEntity(ClZnzp entity);

    ApiResponse<String> updateEntity(ClZnzp znzp);
}
