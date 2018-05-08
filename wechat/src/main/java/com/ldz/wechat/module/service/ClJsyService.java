package com.ldz.wechat.module.service;

import com.ldz.util.bean.ApiResponse;
import com.ldz.wechat.module.model.ClJsy;

public interface ClJsyService {

	/*
	 * 通过驾驶员身份证号码,驾驶员姓名获取驾驶员
	 */
	ApiResponse<ClJsy> findJsy(String sfzhm,String xm);
	/*
	 * 修改驾驶员信息
	 */
	ApiResponse<String> updatejsy(ClJsy jsy);
}
