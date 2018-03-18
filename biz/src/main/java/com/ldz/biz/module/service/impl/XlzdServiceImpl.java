package com.ldz.biz.module.service.impl;

import com.ldz.util.bean.ApiResponse;
import com.ldz.sys.base.BaseServiceImpl;
import com.ldz.biz.module.mapper.ClXlzdMapper;
import com.ldz.biz.module.model.ClXlzd;
import com.ldz.biz.module.service.XlzdService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.common.Mapper;

@Service
public class XlzdServiceImpl extends BaseServiceImpl<ClXlzd,String> implements XlzdService{
    @Autowired
    private ClXlzdMapper entityMapper;

    @Override
    protected Mapper<ClXlzd> getBaseMapper() {
        return entityMapper;
    }

    @Override
    protected Class<?> getEntityCls(){
        return ClXlzd.class;
    }

    @Override
    public ApiResponse<String> saveEntity(ClXlzd entity) {
        save(entity);
        return ApiResponse.saveSuccess();
    }
}
