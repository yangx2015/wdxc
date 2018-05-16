package com.ldz.wechat.module.service.impl;

import com.ldz.util.commonUtil.JsonUtil;
import com.ldz.util.commonUtil.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ldz.util.bean.ApiResponse;
import com.ldz.util.exception.RuntimeCheck;
import com.ldz.wechat.module.mapper.SysJzgxxMapper;
import com.ldz.wechat.module.model.SysJzgxx;
import com.ldz.wechat.module.service.SysJzgxxService;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

@Service
public class SysJzgxxServiceImpl implements SysJzgxxService {

	@Autowired
	private SysJzgxxMapper jzgmapper;

	@Override
	public ApiResponse<String> findJzg(String idCard, String name) {
		SysJzgxx jzg = jzgmapper.findJzg(idCard, name);
		String userInfo = JsonUtil.toJson(jzg);
		RuntimeCheck.ifNull(jzg, "身份证或者姓名有误");
		String token = JwtUtil.createWechatToken("jzg",userInfo);
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
		request.setAttribute("type",jzg);
		request.setAttribute("userInfo",userInfo);
		ApiResponse<String> res = new ApiResponse<>();
		res.setResult(token);
		return res;
	}

	public  SysJzgxx findById(String id){
		return jzgmapper.findById(id);
	}


}
