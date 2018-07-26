package com.ldz.biz.module.service.impl;

import com.ldz.biz.module.bean.GpsInfo;
import com.ldz.biz.module.mapper.ClZdglMapper;
import com.ldz.biz.module.model.ClZdgl;
import com.ldz.biz.module.service.InstructionService;
import com.ldz.biz.module.service.ZdglService;
import com.ldz.sys.model.SysJg;
import com.ldz.sys.service.JgService;
import com.ldz.util.bean.ApiResponse;
import com.ldz.util.commonUtil.HttpUtil;
import com.ldz.util.commonUtil.JsonUtil;
import com.ldz.util.redis.RedisTemplateUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Service
public class InstructionServiceImpl  implements InstructionService {
	@Autowired
	private ClZdglMapper mapper;
	@Autowired
	private ZdglService service;
	@Autowired
	private JgService jgService;
    @Autowired
    private RedisTemplateUtil redisTemplate;

	@Value("${apiurl}")
    private String url;
	Logger errorLog = LoggerFactory.getLogger("error_info");
	Logger accessLog = LoggerFactory.getLogger("access_info");




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

			result = HttpUtil.postJson(url +"/push/carcmd", postHeaders, postEntity);
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

	@Override
	public ApiResponse<String> batchUpdate(GpsInfo info,String jgdm) {


		// 查询当前用户所在机构及其子机构的设备编号
		List<SysJg> sysJgs = jgService.findLike(SysJg.InnerColumn.jgdm, jgdm + "%");
		List<String> jgdms = sysJgs.stream().map(SysJg::getJgdm).collect(Collectors.toList());
		List<ClZdgl> zdgls = service.findIn(ClZdgl.InnerColumn.jgdm, jgdms);
		Map<String, String> postHeaders = new HashMap<String, String>();
		postHeaders.put("Content-Type", MediaType.APPLICATION_JSON_VALUE);
		accessLog.info("开启线程池，升级终端");
		ExecutorService pool = Executors.newSingleThreadExecutor();
		for(ClZdgl clZdgl : zdgls){
			pool.submit(new Runnable() {
				@Override
				public void run() {
					try {
						info.setDeviceId(clZdgl.getZdbh());
						String finalPostEntity = JsonUtil.toJson(info);
						HttpUtil.postJson(url + "/push/carcmd", postHeaders, finalPostEntity);
					} catch (Exception e) {
						errorLog.info("设备升级请求异常,设备编号：{}",clZdgl.getZdbh(),e );
					}
				}
			});
		}
		pool.shutdown();
		return ApiResponse.success();

	}

}
