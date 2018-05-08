package com.ldz.wechat.module.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ldz.util.bean.ApiResponse;
import com.ldz.wechat.module.model.ClJsy;
import com.ldz.wechat.module.service.ClJsyService;

@RestController
@RequestMapping("put/jsy")
public class ClJsyCtrl {

	@Autowired
	private ClJsyService jsyService;

	@PostMapping("/getjsy")
	public ApiResponse<ClJsy> findJsy(String sfzhm, String xm) {

		return jsyService.findJsy(sfzhm, xm);
	}
	@PostMapping("update")
	public ApiResponse<String> updatejsy(ClJsy jsy){
		
		 return jsyService.updatejsy(jsy);
	}

}
