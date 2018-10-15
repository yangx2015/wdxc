package com.ldz.wechat.module.service.impl;

import com.ldz.util.bean.ApiResponse;
import com.ldz.util.bean.SimpleCondition;
import com.ldz.util.commonUtil.Des;
import com.ldz.util.commonUtil.JsonUtil;
import com.ldz.util.commonUtil.JwtUtil;
import com.ldz.util.commonUtil.StringDivUtils;
import com.ldz.util.exception.RuntimeCheck;
import com.ldz.wechat.base.BaseServiceImpl;
import com.ldz.wechat.module.mapper.SysJzgxxMapper;
import com.ldz.wechat.module.model.SysJzgxx;
import com.ldz.wechat.module.service.SysJzgxxService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import tk.mybatis.mapper.common.Mapper;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Service
public class SysJzgxxServiceImpl extends BaseServiceImpl<SysJzgxx,String> implements SysJzgxxService {

	@Autowired
	private SysJzgxxMapper jzgmapper;
	@Autowired
	private StringRedisTemplate redisDao;

	@Override
	protected Mapper<SysJzgxx> getBaseMapper() {
		return jzgmapper;
	}

	@Override
	protected Class<?> getEntityCls(){
		return SysJzgxx.class;
	}

	@Override
	public ApiResponse<String> findJzg(String zjhm, String pwd) {
		RuntimeCheck.ifBlank(zjhm, "工号不为空");
		RuntimeCheck.ifBlank(pwd, "密码不为空");
		String pwdEncrypt="";
		//加密密码
		try {
			pwdEncrypt= Des.encrypt(pwd);
		} catch (Exception e1) {
			throw new RuntimeException("密码加密异常",e1);
		}
		SysJzgxx jzg = jzgmapper.findJzg(pwdEncrypt,zjhm);
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

	public  ApiResponse<Map<String,Object>> getUserInfo(){
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
		Map<String,Object> map = new HashMap<>();
		String type = (String) request.getAttribute("type");
		String userInfo = (String) request.getAttribute("userInfo");
		RuntimeCheck.ifTrue((StringUtils.isEmpty(type) || StringUtils.isEmpty(userInfo)) && true,"当前登录用户未空！");
		if ("jzg".equals(type)){
			SysJzgxx jzg = JsonUtil.toBean(userInfo,SysJzgxx.class);
			RuntimeCheck.ifNull(jzg,"未找到教职工信息");
			map.put("userInfo",jzg);
		}else{
			RuntimeCheck.ifTrue(true,"您不属于教职工");
		}
		return ApiResponse.success(map);

	}

	/**
	 * 修改密码
	 * @param oldPwd	原密码
	 * @param newPwd	新密码
	 * @return
	 */
	@Override
	public ApiResponse<String> mdfPwd(String oldPwd, String newPwd){
		String userId=getCurrentUser(true);
		SysJzgxx jsy=findById(userId);
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

	/**
	 * 下发手机号码
	 * @param dn
	 * @return
	 */
	@Override
	public ApiResponse<String> updateDnSendSMS(String dn){
		String userId=getCurrentUser(true);
		SimpleCondition condition = new SimpleCondition(SysJzgxx.class);
		condition.and().andEqualTo(SysJzgxx.InnerColumn.sjhm.name() , dn);
		condition.setOrderByClause(" ID DESC");
		List<SysJzgxx> jzgList = this.findByCondition(condition);
		if(jzgList!=null&&jzgList.size()>0){
			if(StringUtils.equals(jzgList.get(0).getSjhm(),userId)){
				return ApiResponse.fail("您已绑定了该手机号码，无需再次绑定");
			}else{
				return ApiResponse.fail("该手机号码已被其它用户绑定，暂时不能再次绑定");
			}
		}
//		生成手机验证码
		String identifyingCode= StringDivUtils.getSix();//获取验证码
		boolean sendType=false;
//		sendType=sendSMS(dn,identifyingCode); // todo 下发短信
		sendType=true;
		if(sendType){
			//将用户的ID放到redis中。  appUserLoginRegister
			redisDao.boundValueOps("updateDnSendSms"+userId+"_"+dn).set(identifyingCode, 5, TimeUnit.MINUTES);//设备邀请码，为10分钟过期
			return  ApiResponse.success(redisDao.boundValueOps("updateDnSendSms"+userId+"_"+dn).get());// todo 调试上生产环境时，这里需要删除
//			return ApiResponse.success();
		}else{
			return  ApiResponse.fail("短信下发失败");
		}
	}

	/**
	 * 更新手机号码
	 * @param dn			手机号码
	 * @param pollcode		验证码
	 * @return
	 */
	@Override
	public ApiResponse<String> updateDn(String dn, String pollcode){
		String userId=getCurrentUser(true);//教职员ID
		String identifyingCode=redisDao.boundValueOps("updateDnSendSms"+userId+"_"+dn).get();
		RuntimeCheck.ifBlank(identifyingCode,"验证码错误");
		RuntimeCheck.ifFalse(StringUtils.equals(pollcode,identifyingCode),"验证码错误");
		SysJzgxx jzgxx=new SysJzgxx();
		jzgxx.setId(userId);
		jzgxx.setSjhm(dn);
		this.update(jzgxx);
		return ApiResponse.success();
	}
}
