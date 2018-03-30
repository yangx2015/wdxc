package com.ldz.biz.module.service;

import com.ldz.util.bean.ApiResponse;
import com.ldz.sys.base.BaseService;
import com.ldz.biz.module.model.ClZdgl;

public interface ZdglService extends BaseService<ClZdgl,String>{
    ApiResponse<String> saveEntity(ClZdgl entity);
    /*
     * 插入一条数据 如果数据存在则更新
     * 
     */
    void insetAndUpdate(ClZdgl entity);
}
