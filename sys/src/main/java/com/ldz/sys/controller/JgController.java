package com.ldz.sys.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.ldz.sys.base.BaseController;
import com.ldz.sys.base.BaseService;
import com.ldz.sys.model.SysJg;
import com.ldz.sys.service.JgService;
import com.ldz.util.bean.ApiResponse;

@RestController
@RequestMapping("api/jg")
public class JgController extends BaseController<SysJg, String> {
	@Autowired
	private JgService jgService;

	@Override
	protected BaseService<SysJg, String> getBaseService() {
		return jgService;
	}

	@RequestMapping("add")
	public ApiResponse<String> add(SysJg entity) {
		return jgService.saveEntity(entity);
	}

	@RequestMapping("getOrgTree")
	public ApiResponse<List<SysJg>> getOrgTree() {
		return jgService.getOrgTree();
	}

	@Override
	@RequestMapping(value = "/save", method = { RequestMethod.POST })
	public ApiResponse<String> save(SysJg jg) {
		return jgService.saveEntity(jg);
	}

}
