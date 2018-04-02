package com.ldz.biz.module.service.impl;

import com.ldz.util.bean.ApiResponse;
import com.ldz.sys.base.BaseServiceImpl;
import com.ldz.biz.module.bean.gpsSJInfo;
import com.ldz.biz.module.mapper.ClSbyxsjjlMapper;
import com.ldz.biz.module.model.ClSbyxsjjl;
import com.ldz.biz.module.service.SbyxsjjlService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.common.Mapper;

@Service
public class SbyxsjjlServiceImpl extends BaseServiceImpl<ClSbyxsjjl,String> implements SbyxsjjlService{
    @Autowired
    private ClSbyxsjjlMapper entityMapper;

    @Override
    protected Mapper<ClSbyxsjjl> getBaseMapper() {
        return entityMapper;
    }

    @Override
    protected Class<?> getEntityCls(){
        return ClSbyxsjjl.class;
    }

    @Override
    public ApiResponse<String> saveEntity(ClSbyxsjjl entity) {
        save(entity);
        return ApiResponse.saveSuccess();
    }

	@Override
	public ApiResponse<List<ClSbyxsjjl>> historyTrajectory(gpsSJInfo gpssjinfo) {
		ApiResponse<List<ClSbyxsjjl>> apiResponse = new ApiResponse<List<ClSbyxsjjl>>();
		apiResponse.setResult(entityMapper.historyTrajectory(gpssjinfo));
		return apiResponse;
	}
}
