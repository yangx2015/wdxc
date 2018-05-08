package com.ldz.biz.module.service.impl;

import com.ldz.util.bean.ApiResponse;
import com.ldz.sys.base.BaseServiceImpl;
import com.ldz.biz.module.mapper.ClLsdwMapper;
import com.ldz.biz.module.model.ClLsdw;
import com.ldz.biz.module.service.LsdwService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.common.Mapper;

import java.util.Date;

@Service
public class LsdwServiceImpl extends BaseServiceImpl<ClLsdw,String> implements LsdwService{
    @Autowired
    private ClLsdwMapper entityMapper;

    @Override
    protected Mapper<ClLsdw> getBaseMapper() {
        return entityMapper;
    }

    @Override
    protected Class<?> getEntityCls(){
        return ClLsdw.class;
    }

    @Override
    public ApiResponse<String> saveEntity(ClLsdw entity) {
        entity.setId(genId());
        entity.setCjr(getOperateUser());
        entity.setCjsj(new Date());
        entity.setDjcs(new Short("0"));
        save(entity);
        return ApiResponse.saveSuccess();
    }
}
