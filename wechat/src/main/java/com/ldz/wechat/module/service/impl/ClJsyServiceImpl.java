package com.ldz.wechat.module.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ldz.util.bean.ApiResponse;
import com.ldz.util.exception.RuntimeCheck;
import com.ldz.wechat.module.mapper.ClJsyMapper;
import com.ldz.wechat.module.model.ClJsy;
import com.ldz.wechat.module.service.ClJsyService;
@Service
public class ClJsyServiceImpl implements ClJsyService{
    @Autowired
    private ClJsyMapper jsymapper;
    
	@Override
	public ApiResponse<ClJsy> findJsy(String sfzhm, String xm) {
		ClJsy findJzg = jsymapper.findJzg(sfzhm, xm);
		RuntimeCheck.ifNull(findJzg, "姓名或者身份证号码有误");
		 ApiResponse<ClJsy> response = new ApiResponse<>();
		 response.setResult(findJzg);
		return response;
	}

	@Override
	public ApiResponse<String> updatejsy(ClJsy jsy) {
		jsy.setZt(null);
		jsymapper.updateByPrimaryKeySelective(jsy);
		return ApiResponse.updateSuccess();
	}

	public ClJsy findById(String sfzhm){
		ClJsy findJzg = jsymapper.findById(sfzhm);
		return findJzg;
	}
}
