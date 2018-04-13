package com.ldz.wechat.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.ldz.sys.base.BaseController;
import com.ldz.sys.base.BaseService;
import com.ldz.util.bean.ApiResponse;
import com.ldz.wechat.model.ClXlzd;
import com.ldz.wechat.service.XlzdService;

@RestController
@RequestMapping("wechat/xlzd")
public class XLZDCtrl extends BaseController<ClXlzd, String> {
    @Autowired
    private XlzdService xlzdservice;
	@Override
	protected BaseService<ClXlzd, String> getBaseService() {
		return xlzdservice;
	}
	@Override
	@RequestMapping(value = "/update", method = { RequestMethod.POST })
	public ApiResponse<String> update(ClXlzd entity) {
		return xlzdservice.updateEntity(entity);
	}
	@Override
	@RequestMapping(value = "/save", method = { RequestMethod.POST })
	public ApiResponse<String> save(ClXlzd entity) {
		return xlzdservice.saveEntity(entity);
	}
}
