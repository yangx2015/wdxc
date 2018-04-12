package com.ldz.wechat.service;

import com.ldz.sys.base.BaseService;
import com.ldz.util.bean.ApiResponse;
import com.ldz.wechat.model.yjfk;


public interface yjfkService extends BaseService<yjfk,String>{
	
	  ApiResponse<String> saveEntity(yjfk entity);
	  
	  ApiResponse<String> updateEntity(yjfk entity);
}
