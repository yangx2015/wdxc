package com.ldz.biz.module.service.impl;

import com.ldz.util.bean.ApiResponse;
import com.ldz.sys.base.BaseServiceImpl;
import com.ldz.biz.module.mapper.ClSbycsjjlMapper;
import com.ldz.biz.module.model.ClSbycsjjl;
import com.ldz.biz.module.service.SbycsjjlService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.common.Mapper;

@Service
public class SbycsjjlServiceImpl extends BaseServiceImpl<ClSbycsjjl,String> implements SbycsjjlService{
    @Autowired
    private ClSbycsjjlMapper entityMapper;

    @Override
    protected Mapper<ClSbycsjjl> getBaseMapper() {
        return entityMapper;
    }

    @Override
    protected Class<?> getEntityCls(){
        return ClSbycsjjl.class;
    }

    @Override
    public ApiResponse<String> saveEntity(ClSbycsjjl entity) {
        save(entity);
        return ApiResponse.saveSuccess();
    }
}
