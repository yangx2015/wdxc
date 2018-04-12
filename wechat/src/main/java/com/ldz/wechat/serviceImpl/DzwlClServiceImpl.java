package com.ldz.wechat.serviceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ldz.sys.base.BaseServiceImpl;
import com.ldz.util.bean.ApiResponse;
import com.ldz.wechat.mapper.ClDzwlClMapper;
import com.ldz.wechat.model.ClDzwlCl;
import com.ldz.wechat.service.DzwlClService;

import tk.mybatis.mapper.common.Mapper;

@Service
public class DzwlClServiceImpl extends BaseServiceImpl<ClDzwlCl,String> implements DzwlClService{
    @Autowired
    private ClDzwlClMapper entityMapper;

    @Override
    protected Mapper<ClDzwlCl> getBaseMapper() {
        return entityMapper;
    }

    @Override
    protected Class<?> getEntityCls(){
        return ClDzwlCl.class;
    }

    @Override
    public ApiResponse<String> saveEntity(ClDzwlCl entity) {
        save(entity);
        return ApiResponse.saveSuccess();
    }
}
