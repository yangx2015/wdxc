package com.ldz.biz.module.service.impl;

import com.ldz.util.bean.ApiResponse;
import com.ldz.sys.base.BaseServiceImpl;
import com.ldz.biz.module.bean.gpsSJInfo;
import com.ldz.biz.module.mapper.ClGpsLsMapper;
import com.ldz.biz.module.model.ClGpsLs;
import com.ldz.biz.module.service.GpsLsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.common.Mapper;

@Service
public class GpsLsServiceImpl extends BaseServiceImpl<ClGpsLs,String> implements GpsLsService{
    @Autowired
    private ClGpsLsMapper entityMapper;

    @Override
    protected Mapper<ClGpsLs> getBaseMapper() {
        return entityMapper;
    }

    @Override
    protected Class<?> getEntityCls(){
        return ClGpsLs.class;
    }

    @Override
    public ApiResponse<String> saveEntity(ClGpsLs entity) {
        save(entity);
        return ApiResponse.saveSuccess();
    }

	@Override
	public ApiResponse<String[]> getZdbhAllLsGps(gpsSJInfo info) {
		
		
		
		return null;
	}
}
