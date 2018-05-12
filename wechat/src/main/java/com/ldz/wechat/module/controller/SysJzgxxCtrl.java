package com.ldz.wechat.module.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ldz.util.bean.ApiResponse;
import com.ldz.wechat.module.model.SysJzgxx;
import com.ldz.wechat.module.service.SysJzgxxService;

@RestController
@RequestMapping("put/jzg")
public class SysJzgxxCtrl {

	@Autowired
	private SysJzgxxService jzgxxService;
	
	
	@PostMapping("/getJzg")
	ApiResponse<SysJzgxx> findJzg(String name ,String idCard){
		
		return jzgxxService.findJzg(name, idCard);
	}
	
	
}
