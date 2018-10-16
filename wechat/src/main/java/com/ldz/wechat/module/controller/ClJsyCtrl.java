package com.ldz.wechat.module.controller;

import com.ldz.util.exception.RuntimeCheck;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.ldz.util.bean.ApiResponse;
import com.ldz.wechat.module.model.ClJsy;
import com.ldz.wechat.module.service.ClJsyService;

import java.util.Map;

@RestController
@RequestMapping("put/jsy")
public class ClJsyCtrl {

	@Autowired
	private ClJsyService jsyService;

	@RequestMapping("getInfo")
	public ApiResponse<Map<String,Object>> getInfo(){
		return jsyService.getInfo();
	}

	/**
	 * 通过驾驶员手机号、密码
	 * @param sjh	手机号
	 * @param pwd	驾驶员登录密码
	 * @return
	 */
	@PostMapping("/getjsy")
	public ApiResponse<Map<String,Object>> findJsy(String sjh, String pwd) {
		return jsyService.findJsy(sjh, pwd);
	}
	/*
	 * 修改驾驶员信息
	 */
	@PostMapping("/update")
	public ApiResponse<String> updatejsy(ClJsy jsy){
		 return jsyService.updatejsy(jsy);
	}

	/**
	 * 驾驶员修改密码
	 * @param oldPwd	原始密码
	 * @param newPwd	新密码
	 * @param secPwd	再次输入密码
	 * @return
	 */
	@RequestMapping(value = "mdfPwd",method = RequestMethod.POST)
	public ApiResponse<String> mdfPwd(@RequestParam(name = "oldPwd")String oldPwd,
									  @RequestParam(name = "newPwd")String newPwd,
									  @RequestParam(name = "secPwd")String secPwd){
		RuntimeCheck.ifTrue((
				StringUtils.isEmpty(oldPwd) ||
						StringUtils.isEmpty(newPwd) ||
						StringUtils.isEmpty(secPwd)),"请输入密码");
		RuntimeCheck.ifTrue(!newPwd.equals(secPwd),"两次输入密码不一致");
		return jsyService.mdfPwd(oldPwd,newPwd);
	}

}
