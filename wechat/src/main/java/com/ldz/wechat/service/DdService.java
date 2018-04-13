package com.ldz.wechat.service;

import java.util.List;
import java.util.Map;

import com.ldz.sys.base.BaseService;
import com.ldz.util.bean.ApiResponse;
import com.ldz.wechat.model.ClDd;
import com.ldz.wechat.model.ClJsy;

public interface DdService extends BaseService<ClDd,String>{
    ApiResponse<String> saveEntity(ClDd entity);

    ApiResponse<String> updateOrderAuditing(ClDd entity);

    ApiResponse<Map<String,Object>> orderdDetails(ClDd entity);


    ApiResponse<List<ClDd>> assignedOrderd(ClDd entity);

    ApiResponse<List<ClJsy>> driverList(ClJsy entity);

    ApiResponse<List<ClDd>> affirmOrderList(ClDd entity);


    ApiResponse<String> updateAssignOrder(ClDd entity);

    ApiResponse<String> updateCancelAssignOrder(ClDd entity);

    ApiResponse<String> updateOrder(ClDd entity);

    ApiResponse<String> updateAffirmOracle(ClDd entity);

    ApiResponse<List<Map<String,Object>>> proceedsDetail(ClDd entity);

    ApiResponse<List<Map<String,Object>>> paymentDetail(ClDd entity);

    ApiResponse<String> updateFinanceOrder(ClDd entity);

    ApiResponse<String> updateFinanceConfirm(String[] ids);
}