package com.ldz.biz.module.service;

import com.ldz.biz.module.bean.DdTongjiTJ;
import com.ldz.biz.module.bean.Ddtongji;
import com.ldz.biz.module.model.ClDd;
import com.ldz.biz.module.model.ClJsy;
import com.ldz.sys.base.BaseService;
import com.ldz.sys.model.SysYh;
import com.ldz.util.bean.ApiResponse;

import java.util.List;
import java.util.Map;

public interface DdService extends BaseService<ClDd,String>{
    ApiResponse<String> saveEntity(ClDd entity, SysYh userInfo);

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

	ApiResponse<Ddtongji> ddtongji(DdTongjiTJ dd);
}
