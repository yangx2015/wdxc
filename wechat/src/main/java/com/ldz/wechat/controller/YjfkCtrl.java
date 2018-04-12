package com.ldz.wechat.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.ldz.sys.base.BaseController;
import com.ldz.sys.base.BaseService;
import com.ldz.util.bean.ApiResponse;
import com.ldz.wechat.model.yjfk;
import com.ldz.wechat.service.yjfkService;

@RestController
@RequestMapping("wechat/yjfk")
public class YjfkCtrl  extends BaseController<yjfk, String>{
	@Autowired
	private yjfkService yjService;

	@Override
	protected BaseService<yjfk, String> getBaseService() {
		return yjService;
	}

	@Override
	@RequestMapping(value = "/update", method = { RequestMethod.POST })
	public ApiResponse<String> update(yjfk entity) {
		return yjService.updateEntity(entity);
	}
	
	@Override
	@RequestMapping(value = "/save", method = { RequestMethod.POST })
	public ApiResponse<String> save(yjfk entity) {
		return yjService.saveEntity(entity);
	}
	
}
