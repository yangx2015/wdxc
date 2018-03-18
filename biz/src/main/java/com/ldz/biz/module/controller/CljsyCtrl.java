package com.ldz.biz.module.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ldz.biz.module.model.ClJsy;
import com.ldz.biz.module.service.JsyService;
import com.ldz.sys.base.BaseController;
import com.ldz.sys.base.BaseService;

/**
 * 驾驶员设定
 */
@RestController
@RequestMapping("api/jsy")
public class CljsyCtrl extends BaseController<ClJsy, String> {
	 @Autowired
	 private  JsyService jsyservice;

	@Override
	protected BaseService<ClJsy, String> getBaseService() {
		
		return jsyservice;
	}

}
