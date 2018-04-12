package com.ldz.wechat.serviceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ldz.sys.base.BaseServiceImpl;
import com.ldz.util.bean.ApiResponse;
import com.ldz.wechat.mapper.ClLsdwMapper;
import com.ldz.wechat.model.ClLsdw;
import com.ldz.wechat.service.LsdwService;

import tk.mybatis.mapper.common.Mapper;

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
        save(entity);
        return ApiResponse.saveSuccess();
    }
}
