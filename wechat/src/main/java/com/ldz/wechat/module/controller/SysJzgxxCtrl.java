package com.ldz.wechat.module.controller;

import com.ldz.util.bean.ApiResponse;
import com.ldz.util.exception.RuntimeCheck;
import com.ldz.wechat.module.service.SysJzgxxService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("put/jzg")
public class SysJzgxxCtrl {

	@Autowired
	private SysJzgxxService jzgxxService;
	@PostMapping("/jzgLogin")
	ApiResponse<String> JzgLogin(String key){
		return jzgxxService.JzgLogin(key);
	}
	/**
	 * 通过身份证,姓名获取到教职工信息
	 * @param zjhm  证件号码  也叫工号
	 * @param pwd		密码
	 * @return
	 */
	@PostMapping("/getJzg")
	ApiResponse<String> findJzg(String zjhm ,String pwd){
		return jzgxxService.findJzg(zjhm, pwd);
	}

	@PostMapping("/getInfo")
	ApiResponse<Map<String,Object>> getUserInfo(){
		return jzgxxService.getUserInfo();
	}


	/**
	 * 教职工修改密码
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
		return jzgxxService.mdfPwd(oldPwd,newPwd);
	}
	/**
	 * 教职工修改手机号码-发送短信
	 * @param dn	手机号码
	 * @return
	 */
	@RequestMapping(value = "sendSMS",method = RequestMethod.POST)
	public ApiResponse<String> updateDnSendSMS(@RequestParam(name = "dn")String dn){
		RuntimeCheck.ifBlank(dn,"电话不能为空");
		return jzgxxService.updateDnSendSMS(dn);
	}
	/**
	 * 教职工修改手机号码-发送短信
	 * @param dn	手机号码
	 * @param pollcode	验证码
	 * @return
	 */
	@RequestMapping(value = "updateDn",method = RequestMethod.POST)
	public ApiResponse<String> updateDn(@RequestParam(name = "dn")String dn,@RequestParam(name = "pollcode")String pollcode){
		RuntimeCheck.ifBlank(dn,"电话不能为空");
		RuntimeCheck.ifBlank(pollcode,"短信验证码不能为空");
		return jzgxxService.updateDn(dn,pollcode);
	}

}
