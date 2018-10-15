package com.ldz.biz.module.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ldz.biz.module.model.ClWf;
import com.ldz.biz.module.service.WfService;
import com.ldz.sys.base.BaseController;
import com.ldz.sys.base.BaseService;
import com.ldz.util.bean.ApiResponse;

@RestController
@RequestMapping("/api/wfxx")
public class ClWfCtrl extends BaseController<ClWf, String> {

	@Autowired
	private WfService wfService;
	
	@Override
	protected BaseService<ClWf, String> getBaseService() {
		return wfService;
	}
	
	/**
	 * 更新违法状态为已处理
	 * @param id
	 * @return
	 */
	@GetMapping(value = "/updateWfzt/{id}")
	public ApiResponse<String> updateWfzt(@PathVariable("id") String id) {
		return wfService.updateWfzt(id);
	}
}
