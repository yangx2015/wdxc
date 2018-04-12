package com.ldz.wechat.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ldz.sys.base.BaseController;
import com.ldz.sys.base.BaseService;
import com.ldz.util.bean.ApiResponse;
import com.ldz.wechat.bean.gpsSJInfo;
import com.ldz.wechat.model.ClGpsLs;
import com.ldz.wechat.service.GpsLsService;

@RestController
@RequestMapping("wechat/clgpsls")
public class ClGpsLsCtrl extends BaseController<ClGpsLs, String> {

	@Autowired
	private GpsLsService gpslsservice;

	@Override
	protected BaseService<ClGpsLs, String> getBaseService() {
		return gpslsservice;
	}

	@PostMapping("/allgpsls")
	ApiResponse<String[]> getZdbhAllLsGps(gpsSJInfo info) {

		return gpslsservice.getZdbhAllLsGps(info);
	}

}
