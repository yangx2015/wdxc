package com.ldz.wechat.serviceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ldz.sys.base.BaseServiceImpl;
import com.ldz.util.bean.ApiResponse;
import com.ldz.wechat.mapper.ClClyxjlMapper;
import com.ldz.wechat.model.ClClyxjl;
import com.ldz.wechat.service.ClyxjlService;

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
