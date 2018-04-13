package com.ldz.wechat.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.ldz.sys.base.BaseController;
import com.ldz.sys.base.BaseService;
import com.ldz.util.bean.ApiResponse;
import com.ldz.wechat.bean.DdClModel;
import com.ldz.wechat.model.ClZd;
import com.ldz.wechat.service.ZdService;

@RestController
@RequestMapping("wechat/clzd")
public class ClZDCtrl extends BaseController<ClZd, String> {
	@Autowired
	private ZdService zdservice;

	@Override
	protected BaseService<ClZd, String> getBaseService() {

		return zdservice;
	}
	@Override
	@RequestMapping(value = "/update", method = { RequestMethod.POST })
	public ApiResponse<String> update(ClZd entity) {
		return zdservice.updateEntity(entity);
	}
	@Override
	@RequestMapping(value = "/save", method = { RequestMethod.POST })
	public ApiResponse<String> save(ClZd entity) {
		return zdservice.saveEntity(entity);
	}

	/**
	 * 获取线路下站点和车辆的信息
	 * @param xlId	线路ID
	 * @return
	 */
//    @MessageMapping("/getzdcl")
	@RequestMapping(value = "/getzdcl", method = {RequestMethod.POST})
	public ApiResponse<List<DdClModel>> getBySiteVehicleList(String xlId) {
		return zdservice.getBySiteVehicleList(xlId);
	}

	@RequestMapping("getByXlId")
	public ApiResponse<List<ClZd>> getByXlId(String xlId){
		List<ClZd> stations = zdservice.getByXlId(xlId);
		return ApiResponse.success(stations);
	}

}
