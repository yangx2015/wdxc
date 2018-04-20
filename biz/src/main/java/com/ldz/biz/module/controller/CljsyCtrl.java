package com.ldz.biz.module.controller;

import com.ldz.sys.model.SysYh;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.ldz.biz.module.model.ClJsy;
import com.ldz.biz.module.service.JsyService;
import com.ldz.sys.base.BaseController;
import com.ldz.sys.base.BaseService;
import com.ldz.util.bean.ApiResponse;

import java.util.List;

import javax.validation.Valid;

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

	@RequestMapping(value = "/update", method = { RequestMethod.POST })
	public ApiResponse<String> update(@Valid ClJsy entity) {
		return jsyservice.updateEntity(entity);
	}

	@PostMapping("/save")
	public ApiResponse<String> save(@Valid ClJsy entity) {
		return jsyservice.saveEntity(entity);
	}

	@RequestMapping("notBindList")
	public ApiResponse<List<ClJsy>> notBindList(){
		SysYh user = getCurrentUser();
		return jsyservice.notBindList(user);
	}
	
}
