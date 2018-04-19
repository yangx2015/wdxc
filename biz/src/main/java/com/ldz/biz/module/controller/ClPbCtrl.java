package com.ldz.biz.module.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
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
	
	@Override
	@RequestMapping(value = "/update", method = { RequestMethod.POST })
	public ApiResponse<String> update(ClPb entity) {
		return pbservice.updateEntity(entity);
	}
	
	@RequestMapping(value = "/savepb", method = { RequestMethod.POST })
	public ApiResponse<String> save(ClPb entity,String date) {
		return pbservice.saveEntity(entity,date);
	}

	@RequestMapping("deleteByXlAndCl")
	public ApiResponse<String> deleteByXlAndCl(String xlId,String clId,String date){
		return pbservice.deleteByXlAndCl(xlId,clId,date);
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
