package com.ldz.biz.module.service.impl;

import com.ldz.biz.module.mapper.ClClMapper;
import com.ldz.biz.module.mapper.ClClyxjlMapper;
import com.ldz.biz.module.mapper.ClPbMapper;
import com.ldz.biz.module.mapper.ClXlMapper;
import com.ldz.biz.module.model.ClCl;
import com.ldz.biz.module.service.ClService;
import com.ldz.sys.base.BaseServiceImpl;
import com.ldz.util.bean.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.common.Mapper;

@Service
public class ClServiceImpl extends BaseServiceImpl<ClCl,String> implements ClService{
    @Autowired
    private ClClMapper entityMapper;
    @Autowired
    private ClPbMapper clPbMapper;
    @Autowired
    private ClXlMapper xlMapper;
    @Autowired
    private ClClyxjlMapper clyxjlMapper;

    @Override
    protected Mapper<ClCl> getBaseMapper() {
        return entityMapper;
    }

    @Override
    protected Class<?> getEntityCls(){
        return ClCl.class;
    }

    @Override
    public ApiResponse<String> saveEntity(ClCl entity) {
        save(entity);
        return ApiResponse.saveSuccess();
    }

}
