package com.ldz.biz.module.service.impl;

import com.ldz.util.bean.ApiResponse;
import com.ldz.sys.base.BaseServiceImpl;
import com.ldz.biz.module.mapper.ClDdMapper;
import com.ldz.biz.module.model.ClDd;
import com.ldz.biz.module.service.DdService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.common.Mapper;

@Service
public class DdServiceImpl extends BaseServiceImpl<ClDd,String> implements DdService{
    @Autowired
    private ClDdMapper entityMapper;

    @Override
    protected Mapper<ClDd> getBaseMapper() {
        return entityMapper;
    }

    @Override
    protected Class<?> getEntityCls(){
        return ClDd.class;
    }

    @Override
    public ApiResponse<String> saveEntity(ClDd entity) {
        save(entity);
        return ApiResponse.saveSuccess();
    }
}
