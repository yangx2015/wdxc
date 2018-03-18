package com.ldz.biz.module.service.impl;

import com.ldz.util.bean.ApiResponse;
import com.ldz.sys.base.BaseServiceImpl;
import com.ldz.biz.module.mapper.ClDdrzMapper;
import com.ldz.biz.module.model.ClDdrz;
import com.ldz.biz.module.service.DdrzService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.common.Mapper;

@Service
public class DdrzServiceImpl extends BaseServiceImpl<ClDdrz,String> implements DdrzService{
    @Autowired
    private ClDdrzMapper entityMapper;

    @Override
    protected Mapper<ClDdrz> getBaseMapper() {
        return entityMapper;
    }

    @Override
    protected Class<?> getEntityCls(){
        return ClDdrz.class;
    }

    @Override
    public ApiResponse<String> saveEntity(ClDdrz entity) {
        save(entity);
        return ApiResponse.saveSuccess();
    }
}
