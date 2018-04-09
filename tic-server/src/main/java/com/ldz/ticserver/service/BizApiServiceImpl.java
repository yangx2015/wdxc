package com.ldz.ticserver.service;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Executor;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMethod;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ldz.util.bean.RequestCommonParamsDto;
import com.ldz.util.commonUtil.HttpUtil;

@Service("bizApiService")
public class BizApiServiceImpl implements BizApiService{
	
	Logger accessLog = LoggerFactory.getLogger("access_info");
	
	Logger errorLog = LoggerFactory.getLogger("error_info");

	//设备状态更新接口
	@Value("${interface.status}")
	private String statusApi;
	//设备事件更新接口
	@Value("${interface.event}")
	private String eventApi;
	//设备GPS上报处理接口
	@Value("${interface.gps}")
	private String gpsApi;
	//文件上传的数据交互接口
	@Value("${interface.fileapi}")
	private String fileApi;
	
	
	
	@Autowired
	private Executor executor;
	
	//测试上传设备状态接口
	@Override
	public void pushData(RequestCommonParamsDto dto){
		pushDataApi(eventApi,dto);
	}

	@Override
	public void pushFileData(RequestCommonParamsDto dto) {
		// TODO Auto-generated method stub
		pushDataApi(fileApi,dto);
	}
	
	private void pushDataApi(String serverUrl,RequestCommonParamsDto dto){
		if (StringUtils.isNotEmpty(serverUrl)){
			final String[] apis = serverUrl.split(";");
			System.err.println(apis.length);
			for (int i=0; i<apis.length; i++){
				final String apiUrl = apis[i];
				//向所有配置接口写数据
				System.err.println(apiUrl);
				if (StringUtils.isNotEmpty(apiUrl)){
					//使用独立线程去执行接口数据写入，不要影响tic-server主线程接收数据
					executor.execute(new Runnable() {
						@Override
						public void run() {
							//具体写入数据bean对象，数据库定义出来以后，会变为具体对象
							ObjectMapper mapper = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
							String paramJson = "";
							try {
								//将bean对象转成json格式字符串进行数据传输
								paramJson = mapper.writeValueAsString(dto);
								Map<String, String> params = new HashMap<String, String>();
								params.put("content-type", "application/json");
								//发送数据请求
								String result = HttpUtil.postJson(apiUrl, params, paramJson);
								//数据本地日志记录
								accessLog.debug("请求数据["+paramJson+"],接口响应["+result+"]");
							} catch (Exception e) {
								errorLog.error(serverUrl+"接口请求异常", e);
							}
						}
					});
				}
			}
		}
	}
	
}
