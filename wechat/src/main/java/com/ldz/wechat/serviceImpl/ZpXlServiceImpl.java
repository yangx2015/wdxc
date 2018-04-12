package com.ldz.wechat.serviceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ldz.sys.base.BaseServiceImpl;
import com.ldz.util.bean.ApiResponse;
import com.ldz.wechat.mapper.ClZpXlMapper;
import com.ldz.wechat.model.ClZpXl;
import com.ldz.wechat.service.ZpXlService;

import tk.mybatis.mapper.common.Mapper;

@Service
public class ZpXlServiceImpl extends BaseServiceImpl<ClZpXl,String> implements ZpXlService{
    @Autowired
    private ClZpXlMapper entityMapper;

    @Override
    protected Mapper<ClZpXl> getBaseMapper() {
        return entityMapper;
    }

    @Override
    protected Class<?> getEntityCls(){
        return ClZpXl.class;
    }

    @Override
    public ApiResponse<String> saveEntity(ClZpXl entity) {
        save(entity);
        return ApiResponse.saveSuccess();
    }
}
