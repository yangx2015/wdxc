package com.ldz.znzp.service.impl;

import com.ldz.znzp.base.BaseServiceImpl;
import com.ldz.znzp.mapper.ClGpsMapper;
import com.ldz.znzp.model.ClGps;
import com.ldz.util.bean.ApiResponse;
import com.ldz.znzp.service.GpsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.common.Mapper;

@Service
public class GpsServiceImpl extends BaseServiceImpl<ClGps,String> implements GpsService{
    @Autowired
    private ClGpsMapper entityMapper;

    @Override
    protected Mapper<ClGps> getBaseMapper() {
        return entityMapper;
    }

    @Override
    protected Class<?> getEntityCls(){
        return ClGps.class;
    }

    @Override
    public ApiResponse<String> saveEntity(ClGps entity) {
        save(entity);
        return ApiResponse.saveSuccess();
    }
}
