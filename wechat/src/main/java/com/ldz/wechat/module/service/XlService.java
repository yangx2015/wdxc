package com.ldz.wechat.module.service;

import com.ldz.util.bean.ApiResponse;
import com.ldz.wechat.base.BaseService;
import com.ldz.wechat.module.bean.DdClModel;
import com.ldz.wechat.module.model.ClXl;

import java.util.List;

public interface XlService extends BaseService<ClXl,String> {
    List<ClXl> getAll();

    ApiResponse<List<DdClModel>> getBySiteVehicleList(String xlId);
}
