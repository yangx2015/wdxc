package com.ldz.wechat.module.service.impl;

import com.ldz.util.bean.ApiResponse;
import com.ldz.util.bean.SimpleCondition;
import com.ldz.util.commonUtil.HttpUtil;
import com.ldz.util.commonUtil.JsonUtil;
import com.ldz.util.commonUtil.JwtUtil;
import com.ldz.wechat.module.mapper.ClXlMapper;
import com.ldz.wechat.module.mapper.SysJzgxxMapper;
import com.ldz.wechat.module.model.SysJg;
import com.ldz.wechat.module.model.SysJzgxx;
import com.ldz.wechat.module.service.JgService;
import com.ldz.wechat.module.service.MainService;
import com.ldz.wechat.module.service.SysJzgxxService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service("wxMainService")
public class MainServiceImpl  implements MainService {
	Logger accessLog = LoggerFactory.getLogger("access_info");

	@Autowired
	private SysJzgxxMapper jzgmapper;

	@Autowired
	private ClXlMapper entityMapper;

	@Autowired
	private SysJzgxxService jzgxxService;

	@Autowired
	private JgService jgService;


	@Override
	public ApiResponse<String> validateStudent(String mhtkt){
		String userId=getUserId(mhtkt);
		if(StringUtils.isEmpty(userId)||StringUtils.equals(userId,"null")){
			return ApiResponse.fail("身份验证失败");
		}
		ApiResponse<String> res = new ApiResponse<>();
		res.setCode(200);
		res.setMessage("验证通过");
		res.setResult(userId);
		return res;
	}

	/**
	 * 微信员工跳转
	 * @param mhtkt
	 * @return
	 */
	@Override
	public ApiResponse<String> validateStaff(String mhtkt){
		String userId=getUserId(mhtkt);
		if(StringUtils.isEmpty(userId)||StringUtils.equals(userId,"null")){
			return ApiResponse.fail("身份验证失败");
		}

		//1、查数据库，看看当前员工是否已经插入库。如果已经插入库，就直接做登录操作。
		SysJzgxx jzg=jzgxxService.findById("pk"+userId);
//		if(jzg!=null){
//			Date cjsj=jzg.getCjsj();
//			if((new Date().getTime()-cjsj.getTime())/(60*60*1000*24)>10){//如果这个教职工信息超过了10天，数据就需要再更新一下
//
//			}
//		}
		if(jzg==null){
			//将用户从视图里获取出来。
			List<Map<String,String>> userList=entityMapper.getUserView(userId,"教职工");
			if(userList==null||userList.size()<1){
				return ApiResponse.fail("从视图中获取数据失败，未找到数据");
			}
			Map<String,String> map=userList.get(0);
			String xh=map.get("XH");//学员
			if(StringUtils.isEmpty(xh)){
				xh=map.get("xh");
			}
			String name=map.get("XM");//姓名
			if(StringUtils.isEmpty(name)){
				name=map.get("xm");
			}
			String phone=map.get("SJHM");//手机号码
			if(StringUtils.isEmpty(phone)){
				phone=map.get("sjhm");
			}
			String jgCode=map.get("YXDM");//机构ID
			if(StringUtils.isEmpty(jgCode)){
				jgCode=map.get("yxdm");
			}
			String jgName=map.get("YXMC");//机构名称
			if(StringUtils.isEmpty(jgName)){
				jgName=map.get("yxmc");
			}
			String js=map.get("JS");//角色
			if(StringUtils.isEmpty(js)){
				js=map.get("js");
			}
			SimpleCondition simpleCondition = new SimpleCondition(SysJg.class);
			simpleCondition.eq(SysJg.InnerColumn.jgmc.name(),jgName);
			simpleCondition.setOrderByClause(SysJg.InnerColumn.jgdm.desc());
			List<SysJg> jgList=jgService.findByCondition(simpleCondition);
			if(jgList==null||jgList.size()<1){
				return ApiResponse.fail("未找到该用户所属的角色");
			}

			SysJzgxx obj=new SysJzgxx();
			obj.setId("pk"+xh);//ID
			obj.setXm(name);//姓名
			obj.setZjhm(xh);//手机号码
			obj.setJgdm(jgList.get(0).getJgdm());//机构ID
			obj.setJdmc(jgList.get(0).getJgmc());//机构名称
			obj.setSjhm(phone);
			obj.setCjsj(new Date());
			obj.setCjr(name);
			jzgmapper.insert(obj);
			jzg=obj;
		}
		jzg=jzgxxService.findById("pk"+userId);
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
	public String getUserId(String mhtkt){
		String url="http://weixintzzo.whu.edu.cn/auth/token?mhtkt="+mhtkt;
		String postJson = "";
		try {
			postJson = HttpUtil.get(url);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}

		accessLog.debug("请求接口：mhtkt:{}，接口返回的值:{}",mhtkt,postJson);
		Map<String,String> map= JsonUtil.toBean(postJson, HashMap.class);
		accessLog.debug("获取到的用户ID:{}",map.get("user"));
		String userId=map.get("user");
		if(StringUtils.isEmpty(userId)||StringUtils.equals(userId,"null")){
			userId="";
		}
		return userId;
	}
}
