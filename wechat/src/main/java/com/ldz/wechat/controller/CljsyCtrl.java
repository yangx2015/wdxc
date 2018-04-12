package com.ldz.wechat.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.ldz.sys.base.BaseController;
import com.ldz.sys.base.BaseService;
import com.ldz.util.bean.ApiResponse;
import com.ldz.wechat.model.ClJsy;
import com.ldz.wechat.service.JsyService;

/**
 * 驾驶员设定
 */
@RestController
@RequestMapping("wechat/jsy")
public class CljsyCtrl extends BaseController<ClJsy, String> {
	 @Autowired
	 private  JsyService jsyservice;

	@Override
	protected BaseService<ClJsy, String> getBaseService() {
		
		return jsyservice;
	}

	@RequestMapping(value = "/update", method = { RequestMethod.POST })
	public ApiResponse<String> update(ClJsy entity) {
		return jsyservice.updateEntity(entity);
	}

	@PostMapping("/save")
	public ApiResponse<String> save(ClJsy entity) {
		return jsyservice.saveEntity(entity);
	}
	
	
}
