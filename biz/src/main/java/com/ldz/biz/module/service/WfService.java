package com.ldz.biz.module.service;

import org.springframework.web.bind.annotation.PathVariable;

import com.ldz.biz.module.model.ClWf;
import com.ldz.sys.base.BaseService;
import com.ldz.util.bean.ApiResponse;

public interface WfService extends BaseService<ClWf, String>{
    
	ApiResponse<String> saveEntity(ClWf entity);
	
	public ApiResponse<String> updateWfzt(String id);
}
