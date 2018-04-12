package com.ldz.wechat.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.ldz.sys.base.BaseController;
import com.ldz.sys.base.BaseService;
import com.ldz.util.bean.ApiResponse;
import com.ldz.wechat.model.ClLsc;
import com.ldz.wechat.service.LscService;

/**
 * 临时车设定
 */
@RestController
@RequestMapping("wechat/lsc")
public class CllscCtrl extends BaseController<ClLsc, String> {
	 @Autowired
	 private  LscService lscService;

	@Override
	protected BaseService<ClLsc, String> getBaseService() {
		
		return lscService;
	}

	@RequestMapping(value = "/save", method = { RequestMethod.POST })
	public ApiResponse<String> save(ClLsc entity) {
		return lscService.saveEntity(entity);
	}
}
