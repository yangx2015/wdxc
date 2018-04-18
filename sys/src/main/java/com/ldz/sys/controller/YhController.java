
package com.ldz.sys.controller;

import com.ldz.sys.base.BaseController;
import com.ldz.sys.base.BaseService;
import com.ldz.sys.constant.Dict;
import com.ldz.sys.exception.RuntimeCheck;
import com.ldz.sys.model.SysFw;
import com.ldz.sys.model.SysYh;
import com.ldz.sys.service.YhService;
import com.ldz.util.bean.ApiResponse;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 用户管理
 */
@RestController
@RequestMapping("/api/yh")
public class YhController extends BaseController<SysYh, String> {

	@Autowired
	private YhService userService;
	@Override
	protected BaseService<SysYh, String> getBaseService() {
		return userService;
	}
	
	@Override
	public ApiResponse<String> save(SysYh entity) {
//		entity.setLx(Dict.UserType.ADMIN.getCode());
		return this.userService.saveEntity(entity);
	}

	@RequestMapping(value = "mdfPwd",method = RequestMethod.POST)
	public ApiResponse<String> mdfPwd(@RequestParam(name = "oldPwd")String oldPwd,
									  @RequestParam(name = "newPwd")String newPwd,
									  @RequestParam(name = "secPwd")String secPwd){
		RuntimeCheck.ifTrue((
				StringUtils.isEmpty(oldPwd) ||
				StringUtils.isEmpty(newPwd) ||
				StringUtils.isEmpty(secPwd)),"请输入密码");
		RuntimeCheck.ifTrue(!newPwd.equals(secPwd),"两次输入密码不一致");
		SysYh user = getCurrentUser();
		return userService.mdfPwd(user.getYhid(),oldPwd,newPwd);
	}



	@RequestMapping(value = "getUserPermissions",method = RequestMethod.POST)
	public ApiResponse<List<SysFw>> getUserPermissions(){
		SysYh user = getCurrentUser();
		return userService.getUserPermissions(user);
	}
}
