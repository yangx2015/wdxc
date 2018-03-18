package com.ldz.biz.module.service;

import com.ldz.biz.module.model.ClSgwj;
import com.ldz.util.bean.ApiResponse;
import com.ldz.sys.base.BaseService;
import com.ldz.biz.module.model.ClSg;

import java.util.List;

public interface SgService extends BaseService<ClSg,String>{
    ApiResponse<String> saveEntity(ClSg entity);

    ApiResponse<String> updateEntity(ClSg entity);

    List<ClSgwj> getSgwj(String sgId);

    void setSgwj(ClSg sg);
}
