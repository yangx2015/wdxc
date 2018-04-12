package com.ldz.wechat.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.ldz.sys.base.BaseController;
import com.ldz.sys.base.BaseService;
import com.ldz.util.bean.ApiResponse;
import com.ldz.wechat.model.ClLsdw;
import com.ldz.wechat.service.LsdwService;

/**
 * 临时车设定
 */
@RestController
@RequestMapping("wechat/lsdw")
public class LsdwCtrl extends BaseController<ClLsdw, String> {
	 @Autowired
	 private LsdwService service;

	@Override
	protected BaseService<ClLsdw, String> getBaseService() {
		
		return service;
	}

	@RequestMapping(value = "/save", method = { RequestMethod.POST })
	public ApiResponse<String> save(ClLsdw entity) {
		return service.saveEntity(entity);
	}
}
