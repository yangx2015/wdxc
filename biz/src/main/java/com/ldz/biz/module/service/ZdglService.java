package com.ldz.biz.module.service;

import com.ldz.biz.module.model.ClZdgl;
import com.ldz.sys.base.BaseService;
import com.ldz.util.bean.ApiResponse;

import java.io.IOException;
import java.util.List;
import java.util.Map;

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
    //获取设备暂时
	ApiResponse<Map<String, Integer>> getzdxc();

    ApiResponse<String> getVersionInfo(String deviceId);

    ApiResponse<List<String>> saveBatch(String filePath) throws IOException;

    void saveBatch(List<ClZdgl> clZdgls);
}
