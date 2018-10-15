package com.ldz.wechat.module.service;

import com.ldz.util.bean.ApiResponse;
import com.ldz.wechat.base.BaseService;
import com.ldz.wechat.module.model.SysJzgxx;

import java.util.Map;

public interface SysJzgxxService extends BaseService<SysJzgxx,String> {

	/*
	 * 通过身份证,姓名获取到教职工信息
	 */
	ApiResponse<String> findJzg(String idCard,String name);

	SysJzgxx findById(String id);

    ApiResponse<Map<String,Object>> getUserInfo();
	/**
	 * 教职工修改密码
	 * @return
	 */
	ApiResponse<String> mdfPwd(String oldPwd, String newPwd);

	ApiResponse<String> updateDnSendSMS(String dn);

    ApiResponse<String> updateDn(String dn, String pollcode);
}
