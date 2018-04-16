package com.ldz.biz.module.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.ldz.biz.module.bean.websocketInfo;
import com.ldz.biz.module.model.ClCl;
import com.ldz.biz.module.service.ClService;
import com.ldz.biz.module.service.GpsService;
import com.ldz.sys.base.BaseController;
import com.ldz.sys.base.BaseService;
import com.ldz.sys.model.SysYh;
import com.ldz.util.bean.ApiResponse;

/**
 * 车辆设定
 */
@RestController
@RequestMapping("api/cl")
public class ClCtrl extends BaseController<ClCl, String> {
	@Autowired
	private ClService clservice;
	@Autowired
	private GpsService gpsservice;

	@RequestMapping("getOrgCarList")
	public ApiResponse<List<ClCl>> getOrgCarList() {
		SysYh yh = getCurrentUser();
		List<ClCl> carList = clservice.getOrgCarList(yh.getJgdm());
		return ApiResponse.success(carList);
	}

	@Override
	protected BaseService<ClCl, String> getBaseService() {
		return clservice;
	}

	@RequestMapping(value = "/update", method = { RequestMethod.POST })
	public ApiResponse<String> update(ClCl entity) {
		return clservice.updateEntity(entity);
	}

	@RequestMapping(value = "/save", method = { RequestMethod.POST })
	public ApiResponse<String> save(ClCl entity) {
		return clservice.saveEntity(entity);
	}

	@GetMapping("/InitClGps")
	public ApiResponse<List<websocketInfo>> inintGps() {

		return gpsservice.inintGps();

	}

}
