package com.ldz.biz.module.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ldz.biz.module.model.ClLsc;
import com.ldz.biz.module.service.LscService;
import com.ldz.sys.base.BaseController;
import com.ldz.sys.base.BaseService;

/**
 * 临时车设定
 */
@RestController
@RequestMapping("api/lsc")
public class CllscCtrl extends BaseController<ClLsc, String> {
	 @Autowired
	 private  LscService lscService;

	@Override
	protected BaseService<ClLsc, String> getBaseService() {
		
		return lscService;
	}

}
