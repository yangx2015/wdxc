package com.ldz.wechat.module.service.impl;

import com.ldz.util.bean.SimpleCondition;
import com.ldz.util.commonUtil.JsonUtil;
import com.ldz.util.commonUtil.JwtUtil;
import com.ldz.wechat.module.mapper.ClDdMapper;
import com.ldz.wechat.module.model.ClDd;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class ClJsyServiceImpl implements ClJsyService{
    @Autowired
    private ClJsyMapper jsymapper;
    @Autowired
	private ClDdMapper ddMapper;
    
	@Override
	public ApiResponse<Map<String,Object>> findJsy(String sfzhm, String xm) {
		ClJsy jsy = jsymapper.findJzg(sfzhm, xm);
		RuntimeCheck.ifNull(jsy, "身份证或者姓名有误");
		String userInfo = JsonUtil.toJson(jsy);
		String token = JwtUtil.createWechatToken("jsy",userInfo);
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
		request.setAttribute("type","jsy");
		request.setAttribute("orgCode",jsy.getJgdm());
		request.setAttribute("userInfo",userInfo);
		Map<String,Object> map = new HashMap<>();
		map.put("token",token);
		map.put("userInfo",userInfo);
		map.put("grade",getJsyGrade(jsy.getSfzhm()));
		return ApiResponse.success(map);
	}

	private float getJsyGrade(String jsyId){
		SimpleCondition condition = new SimpleCondition(ClDd.class);
		condition.eq(ClDd.InnerColumn.sj,jsyId);
		List<ClDd> orders = ddMapper.selectByExample(condition);
		if (orders.size() == 0)return 0F;
		orders = orders.stream().filter(p->p.getPjdj() != null).collect(Collectors.toList());
		float total = 0f;
		for (ClDd order : orders) {
			total += order.getPjdj();
		}
		return total/orders.size();
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
