package com.ldz.wechat.module.service;

import com.ldz.util.bean.ApiResponse;
import com.ldz.wechat.base.BaseService;
import com.ldz.wechat.module.model.ClJsy;

import java.util.Map;

public interface ClJsyService extends BaseService<ClJsy,String> {

	/*
	 * 通过驾驶员手机号、密码
	 */
	ApiResponse<Map<String,Object>> findJsy(String sfzhm, String pwd);
	/*
	 * 修改驾驶员信息
	 */
	ApiResponse<String> updatejsy(ClJsy jsy);

	ClJsy findById(String id);

    ApiResponse<Map<String, Object>> getInfo();

	ApiResponse<String> mdfPwd(String oldPwd, String newPwd);
}
