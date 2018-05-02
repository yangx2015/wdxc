package com.ldz.sys.service.impl;

import com.ldz.sys.base.BaseServiceImpl;
import com.ldz.sys.base.LimitedCondition;
import com.ldz.sys.constant.Dict;
import com.ldz.sys.exception.RuntimeCheck;
import com.ldz.sys.mapper.*;
import com.ldz.sys.model.*;
import com.ldz.sys.service.JgService;
import com.ldz.sys.service.JsService;
import com.ldz.sys.service.YhService;
import com.ldz.util.bean.ApiResponse;
import com.ldz.util.bean.SimpleCondition;
import com.ldz.util.commonUtil.Des;
import com.ldz.util.commonUtil.EncryptUtil;
import org.apache.commons.lang.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.entity.Example;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class YhServiceImpl extends BaseServiceImpl<SysYh, String> implements YhService {
	Logger log = LogManager.getLogger(this);
	@Value("${resePwd:111111}")
	private String resePwd;
	@Autowired
	private SysClkPtyhMapper baseMapper;
	@Autowired
	private SysRsRoleBizMapper roleBizMapper;
	@Autowired
	private SysPtfwMapper bizMapper;
	@Autowired
	private SysRsRoleResourceMapper roleResourceMapper;
	@Autowired
	private SysResourceMapper resourceMapper;
	@Autowired
	private JsService roleService;
	@Autowired
	private JgService jgService;

	@Override
	protected Class<SysYh> getEntityCls(){
		return SysYh.class;
	}
	
	@Override
	protected Mapper<SysYh> getBaseMapper() {
		return baseMapper;
	}

	/**
	 * 新增用户
	 *
	 * @param user 用户
	 * @return 执行结果
	 */
	@Override
	public ApiResponse<String> addUser(SysYh user) {
		RuntimeCheck.ifBlank(user.getZh(),"账号不能为空");
		RuntimeCheck.ifBlank(user.getXm(),"姓名不能为空");
		RuntimeCheck.ifBlank(user.getJgdm(),"请选择机构");
		RuntimeCheck.ifBlank(user.getSjh(),"手机号不能为空");
		RuntimeCheck.ifFalse(StringUtils.isAlphanumeric(user.getZh()),"登陆名只能是数字和字母组成！");
		boolean exists = ifExists(SysYh.InnerColumn.zh.name(),user.getZh());
		RuntimeCheck.ifTrue(exists,"登陆名已存在，请更换别的登陆名！");
		SysJg org = jgService.findByOrgCode(user.getJgdm());
		RuntimeCheck.ifNull(org,"机构不存在");

		SysYh currentUser = getCurrentUser();
		user.setYhid(String.valueOf(idGenerator.nextId()));
		user.setMm(EncryptUtil.encryptUserPwd(user.getMm()));
		user.setCjr(currentUser.getYhid());
		user.setCjsj(new Date());
		user.setZt(Dict.UserStatus.VALID.getCode());
		baseMapper.insertSelective(user);
		return ApiResponse.success();
	}

	@Override
	public ApiResponse<String> saveEntity(SysYh entity) {
		RuntimeCheck.ifBlank(entity.getZh(),"请先输入登陆名！");
		RuntimeCheck.ifBlank(entity.getMm(),"请先输入登陆密码！");
		RuntimeCheck.ifFalse(StringUtils.isAlphanumeric(entity.getZh()),"登陆名只能是数字和字母组成！");

		SimpleCondition condition = new SimpleCondition(SysYh.class);
		condition.eq(SysYh.InnerColumn.zh.name(), entity.getZh());
		Integer count = this.countByCondition(condition);
		RuntimeCheck.ifTrue(count > 0,"登陆名已存在，请更换别的登陆名！");

		entity.setYhid(genId());
		entity.setMm(EncryptUtil.encryptUserPwd(entity.getMm()));
		entity.setZt(Dict.UserStatus.VALID.getCode());
		entity.setSjh(entity.getSjh().trim());
		entity.setCjsj(new Date());
		if (StringUtils.isEmpty(entity.getJgdm())){
			SysYh user = getCurrentUser();
			entity.setJgdm(user.getJgdm());
		}
		this.save(entity);
		return ApiResponse.saveSuccess();
	}

	/**
	 * 修改登录密码
	 *
	 * @param userId 用户id
	 * @param oldPwd 旧密码
	 * @param newPwd 新密码
	 * @return 操作结果
	 */
	@Override
	public ApiResponse<String> mdfPwd(String userId, String oldPwd, String newPwd) {
		SysYh user = baseMapper.selectByPrimaryKey(userId);
		String newEncrypt;
		if (user == null) return ApiResponse.fail("用户不存在");
		try {
			String encrypt = Des.encrypt(oldPwd);
			if (!encrypt.equals(user.getMm())){
				return ApiResponse.fail("密码错误");
			}
			newEncrypt = Des.encrypt(newPwd);
		} catch (Exception e) {
			log.error("加密失败 oldPwd={},newPwd={}",oldPwd,newPwd);
			return ApiResponse.fail("加密失败");
		}
		user.setMm(newEncrypt);
		baseMapper.updateByPrimaryKeySelective(user);
		return ApiResponse.success();
	}


	@Override
	public ApiResponse<List<SysFw>> getUserPermissions(SysYh user) {
		ApiResponse<List<SysFw>> result = new ApiResponse<>();
		// 获取角色
		List<String> roleIds = roleService.getUserRoleIds(user.getYhid());

		Example roleBizExample = new Example(SysRsRoleBiz.class);
		roleBizExample.and().andIn(SysRsRoleBiz.InnerColumn.roleId.name(),roleIds);
		List<SysRsRoleBiz> roleBizs = roleBizMapper.selectByExample(roleBizExample);
		if (roleBizs.size() == 0) return result;

		// 获取bizs
		Set<Long> bizIds = roleBizs.stream().map(SysRsRoleBiz::getBizId).collect(Collectors.toSet());
		Example bizExample = new Example(SysFw.class);
		bizExample.and().andIn(SysFw.InnerColumn.fwId.name(),bizIds);
		List<SysFw> bizs = bizMapper.selectByExample(bizExample);
		result.setResult(bizs);
		Map<String,SysFw> bizMap = bizs.stream().collect(Collectors.toMap(SysFw::getFwId, p->p));

		// 获取resources
		Example roleResourceExample = new Example(SysRsRoleResource.class);
		roleResourceExample.and().andIn(SysRsRoleResource.InnerColumn.roleId.name(),roleIds);
		List<SysRsRoleResource> roleResources = roleResourceMapper.selectByExample(roleResourceExample);
		if (roleResources.size() != 0){
			Set<Long> resourceIds = roleResources.stream().map(SysRsRoleResource::getResId).collect(Collectors.toSet());
			Example resourceExample = new Example(SysResource.class);
			resourceExample.and().andIn(SysResource.InnerColumn.resId.name(),resourceIds);
			List<SysResource> resources = resourceMapper.selectByExample(resourceExample);
			for (SysResource resource : resources) {
				SysFw biz = bizMap.get(resource.getResPid());
				if (biz == null) continue;
//				if (biz.getResourceList() == null){
//					List<SysResource> resourcesList = new ArrayList<>();
//					resourcesList.add(resource);
//					biz.setResourceList(resourcesList);
//				}else{
//					biz.getResourceList().add(resource);
//				}
			}
		}
		return result;
	}

	@Override
	public ApiResponse<String> updateEntity(SysYh user) {
		RuntimeCheck.ifBlank(user.getZh(),"账号不能为空");
		RuntimeCheck.ifBlank(user.getXm(),"姓名不能为空");
		RuntimeCheck.ifBlank(user.getJgdm(),"请选择机构");
		RuntimeCheck.ifBlank(user.getSjh(),"手机号不能为空");
		RuntimeCheck.ifFalse(StringUtils.isAlphanumeric(user.getZh()),"登陆名只能是数字和字母组成！");
		SysJg org = jgService.findByOrgCode(user.getJgdm());
		RuntimeCheck.ifNull(org,"机构不存在");

		LimitedCondition condition= new LimitedCondition(SysYh.class);
		condition.eq(SysYh.InnerColumn.zh, user.getZh());
		List<SysYh> list=baseMapper.selectByExample(condition);

		if(list==null||list.size()==0){
			RuntimeCheck.ifTrue(true,"该账户不存在，或该账户不属于本机构，您无法修改！");
		}

		baseMapper.updateByPrimaryKeySelective(user);
		return updateSession(user);
	}

	@Override
	public ApiResponse<String> updateSession(SysYh user) {
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
		request.setAttribute("userInfo",user);
		request.setAttribute("orgCode",user.getJgdm());
		return ApiResponse.success();
	}

	/**
	 * 重置密码
	 * @param userId
	 * @return
	 */
	@Override
	public ApiResponse<String> resetPassword(String userId){
		SysYh sysUser = getCurrentUser(true);
		RuntimeCheck.ifTrue(StringUtils.equals(sysUser.getYhid(),userId),"您不能为本人重置密码，请联系管理员进行重置密码。");
		SysYh user = baseMapper.selectByPrimaryKey(userId);
		if (user == null) return ApiResponse.fail("用户不存在");
		RuntimeCheck.ifFalse(user.getJgdm().indexOf(sysUser.getJgdm())==0,"您不能为非本机构的人员重置密码");
		String newEncrypt=null;
		try {
			newEncrypt = Des.encrypt(resePwd);
		} catch (Exception e) {
			log.error("加密失败 newEncrypt={}",resePwd);
			return ApiResponse.fail("加密失败");
		}

		user.setMm(newEncrypt);
		baseMapper.updateByPrimaryKeySelective(user);
		return ApiResponse.success();
	}
}