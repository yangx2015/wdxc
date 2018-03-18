package com.ldz.biz.module.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ldz.biz.module.model.ClCl;
import com.ldz.biz.module.service.ClService;
import com.ldz.sys.base.BaseController;
import com.ldz.sys.base.BaseService;

/**
 * 车辆设定
 */
@RestController
@RequestMapping("api/cl")
public class ClCtrl extends BaseController<ClCl, String> {
	 @Autowired
	 private  ClService clservice;

	@Override
	protected BaseService<ClCl, String> getBaseService() {
		
		return clservice;
	}

}
