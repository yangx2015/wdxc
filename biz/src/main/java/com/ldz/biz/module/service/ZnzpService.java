package com.ldz.biz.module.service;

import com.ldz.util.bean.ApiResponse;
import com.ldz.sys.base.BaseService;
import com.ldz.biz.module.model.ClZnzp;

public interface ZnzpService extends BaseService<ClZnzp,String>{
    ApiResponse<String> saveEntity(ClZnzp entity);
}
