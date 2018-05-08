package com.ldz.biz.module.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ldz.biz.module.mapper.ClLscMapper;
import com.ldz.biz.module.model.ClLsc;
import com.ldz.biz.module.service.LscService;
import com.ldz.sys.base.BaseServiceImpl;
import com.ldz.util.bean.ApiResponse;

import tk.mybatis.mapper.common.Mapper;

import java.util.Date;

@Service
public class LscServiceImpl extends BaseServiceImpl<ClLsc,String> implements LscService{
    @Autowired
    private ClLscMapper entityMapper;

    @Override
    protected Mapper<ClLsc> getBaseMapper() {
        return entityMapper;
    }

    @Override
    protected Class<?> getEntityCls(){
        return ClLsc.class;
    }

    @Override
    public ApiResponse<String> saveEntity(ClLsc entity) {
        entity.setCjsj(new Date());
        entity.setCjr(getOperateUser());
        entity.setId(genId());
        save(entity);
        return ApiResponse.saveSuccess();
    }
}
