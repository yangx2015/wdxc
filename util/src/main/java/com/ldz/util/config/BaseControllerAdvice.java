package com.ldz.util.config;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ldz.util.bean.ApiResponse;

/**
 * 基础Controller类
 * @author 李彬彬
 *
 */
@SuppressWarnings("rawtypes")
@RestControllerAdvice
public class BaseControllerAdvice {
	
	Logger log = LoggerFactory.getLogger("error_info");
	
	/*@Bean
	public ObjectMapper jsonMapper(){
	    ObjectMapper objectMapper = new ObjectMapper();
	    // 日期类型转换格式
	    objectMapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));
	    // 忽略当接收json字符串中没有bean结构中的字段时抛出异常问题
	    objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
	    
	    return objectMapper;
	}*/
	
	/**
	 * Controller层数据异常全局扑捉
	 * @param ex
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@ExceptionHandler(Exception.class)
    public @ResponseBody ApiResponse handleUncaughtException(Exception ex, HttpServletRequest request, HttpServletResponse response) throws Exception {
		String context = request.getRequestURI();
		ObjectMapper mapper = new ObjectMapper();
		log.error("请求发生异常，来源路径["+context+"]，请求数据["+mapper.writeValueAsString(request.getParameterMap())+"]", ex);
		
		ApiResponse api = new ApiResponse();
		api.setCode(ApiResponse.FAILED);
		api.setMessage("数据处理异常！");
		return api;
    }
}
