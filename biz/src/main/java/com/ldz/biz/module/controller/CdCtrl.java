package com.ldz.biz.module.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.ldz.biz.module.model.ClCd;
import com.ldz.biz.module.service.CdService;
import com.ldz.sys.base.BaseController;
import com.ldz.sys.base.BaseService;
import com.ldz.util.bean.ApiResponse;

/**
 * 车队设定
 */
@RestController
@RequestMapping("api/cd")
public class CdCtrl extends BaseController<ClCd, String> {
	 @Autowired
	 private  CdService cdservice;

	@Override
	protected BaseService<ClCd, String> getBaseService() {
		return cdservice;
	}

	@RequestMapping(value = "/update", method = { RequestMethod.POST })
	public ApiResponse<String> update(ClCd entity) {
		return cdservice.updateEntity(entity);
	}

	@RequestMapping(value = "/save", method = { RequestMethod.POST })
	public ApiResponse<String> save(ClCd entity) {
		return cdservice.saveEntity(entity);
	}


}
