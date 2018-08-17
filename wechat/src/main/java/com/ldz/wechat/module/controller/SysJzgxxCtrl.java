package com.ldz.wechat.module.controller;

import com.ldz.util.bean.ApiResponse;
import com.ldz.wechat.module.service.SysJzgxxService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("put/jzg")
public class SysJzgxxCtrl {

	@Autowired
	private SysJzgxxService jzgxxService;
	
	
	@PostMapping("/getJzg")
	ApiResponse<String> findJzg(String zjhm ,String name){
		
		return jzgxxService.findJzg(zjhm, name);
	}

	@PostMapping("/getInfo")
	ApiResponse<Map<String,Object>> getUserInfo(){
		return jzgxxService.getUserInfo();
	}
	
}
