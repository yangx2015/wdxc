package com.ldz.biz.module.service;

import java.util.List;

import com.ldz.biz.module.bean.GpsInfo;
import com.ldz.biz.module.model.ClCssd;
import com.ldz.sys.base.BaseService;
import com.ldz.util.bean.ApiResponse;

public interface CssdService extends BaseService<ClCssd,String>{
    ApiResponse<String> saveEntity(ClCssd entity);

    ApiResponse<String> updateEntity(ClCssd entity);

    ApiResponse<List<ClCssd>> getByCx(String cx);
    
    
    ApiResponse<String> setCssds(List<String> cphs,String csz);
    
    void insetAndUpdate(ClCssd entity);
    
    public ApiResponse<String> senZl(GpsInfo info);
}
