package com.ldz.wechat.module.service.impl;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.ldz.util.bean.ApiResponse;
import com.ldz.util.commonUtil.JsonUtil;
import com.ldz.util.commonUtil.JwtUtil;
import com.ldz.util.exception.RuntimeCheck;
import com.ldz.wechat.module.mapper.SysJzgxxMapper;
import com.ldz.wechat.module.model.SysJzgxx;
import com.ldz.wechat.module.service.SysJzgxxService;

@Service
public class SysJzgxxServiceImpl implements SysJzgxxService {

	@Autowired
	private SysJzgxxMapper jzgmapper;

	@Override
	public ApiResponse<String> findJzg(String zjhm, String name) {
		RuntimeCheck.ifBlank(zjhm, "证件号码不为空");
		RuntimeCheck.ifBlank(name, "姓名不为空");
		SysJzgxx jzg = jzgmapper.findJzg(name,zjhm);
		RuntimeCheck.ifNull(jzg, "证件号码或姓名有误");
		String userInfo = JsonUtil.toJson(jzg);
		String token = JwtUtil.createWechatToken("jzg",userInfo);
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
		request.setAttribute("type","jzg");
		request.setAttribute("orgCode",jzg.getJgdm());
		request.setAttribute("userInfo",userInfo);
		ApiResponse<String> res = new ApiResponse<>();
		res.setResult(token);
		return res;
	}

	public  SysJzgxx findById(String id){
		return jzgmapper.findById(id);
	}


}
