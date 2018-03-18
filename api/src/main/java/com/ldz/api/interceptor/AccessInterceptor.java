//package com.ldz.api.interceptor;
//
//import java.io.IOException;
//import java.util.Arrays;
//import java.util.List;
//
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//
//import org.apache.commons.lang.StringUtils;
//import org.springframework.data.redis.core.StringRedisTemplate;
//import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
//
//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.ldz.sys.bean.Permission;
//import com.ldz.sys.constant.Dict;
//import com.ldz.sys.model.SysUser;
//import com.ldz.sys.service.GnService;
//import com.ldz.sys.service.YhService;
//import com.ldz.util.bean.ApiResponse;
//import com.ldz.util.commonUtil.JwtUtil;
//import com.ldz.util.spring.SpringContextUtil;
//
//import lombok.extern.slf4j.Slf4j;
//
///**
// * 访问接口控制
// * @author 李彬彬
// *
// */
//@Slf4j
//public class AccessInterceptor extends HandlerInterceptorAdapter {
//
//	private GnService resourceService;
//
//	private YhService userService;
//
//	private StringRedisTemplate redisDao;
//
//	private List<String> whiteList;
//
//	public AccessInterceptor() {
//	}
//
//	public AccessInterceptor(StringRedisTemplate redisTemp) {
//		this.resourceService = SpringContextUtil.getBean(GnService.class);
//		this.userService = SpringContextUtil.getBean(YhService.class);
//		this.redisDao = redisTemp;
//		this.whiteList = Arrays.asList("/api/resource/getOnlineUserPermission","/api/user/getAll");
//
//	}
//
//	@Override
//	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
//		//查看请求类型
//		String method = request.getMethod();
//		if (method.equals("OPTIONS")){
//			//如果收到的是跨域预请求消息，直接响应，返回true，以便后续跨域请求成功
//			return true;
//		}
//
//		//访问权限值
////		String userid = "348561383694008320";
////		String token = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ3Y3BtcyIsImF1ZCI6IndjcG1zIiwibG9naW5OYW1lIjoiYWRtaW4iLCJpc3MiOiJ3Y3BtcyIsInVzZXJJZCI6IjM0ODU2MTM4MzY5NDAwODMyMCJ9.TjjF-kfmSnnJYIV4y5XR1C00rqOv3YxkeAB1sAvi80g";
//		String userid = request.getHeader("user_id");
//		String token = request.getHeader("token");
//		String url = request.getHeader("url");
//
//		if (token == null) token = request.getParameter("token");
//		if (userid == null) userid = request.getParameter("user_id");
//		if (StringUtils.isEmpty(userid) || StringUtils.isEmpty(token)){
//			return false;
//		}
//		log.debug("访问地址[{}], 请求openid[{}],请求token[{},header请求地址[{}]]", request.getRequestURI(), userid, token, url);
//
//		// 验证用户状态
//		SysUser user = userService.findById(userid);
//		if (!Dict.UserStatus.VALID.getCode().equals(user.getUserStatus())){
//			return false;
//		}
//		try{
//			//验证访问者是否合法
//			String userId = JwtUtil.getClaimAsString(token,"userId");
//			log.debug("userId="+userId);
//			if (!userid.equals(userId)){
//				return false;
//			}
//			String value = redisDao.boundValueOps(userid).get();
//			log.debug("value="+value);
//			log.debug("token="+token);
////			if (StringUtils.isEmpty(value) || !value.equals(token)){
////				return false;
////			}
//			request.setAttribute("userInfo", user);
//			// {"userId":"348561383694008320","loginName":"admin","userType":"00","userPhone":"133 4444 3343","userName":"管理员","headImg":"userHead/截屏_20180115_103307.jpg","userStatus":"01","deviceCount":0}
////			String userInfoJson = "{\"userId\":\"348561383694008320\",\"loginName\":\"admin\",\"userType\":\"00\",\"userPhone\":\"133 4444 3343\",\"userName\":\"管理员\",\"headImg\":\"userHead/截屏_20180115_103307.jpg\",\"userStatus\":\"01\",\"deviceCount\":0}";
//			String userInfoJson = redisDao.boundValueOps(userid+"-userInfo").get();
//			ObjectMapper mapper = new ObjectMapper();
//			SysUser userInfo = mapper.readValue(userInfoJson, SysUser.class);
//			if (!whiteList.contains(request.getRequestURI()) && !"su".equals(userInfo.getUserType())){ // su 用户可访问所有权限
//				if (!checkPermission(userInfo,request)){
//					request.getRequestDispatcher("/403").forward(request,response);
//					return true;
//				}
//			}
//		}catch(Exception e){
//			return false;
//		}
//
//		return super.preHandle(request, response, handler);
//	}
//
//	private boolean checkPermission(SysUser userInfo,HttpServletRequest request) throws IOException {
//		ApiResponse<List<Permission>> peremissionsRes =  resourceService.getOnlineUserPermission(userInfo.getUserId());
//		if (findPermission(peremissionsRes.getResult(),request.getRequestURI()) == null){
//			return false;
//		}
//		return true;
//	}
//
//	private Permission findPermission(List<Permission> permissions,String url){
//		Permission p = null;
//		for (Permission permission : permissions) {
//			if (permission.getApiPrefix() != null && url.startsWith(permission.getApiPrefix())){
//				return permission;
//			}
//			if (permission.getChildren() != null && permission.getChildren().size() != 0){
//				p = findPermission(permission.getChildren(),url);
//				if (p != null) return p;
//			}
//		}
//		return null;
//	}
//}
