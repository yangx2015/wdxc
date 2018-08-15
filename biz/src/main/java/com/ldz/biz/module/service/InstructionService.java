package com.ldz.biz.module.service;

import com.ldz.biz.module.bean.GpsInfo;
import com.ldz.biz.module.model.ClZdgl;
import com.ldz.util.bean.ApiResponse;

public interface InstructionService {

	/*
	 * 用于业务系统对终端发送指令
	 * 
	 */
	
	ApiResponse<String> sendinstruction(GpsInfo info);

    ApiResponse<String> batchUpdate(GpsInfo info, ClZdgl zdgl);
}
