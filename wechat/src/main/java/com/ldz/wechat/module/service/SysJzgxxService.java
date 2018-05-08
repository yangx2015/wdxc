package com.ldz.wechat.module.service;

import com.ldz.util.bean.ApiResponse;
import com.ldz.wechat.module.model.SysJzgxx;

public interface SysJzgxxService {

	/*
	 * 通过身份证,姓名获取到教职工信息
	 */
	
	ApiResponse<SysJzgxx> findJzg(String idCard,String name);
}
