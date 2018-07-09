package com.ldz.biz.module.service.impl;


import com.ldz.biz.module.mapper.ClXcMapper;
import com.ldz.biz.module.model.ClXc;
import com.ldz.biz.module.service.XcService;
import com.ldz.sys.base.BaseServiceImpl;
import com.ldz.util.bean.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.common.Mapper;

@Service
public class XcServiceImpl extends BaseServiceImpl<ClXc,String> implements XcService{
    @Autowired
    private ClXcMapper entityMapper;

    @Override
    protected Mapper<ClXc> getBaseMapper() {
        return entityMapper;
    }

    @Override
    protected Class<?> getEntityCls(){
        return ClXc.class;
    }

    @Override
    public ApiResponse<String> saveEntity(ClXc clXc) {

        clXc.setId(genId());
        int i = save(clXc);
        return i==1 ? ApiResponse.success():ApiResponse.fail();
    }
}
