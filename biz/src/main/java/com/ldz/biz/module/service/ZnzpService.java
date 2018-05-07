package com.ldz.biz.module.service;

import com.ldz.util.bean.ApiResponse;
import com.ldz.sys.base.BaseService;
import com.ldz.biz.module.model.ClZnzp;

import java.util.List;

public interface ZnzpService extends BaseService<ClZnzp,String>{
    ApiResponse<String> saveEntity(ClZnzp entity);

    ApiResponse<String> updateEntity(ClZnzp znzp);

    ApiResponse<List<String>> getXlIds(String zpId);
}
