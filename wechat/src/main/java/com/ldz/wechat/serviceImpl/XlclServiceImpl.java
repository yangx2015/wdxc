package com.ldz.wechat.serviceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ldz.sys.base.BaseServiceImpl;
import com.ldz.util.bean.ApiResponse;
import com.ldz.wechat.mapper.ClXlclMapper;
import com.ldz.wechat.model.ClXlcl;
import com.ldz.wechat.service.XlclService;

import tk.mybatis.mapper.common.Mapper;

@Service
public class XlclServiceImpl extends BaseServiceImpl<ClXlcl,String> implements XlclService{
    @Autowired
    private ClXlclMapper entityMapper;

    @Override
    protected Mapper<ClXlcl> getBaseMapper() {
        return entityMapper;
    }

    @Override
    protected Class<?> getEntityCls(){
        return ClXlcl.class;
    }

    @Override
    public ApiResponse<String> saveEntity(ClXlcl entity) {
        save(entity);
        return ApiResponse.saveSuccess();
    }
}
