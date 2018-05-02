package com.ldz.sys.base;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.ldz.sys.exception.RuntimeCheckException;
import com.ldz.util.bean.ApiResponse;
import com.ldz.util.commonUtil.JsonUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 基础Controller类
 * @author 李彬彬
 *
 */
@SuppressWarnings("rawtypes")
@RestControllerAdvice
public class BaseControllerAdvice {
	
	Logger log = LoggerFactory.getLogger("error_info");


	@ExceptionHandler(RuntimeCheckException.class)
	@ResponseBody
	public ApiResponse<String> serverExceptionHandler(HttpServletRequest request,Exception e) throws JsonProcessingException {
		log.error("请求参数异常，来源路径["+request.getRequestURI()+"]，请求数据["+ JsonUtil.toJson(request.getParameterMap())+"]", e);
		return ApiResponse.fail(e.getMessage());
	}
	/**
	 * Controller层数据异常全局扑捉
	 * @param ex
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@ExceptionHandler(Exception.class)
    public @ResponseBody
	ApiResponse handleUncaughtException(Exception ex, HttpServletRequest request, HttpServletResponse response) throws Exception {
		log.error("请求发生异常，来源路径["+request.getRequestURI()+"]，请求数据["+ JsonUtil.toJson(request.getParameterMap())+"]", ex);
		return ApiResponse.fail("未知错误");
    }


}
