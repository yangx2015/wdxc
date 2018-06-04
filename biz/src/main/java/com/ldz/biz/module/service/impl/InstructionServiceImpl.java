package com.ldz.biz.module.service.impl;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

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
		String key = "sendInstruction-"+info.getDeviceId()+"-";
		boolean checkRedis = false;
		if ("12".equals(info.getCmdType())){
		    key += "photo";
			checkRedis = true;
        }else if ("11".equals(info.getCmdType())){
		    key += "video";
			checkRedis = true;
        }
		if (checkRedis){
			if (redisTemplate.hasKey(key)){
				return ApiResponse.fail("指令发送过于频繁");
			}
			redisTemplate.boundValueOps(key).set("", 1, TimeUnit.MINUTES);
		}


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
			//合并视频
			if (info.getCmdType().equals("13")) {
				String results = apiResponse.getResult();
				redisTemplate.boundListOps("BJ"+info.getDeviceId()).leftPush(results);
				return apiResponse;
			}
			//拍视频
			if (info.getCmdType().equals("11")) {
				String results = apiResponse.getResult();
				redisTemplate.boundListOps("SP"+info.getDeviceId()).leftPush(results);
				return apiResponse;
			}
			//拍照片
            if (info.getCmdType().equals("12")) {
            	String results = apiResponse.getResult();
				redisTemplate.boundListOps("ZP"+info.getDeviceId()).leftPush(results);
				return apiResponse;
			}

			ClZdgl clzd = mapper.selectByPrimaryKey(info.getDeviceId());
			//急加速灵敏度设置
			if (info.getCmdType().equals("02")) {
				clzd.setJslmd(info.getCmd());
				service.update(clzd);
				return apiResponse;
			}
			//碰撞灵敏度
			if (info.getCmdType().equals("20")) {
				clzd.setPzlmd(info.getCmd());
				service.update(clzd);
				return apiResponse;
			}
			//已废弃
			if (info.getCmdType().equals("30")) {
				clzd.setScms(info.getCmd());
				service.update(clzd);
				return apiResponse;
			}
			//终端数据上报地址
			if (info.getCmdType().equals("91")) {
				clzd.setCmd(info.getCmd());
				service.update(clzd);
				return apiResponse;
			}
			//上传视频模式
			if (info.getCmdType().equals("50")) {
				clzd.setSpscms(info.getCmd());
				service.update(clzd);
				return apiResponse;
			}


		} catch (Exception e) {
			e.printStackTrace();
		}
		return apiResponse;

	}

}
