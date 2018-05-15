package com.ldz.wechat.module.service.impl;

import com.ldz.util.commonUtil.JsonUtil;
import com.ldz.util.commonUtil.JwtUtil;
import com.ldz.wechat.module.model.SysJzgxx;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ldz.util.bean.ApiResponse;
import com.ldz.util.exception.RuntimeCheck;
import com.ldz.wechat.module.mapper.ClJsyMapper;
import com.ldz.wechat.module.model.ClJsy;
import com.ldz.wechat.module.service.ClJsyService;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

@Service
public class ClJsyServiceImpl implements ClJsyService{
    @Autowired
    private ClJsyMapper jsymapper;
    
	@Override
	public ApiResponse<String> findJsy(String sfzhm, String xm) {
		ClJsy jsy = jsymapper.findJzg(sfzhm, xm);
		RuntimeCheck.ifNull(jsy, "身份证或者姓名有误");
		String userInfo = JsonUtil.toJson(jsy);
		String token = JwtUtil.createWechatToken("jsy",userInfo);
//		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
//		request.setAttribute("type",jsy);
//		request.setAttribute("userInfo",userInfo);
		ApiResponse<String> res = new ApiResponse<>();
		res.setResult(token);
		return res;
	}

	@Override
	public ApiResponse<String> updatejsy(ClJsy jsy) {
		jsy.setZt(null);
		jsymapper.updateByPrimaryKeySelective(jsy);
		return ApiResponse.updateSuccess();
	}

	public ClJsy findById(String sfzhm){
		ClJsy findJzg = jsymapper.findById(sfzhm);
		return findJzg;
	}
}
