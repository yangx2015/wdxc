package com.ldz.biz.module.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ldz.biz.module.bean.gpsSJInfo;
import com.ldz.biz.module.model.ClSbyxsjjl;
import com.ldz.biz.module.service.SbyxsjjlService;
import com.ldz.sys.base.BaseController;
import com.ldz.sys.base.BaseService;
import com.ldz.util.bean.ApiResponse;

@RestController
@RequestMapping("api/clsbyxsjjl")
public class ClSbyxsjjlCtrl extends BaseController<ClSbyxsjjl, String> {
	@Autowired
	private SbyxsjjlService service;

	@Override
	protected BaseService<ClSbyxsjjl, String> getBaseService() {
		return service;
	}

	
	@PostMapping("/history")
	public ApiResponse<List<ClSbyxsjjl>> historyTrajectory(@RequestBody gpsSJInfo gpssjinfo) {
	
		return service.historyTrajectory(gpssjinfo);
	}
}
   