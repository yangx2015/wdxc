package com.ldz.wechat.service;

import com.ldz.util.bean.ApiResponse;
import com.ldz.wechat.bean.GpsInfo;

public interface InstructionService {

	/*
	 * 用于业务系统对终端发送指令
	 * 
	 */
	
	ApiResponse<String> sendinstruction(GpsInfo info);
}
