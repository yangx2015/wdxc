package com.ldz.wechat.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.ldz.sys.base.BaseController;
import com.ldz.sys.base.BaseService;
import com.ldz.util.bean.ApiResponse;
import com.ldz.wechat.model.ClXl;
import com.ldz.wechat.service.XlService;

@RestController
@RequestMapping("wechat/xl")
public class ClXlCtrl  extends BaseController<ClXl, String>{
    @Autowired
    private XlService xlservice;
	@Override
	protected BaseService<ClXl, String> getBaseService() {
		return xlservice;
	}
	@Override
	@RequestMapping(value = "/update", method = { RequestMethod.POST })
	public ApiResponse<String> update(ClXl entity) {
		return xlservice.updateEntity(entity);
	}
	@Override
	@RequestMapping(value = "/save", method = { RequestMethod.POST })
	public ApiResponse<String> save(ClXl entity) {
		return xlservice.saveEntity(entity);
	}
	
	
}
