package com.ldz.wechat.module.controller;

import com.ldz.util.bean.ApiResponse;
import com.ldz.wechat.module.bean.DdClModel;
import com.ldz.wechat.module.model.ClXl;
import com.ldz.wechat.module.model.SysHdyx;
import com.ldz.wechat.module.service.HdService;
import com.ldz.wechat.module.service.XlService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
@RequestMapping("put/xl")
public class ClXlCtrl {
    @Autowired
    private XlService wxXlService;

    @Autowired
    private HdService wxHdService;

	/**
	 * 查询所有线路
	 * @return
	 */
	@RequestMapping(value="/getAll", method={RequestMethod.POST})
	public ApiResponse<List<ClXl>> getAll(){
		return ApiResponse.success(wxXlService.getAll());
	}

	/**
	 * 获取线路下站点和车辆的信息
	 * @param xlId	线路ID
	 * @return
	 */
	@RequestMapping(value = "/getzdcl", method = {RequestMethod.POST})
	public ApiResponse<List<DdClModel>> getBySiteVehicleList(String xlId) {
		return wxXlService.getBySiteVehicleList(xlId);
	}
	/**
	 * 获取活动图片列表 也就是广告位的图片列表
	 */
	@RequestMapping(value = "/getadsense", method = {RequestMethod.POST})
	public ApiResponse<List<SysHdyx>> getAdsenseList() {
		return ApiResponse.success(wxHdService.getAdsenseList());
	}


}
