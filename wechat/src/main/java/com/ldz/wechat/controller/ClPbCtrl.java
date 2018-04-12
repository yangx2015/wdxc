package com.ldz.wechat.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.ldz.sys.base.BaseController;
import com.ldz.sys.base.BaseService;
import com.ldz.util.bean.ApiResponse;
import com.ldz.wechat.bean.PbClXlmodel;
import com.ldz.wechat.bean.PbInfo;
import com.ldz.wechat.bean.XbXlPb;
import com.ldz.wechat.model.ClPb;
import com.ldz.wechat.service.PbService;

@RestController
@RequestMapping("wechat/pb")
public class ClPbCtrl extends BaseController<ClPb, String> {

	@Autowired
	private PbService pbservice;

	@Override
	protected BaseService<ClPb, String> getBaseService() {
		return pbservice;
	}
	
	@Override
	@RequestMapping(value = "/update", method = { RequestMethod.POST })
	public ApiResponse<String> update(ClPb entity) {
		return pbservice.updateEntity(entity);
	}
	
	@Override
	@RequestMapping(value = "/save", method = { RequestMethod.POST })
	public ApiResponse<String> save(ClPb entity) {
		return pbservice.saveEntity(entity);
	}

	@PostMapping("/xbpb/")
	public ApiResponse<List<PbInfo>> getPbInfo(PbClXlmodel  pbclxlmodel) {
		return pbservice.getPbInfo(pbclxlmodel);
	}

	@PostMapping("/xbbjpb/")
	public ApiResponse<List<XbXlPb>> getAllPbInfo(PbClXlmodel  pbclxlmodel) {
		return pbservice.getAllPbInfo(pbclxlmodel);
	}
}
