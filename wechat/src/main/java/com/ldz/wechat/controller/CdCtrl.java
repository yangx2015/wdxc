package com.ldz.wechat.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.ldz.sys.base.BaseController;
import com.ldz.sys.base.BaseService;
import com.ldz.util.bean.ApiResponse;
import com.ldz.wechat.model.ClCd;
import com.ldz.wechat.service.CdService;

/**
 * 车队设定
 */
@RestController
@RequestMapping("wechat/cd")
public class CdCtrl extends BaseController<ClCd, String> {
	 @Autowired
	 private  CdService cdservice;

	@Override
	protected BaseService<ClCd, String> getBaseService() {
		return cdservice;
	}
	@Override
	@RequestMapping(value = "/update", method = { RequestMethod.POST })
	public ApiResponse<String> update(ClCd entity) {
		return cdservice.updateEntity(entity);
	}
	@Override
	@RequestMapping(value = "/save", method = { RequestMethod.POST })
	public ApiResponse<String> save(ClCd entity) {
		return cdservice.saveEntity(entity);
	}


}
