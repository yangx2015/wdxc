package com.ldz.wechat.module.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.ldz.util.bean.ApiResponse;
import com.ldz.wechat.base.BaseController;
import com.ldz.wechat.base.BaseService;
import com.ldz.wechat.module.model.SysYjfk;
import com.ldz.wechat.module.service.YjService;

/**
 * 意见
 */
@RestController
@RequestMapping("put/yj")
public class YjController extends BaseController<SysYjfk, String> {
	@Autowired
	private YjService service;

	@Override
	protected BaseService<SysYjfk, String> getBaseService() {
		return service;
	}

	@Override
	@RequestMapping(value = "/save", method = { RequestMethod.POST })
	public ApiResponse<String> save(SysYjfk entity) {

		return service.saveEntity(entity);
	}

	@Override
	@RequestMapping(value="/update", method={RequestMethod.POST})
	public ApiResponse<String> update(SysYjfk entity){
		return service.updateEntity(entity);
	}
}
