package com.ldz.biz.module.service.impl;

import com.ldz.util.bean.ApiResponse;
import com.ldz.sys.base.BaseServiceImpl;
import com.ldz.biz.module.mapper.ClClyxjlMapper;
import com.ldz.biz.module.model.ClClyxjl;
import com.ldz.biz.module.service.ClyxjlService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.common.Mapper;

@Service
public class ClyxjlServiceImpl extends BaseServiceImpl<ClClyxjl,String> implements ClyxjlService{
    @Autowired
    private ClClyxjlMapper entityMapper;

    @Override
    protected Mapper<ClClyxjl> getBaseMapper() {
        return entityMapper;
    }

    @Override
    protected Class<?> getEntityCls(){
        return ClClyxjl.class;
    }

    @Override
    public ApiResponse<String> saveEntity(ClClyxjl entity) {
        save(entity);
        return ApiResponse.saveSuccess();
    }
}
