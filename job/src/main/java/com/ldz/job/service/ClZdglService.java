package com.ldz.job.service;

import com.ldz.util.bean.ApiResponse;

public interface ClZdglService {

	/*
	 * 根据车辆的终端编号查询 设备在线状态，批量的将设备状态更新
	 */
	ApiResponse<String> checkOnline();
}
