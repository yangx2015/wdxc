package com.ldz.job.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;

import com.ldz.job.bean.GpsInfo;
import com.ldz.job.mapper.ClZdglMapper;
import com.ldz.job.model.ClZdgl;
import com.ldz.job.service.ClZdglService;
import com.ldz.util.bean.ApiResponse;
import com.ldz.util.commonUtil.HttpUtil;
import com.ldz.util.commonUtil.JsonUtil;

@Service
public class ClZdglServiceImpl implements ClZdglService {


	private static final Logger log = LoggerFactory.getLogger(ClZdglServiceImpl.class);

	private String url = "http://47.98.39.45:8080/tic-server/api/push/checkOnlin/";

	private String gpsSaveUrl="http://47.98.39.45:8080/biz/pub/gps/save";

	@Autowired
	private ClZdglMapper clZdglMapper;



	@SuppressWarnings("unchecked")
	@Override
	public ApiResponse<String> checkOnline() {

				// 获取所有正常的设备
				ClZdgl clZdgl = new ClZdgl();
				clZdgl.setZt("00");
				List<ClZdgl> gpslist = clZdglMapper.select(clZdgl);
/*
				List<String> zubhList = gpslist.stream().filter(s -> StringUtils.isNotEmpty(s.getZdbh()))
						.map(ClZdgl::getZdbh).collect(Collectors.toList());*/

				Map<String, ClZdgl> collect = gpslist.stream().filter(s -> StringUtils.isNotEmpty(s.getZdbh()))
						.collect(Collectors.toMap(ClZdgl::getZdbh, ClZdgl -> ClZdgl));

				Set<String> keySet = collect.keySet();


		for (String zdbh : keySet) {

			String string = HttpUtil.get(url + zdbh);
			ApiResponse<String> bean = JsonUtil.toBean(string, ApiResponse.class);

			if (bean.getCode() != 200) {
				ClZdgl clZdgl2 = collect.get(zdbh);
				clZdgl2.setZxzt("20");
				clZdglMapper.updateByPrimaryKeySelective(clZdgl2);
				//并将离线消息通知到gps上传
				  ApiResponse<String> senML = senML(zdbh);
				  if (senML.getCode()==200) {
					  log.info("上传离线信息到服务器成功终端编号为:"+zdbh);
				}

				log.info("更新了一条正常的设备状态终端编号为:"+zdbh);
			}

		}
		return ApiResponse.success();

	}

	@SuppressWarnings("unchecked")
	public ApiResponse<String> senML(String zdbh) {
		GpsInfo gpsinfo = new GpsInfo();
		gpsinfo.setDeviceId(zdbh);
		gpsinfo.setEventType("80");

		String postEntity = JsonUtil.toJson(gpsinfo);
		ApiResponse<String> apiResponse = null;
			Map<String, String> postHeaders = new HashMap<String, String>();
			postHeaders.put("Content-Type", MediaType.APPLICATION_JSON_VALUE);
			try {
				 String postJson = HttpUtil.postJson(gpsSaveUrl, postHeaders, postEntity);
				 apiResponse=(ApiResponse<String>)JsonUtil.toBean(postJson, ApiResponse.class);
			} catch (Exception e) {
				e.printStackTrace();
			}
		return apiResponse;
	}

	public static void main(String[] args) {
		 String url1= "http://47.98.39.45:8080/tic-server/api/push/checkOnlin/";
		 String aString="asdad";
		 System.out.println(url1+aString);

	}
}
