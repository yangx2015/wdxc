package com.ldz.biz.module.service;

import com.ldz.biz.module.bean.*;
import com.ldz.biz.module.model.ClPb;
import com.ldz.sys.base.BaseService;
import com.ldz.util.bean.ApiResponse;

import java.util.List;
import java.util.Map;

public interface PbService extends BaseService<ClPb, String> {
	ApiResponse<String> saveEntity(ClPb entity);

	ApiResponse<String> updateEntity(ClPb entity);

	/*
	 * 根据日期,车辆类型,线路类型获取到当日排版信息
	             clcx :车辆车型 (10:小车,20:大车,30:校巴)
	             lulx;:线路类型(10:校巴,20大车)
	             date2; 后台转换日期后的字符串类型
	 */
	ApiResponse<List<PbInfo>> getPbInfo(PbClXlmodel pbclxlmodel);

	/*
	 * 获取所有路线的排班信息
	 * 
	             clcx :车辆车型 (10:小车,20:大车,30:校巴)  不传就是该线路下面所有的车辆
	             lulx;:线路类型(10:校巴,20大车)
	             date2; 后台转换日期后的字符串类型
	 */
	ApiResponse<List<XbXlPb>> getAllPbInfo(PbClXlmodel pbclxlmodel);

	ApiResponse<String> deleteByXlAndCl(String xlId, String clId,String date);

	ApiResponse<List<ClClModel>> getAllNotPbClList(String xlId, String date,String cx);
    //班车统计
	ApiResponse<List<PbInfo>> bancheTj(PbClXlmodel pbclxlmodel);
    //校巴开班条形图
	ApiResponse<JrXbKb> xbkb(PbClXlmodel pbclxlmodel);

    ApiResponse<Map<String,String>> savePbList(ClPb entity);

	ApiResponse<List<PbInfo>> checkPbCl(ClPb entity);

	ApiResponse<String> delPbList(ClPb entity);
	//移除一组排班
    void removePbList(String pbId);
	/**
	 * 修改排班时间.
	 * id	排班ID
	 * kssj	开始时间
	 * jssj	结束时间
	 */
	ApiResponse<String> updPbTime(ClPb entity);
}
