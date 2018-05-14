package com.ldz.wechat.module.service;

import com.ldz.util.bean.ApiResponse;
import com.ldz.wechat.base.BaseService;
import com.ldz.wechat.module.model.ClDd;
import com.ldz.wechat.module.model.ClJsy;

import java.util.List;
import java.util.Map;

public interface DdService extends BaseService<ClDd,String> {

    ApiResponse<String> saveEntity(ClDd entity, String userId);

    ApiResponse<String> updateOrder(ClDd entity,String userId);

    ApiResponse<String> updateAffirmOracle(ClDd entity, String userId);

    ApiResponse<String> driverConfirm(String id,String userId);
}
