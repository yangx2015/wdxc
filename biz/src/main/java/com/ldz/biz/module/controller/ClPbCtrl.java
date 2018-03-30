package com.ldz.biz.module.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.ldz.biz.module.bean.PbClXlmodel;
import com.ldz.biz.module.bean.PbInfo;
import com.ldz.biz.module.bean.XbXlPb;
import com.ldz.biz.module.model.ClPb;
import com.ldz.biz.module.service.PbService;
import com.ldz.sys.base.BaseController;
import com.ldz.sys.base.BaseService;
import com.ldz.util.bean.ApiResponse;

@RestController
@RequestMapping("api/pb")
public class ClPbCtrl extends BaseController<ClPb, String> {

	@Autowired
	private PbService pbservice;

	@Override
	protected BaseService<ClPb, String> getBaseService() {
		return pbservice;
	}

	@RequestMapping(value = "/update", method = { RequestMethod.POST })
	public ApiResponse<String> update(@RequestBody ClPb entity) {
		return pbservice.updateEntity(entity);
	}

	@RequestMapping(value = "/save", method = { RequestMethod.POST })
	public ApiResponse<String> save(@RequestBody ClPb entity) {
		return pbservice.saveEntity(entity);
	}

	@PostMapping("/xbpb/")
	public ApiResponse<List<PbInfo>> getPbInfo(@RequestBody PbClXlmodel  pbclxlmodel) {
		return pbservice.getPbInfo(pbclxlmodel);
	}

	@PostMapping("/xbbjpb/")
	public ApiResponse<List<XbXlPb>> getAllPbInfo(@RequestBody PbClXlmodel  pbclxlmodel) {
		return pbservice.getAllPbInfo(pbclxlmodel);
	}
}
