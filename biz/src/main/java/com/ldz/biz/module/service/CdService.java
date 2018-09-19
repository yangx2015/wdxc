package com.ldz.biz.module.service;

import com.ldz.biz.module.model.ClCd;
import com.ldz.biz.module.model.ClCl;
import com.ldz.biz.module.model.ClJsy;
import com.ldz.sys.base.BaseService;
import com.ldz.util.bean.ApiResponse;

import java.util.List;

public interface CdService extends BaseService<ClCd,String> {
    ApiResponse<String> saveEntity(ClCd entity);

    ApiResponse<String> updateEntity(ClCd entity);

    ApiResponse<List<ClCl>> notBindCarList();

    ApiResponse<List<ClJsy>> notBindDriverList();
}
