package com.ldz.wechat.service;

import java.util.List;

import com.ldz.sys.base.BaseService;
import com.ldz.util.bean.ApiResponse;
import com.ldz.wechat.model.ClSg;
import com.ldz.wechat.model.ClSgwj;

public interface SgService extends BaseService<ClSg,String>{
    ApiResponse<String> saveEntity(ClSg entity);

    ApiResponse<String> updateEntity(ClSg entity);

    List<ClSgwj> getSgwj(String sgId);

    void setSgwj(ClSg sg);
}
