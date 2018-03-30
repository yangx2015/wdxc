package com.ldz.biz.module.service.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;

import com.ldz.biz.module.bean.GpsInfo;
import com.ldz.biz.module.service.InstructionService;
import com.ldz.util.bean.ApiResponse;
import com.ldz.util.commonUtil.HttpUtil;
import com.ldz.util.commonUtil.JsonUtil;
@Service
public class InstructionServiceImpl implements InstructionService {



	@SuppressWarnings("unchecked")
	@Override
	public ApiResponse<String> sendinstruction(GpsInfo info) {

		// 临时测试用的url
		String url = "http://weicy.natapp1.cc/api/push/carcmd";
		
		String postEntity = JsonUtil.toJson(info);
		String result = "";
		ApiResponse<String> apiResponse =null;
		try {
			Map<String, String> postHeaders = new HashMap<String, String>();
			postHeaders.put("Content-Type", MediaType.APPLICATION_JSON_VALUE);
			result = HttpUtil.postJson(url, postHeaders, postEntity);
			apiResponse=(ApiResponse<String>)JsonUtil.toBean(result, ApiResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return apiResponse;

	}

}
