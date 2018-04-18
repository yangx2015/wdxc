package com.ldz.biz.module.service;

import com.ldz.biz.module.model.ClZdgl;
import com.ldz.sys.base.BaseService;
import com.ldz.util.bean.ApiResponse;

import java.util.List;

public interface ZdglService extends BaseService<ClZdgl,String>{
    ApiResponse<String> saveEntity(ClZdgl entity);
    /*
     * 插入一条数据 如果数据存在则更新
     * 
     */
    void insetAndUpdate(ClZdgl entity);

    ApiResponse<String> updateEntity(ClZdgl entity);
    /**
     * 查询所有未被绑定的未被停用的终端
     * @return
     */
    ApiResponse<List<ClZdgl>> unboundList();
}
