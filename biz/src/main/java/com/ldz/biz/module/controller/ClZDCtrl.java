package com.ldz.biz.module.controller;

import com.ldz.biz.module.bean.DdClModel;
import com.ldz.biz.module.model.ClZd;
import com.ldz.biz.module.service.ZdService;
import com.ldz.sys.base.BaseController;
import com.ldz.sys.base.BaseService;
import com.ldz.util.bean.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/clzd")
public class ClZDCtrl extends BaseController<ClZd, String> {
	@Autowired
	private ZdService zdservice;

	@Override
	protected BaseService<ClZd, String> getBaseService() {

		return zdservice;
	}

	@RequestMapping(value = "/update", method = { RequestMethod.POST })
	public ApiResponse<String> update(ClZd entity) {
		return zdservice.updateEntity(entity);
	}

	@RequestMapping(value = "/save", method = { RequestMethod.POST })
	public ApiResponse<String> save(ClZd entity) {
		return zdservice.saveEntity(entity);
	}

    @MessageMapping("/getzdcl")
//	@RequestMapping(value = "/getzdcl", method = { RequestMethod.GET })
	public ApiResponse<List<DdClModel>> getBySiteVehicleList(String xlId) {
		xlId="1";
		return zdservice.getBySiteVehicleList(xlId);
	}

	@RequestMapping("getByXlId")
	public ApiResponse<List<ClZd>> getByXlId(String xlId){
		List<ClZd> stations = zdservice.getByXlId(xlId);
		return ApiResponse.success(stations);
	}

}
