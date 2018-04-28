package com.ldz.biz.module.service.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;

import com.ldz.biz.module.bean.GpsInfo;
import com.ldz.biz.module.mapper.ClZdglMapper;
import com.ldz.biz.module.model.ClZdgl;
import com.ldz.biz.module.service.InstructionService;
import com.ldz.biz.module.service.ZdglService;
import com.ldz.util.bean.ApiResponse;
import com.ldz.util.commonUtil.HttpUtil;
import com.ldz.util.commonUtil.JsonUtil;
import com.ldz.util.redis.RedisTemplateUtil;
@Service
public class InstructionServiceImpl  implements InstructionService {
	@Autowired
	private ClZdglMapper mapper;
	@Autowired
	private ZdglService service;
    @Autowired
    private RedisTemplateUtil redisTemplate;
	
	@Value("${ticserver.url}")
    private String url;
	


	

	@SuppressWarnings("unchecked")
	@Override
	public ApiResponse<String> sendinstruction(GpsInfo info) {



		String postEntity = JsonUtil.toJson(info);
		String result = "";
		ApiResponse<String> apiResponse =null;
		try {
			Map<String, String> postHeaders = new HashMap<String, String>();
			postHeaders.put("Content-Type", MediaType.APPLICATION_JSON_VALUE);
			result = HttpUtil.postJson(url, postHeaders, postEntity);
			apiResponse=(ApiResponse<String>)JsonUtil.toBean(result, ApiResponse.class);
			
			if (apiResponse.getCode()!=200) {
				return apiResponse;
			}
			if (info.getCmdType().equals("13")) {
				String results = apiResponse.getResult();
				redisTemplate.boundListOps("BJ"+info.getDeviceId()).leftPush(results);
				
			}
			
			ClZdgl clzd = mapper.selectByPrimaryKey(info.getDeviceId());
			
			if (info.getCmdType().equals("02")) {
				clzd.setJslmd(info.getCmd());
			}
			if (info.getCmdType().equals("20")) {
				clzd.setPzlmd(info.getCmd());
			}
			if (info.getCmdType().equals("30")) {
				clzd.setScms(info.getCmd());
			}
			if (info.getCmdType().equals("91")) {
				clzd.setCmd(info.getCmd());
			}
			if (info.getCmdType().equals("50")) {
				clzd.setSpscms(info.getCmd());
			}
			
			service.update(clzd);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return apiResponse;

	}

}
