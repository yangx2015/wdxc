package com.ldz.biz.module.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.ldz.biz.module.model.ClZd;
import com.ldz.biz.module.service.ZdService;
import com.ldz.sys.base.BaseController;
import com.ldz.sys.base.BaseService;
import com.ldz.util.bean.ApiResponse;

@RestController
@RequestMapping("clzd")
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
	@RequestMapping(value = "/getBySiteVehicle", method = { RequestMethod.GET })
	public ApiResponse<String> save() {
		 zdservice.getBySiteVehicleList("1");
		return ApiResponse.saveSuccess();
	}

}
