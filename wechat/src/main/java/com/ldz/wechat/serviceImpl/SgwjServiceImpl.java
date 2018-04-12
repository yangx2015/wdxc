package com.ldz.wechat.serviceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ldz.sys.base.BaseServiceImpl;
import com.ldz.util.bean.ApiResponse;
import com.ldz.wechat.mapper.ClSgwjMapper;
import com.ldz.wechat.model.ClSgwj;
import com.ldz.wechat.service.SgwjService;

import tk.mybatis.mapper.common.Mapper;

@Service
public class SgwjServiceImpl extends BaseServiceImpl<ClSgwj,String> implements SgwjService{
    @Autowired
    private ClSgwjMapper entityMapper;

    @Override
    protected Mapper<ClSgwj> getBaseMapper() {
        return entityMapper;
    }

    @Override
    protected Class<?> getEntityCls(){
        return ClSgwj.class;
    }

    @Override
    public ApiResponse<String> saveEntity(ClSgwj entity) {
        save(entity);
        return ApiResponse.saveSuccess();
    }
}
