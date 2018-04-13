package com.ldz.wechat.service;

import com.ldz.sys.base.BaseService;
import com.ldz.util.bean.ApiResponse;
import com.ldz.wechat.model.ClZdgl;

public interface ZdglService extends BaseService<ClZdgl,String>{
    ApiResponse<String> saveEntity(ClZdgl entity);
    /*
     * 插入一条数据 如果数据存在则更新
     * 
     */
    void insetAndUpdate(ClZdgl entity);

    ApiResponse<String> updateEntity(ClZdgl entity);
}