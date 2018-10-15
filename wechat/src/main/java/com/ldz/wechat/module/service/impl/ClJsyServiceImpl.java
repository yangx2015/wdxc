package com.ldz.wechat.module.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

import com.ldz.util.commonUtil.Des;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.ldz.util.bean.ApiResponse;
import com.ldz.util.bean.SimpleCondition;
import com.ldz.util.commonUtil.JsonUtil;
import com.ldz.util.commonUtil.JwtUtil;
import com.ldz.util.exception.RuntimeCheck;
import com.ldz.wechat.base.BaseServiceImpl;
import com.ldz.wechat.module.mapper.ClDdMapper;
import com.ldz.wechat.module.mapper.ClJsyMapper;
import com.ldz.wechat.module.model.ClDd;
import com.ldz.wechat.module.model.ClJsy;
import com.ldz.wechat.module.service.ClJsyService;

import tk.mybatis.mapper.common.Mapper;

@Service
public class ClJsyServiceImpl extends BaseServiceImpl<ClJsy,String> implements ClJsyService{
    @Autowired
    private ClJsyMapper jsymapper;
    @Autowired
	private ClDdMapper ddMapper;
	/**
	 * 通过驾驶员手机号、密码
	 * @param sjh	手机号
	 * @param pwd	驾驶员登录密码
	 * @return
	 */
	@Override
	public ApiResponse<Map<String,Object>> findJsy(String sjh, String pwd) {
		RuntimeCheck.ifBlank(sjh, "手机号必填");
	    RuntimeCheck.ifBlank(pwd, "密码必填");
		String pwdEncrypt="";
		//加密密码
		try {
			pwdEncrypt=Des.encrypt(pwd);
		} catch (Exception e1) {
			throw new RuntimeException("密码加密异常",e1);
		}
		ClJsy jsy = jsymapper.findJzg(sjh,pwdEncrypt);
		RuntimeCheck.ifNull(jsy, "账户或密码有误");

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


	private int getJsyGrade(String jsyId){
		SimpleCondition condition = new SimpleCondition(ClDd.class);
		condition.eq(ClDd.InnerColumn.sj,jsyId);
		List<ClDd> orders = ddMapper.selectByExample(condition);
		if (orders.size() == 0)return 0;
		orders = orders.stream().filter(p->p.getPjdj() != null).collect(Collectors.toList());
		float total = 0f;
		for (ClDd order : orders) {
			total += order.getPjdj();
		}
		return (int) (total/orders.size());
	}

	@Override
	public ApiResponse<String> updatejsy(ClJsy jsy) {
		String userId=getCurrentUser(true);
		jsy.setZt(null);
		jsy.setSfzhm(userId);
		jsymapper.updateByPrimaryKeySelective(jsy);
		return ApiResponse.updateSuccess();
	}

	@Override
	protected Mapper<ClJsy> getBaseMapper() {
		return jsymapper;
	}

	public ClJsy findById(String sfzhm){
		ClJsy findJzg = jsymapper.findById(sfzhm);
		return findJzg;
	}

	@Override
	public ApiResponse<Map<String, Object>> getInfo() {
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
		String userInfo = (String) request.getAttribute("userInfo");
		RuntimeCheck.authCheck(userInfo == null);

		ClJsy jsy = JsonUtil.toBean(userInfo,ClJsy.class);
		RuntimeCheck.authCheck(jsy == null);

		Map<String,Object> map = new HashMap<>();
		map.put("userInfo",userInfo);
		map.put("grade",getJsyGrade(jsy.getSfzhm()));
		return ApiResponse.success(map);
	}

	/**
	 * 驾驶员修改密码
	 * @param oldPwd	原密码
	 * @param newPwd	新密码
	 * @return
	 */
	@Override
	public ApiResponse<String> mdfPwd(String oldPwd, String newPwd){
		String userId=getCurrentUser(true);
		ClJsy jsy=findById(userId);
		if (jsy == null) return ApiResponse.fail("用户不存在");
		String newEncrypt;
		try {
			String encrypt = Des.encrypt(oldPwd);
			if (!encrypt.equals(jsy.getPwd())){
				return ApiResponse.fail("密码错误");
			}
			newEncrypt = Des.encrypt(newPwd);
		} catch (Exception e) {
			return ApiResponse.fail("加密失败");
		}
		jsy.setPwd(newEncrypt);
		this.update(jsy);
		return ApiResponse.success();
	}
}
