package com.ldz.biz.module.service.impl;

import com.ldz.biz.module.mapper.ClyyMapper;
import com.ldz.biz.module.model.Clyy;
import com.ldz.biz.module.service.ClYyService;
import com.ldz.sys.base.BaseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.common.Mapper;
@Service
public class ClYyServiceImpl extends BaseServiceImpl<Clyy,String> implements ClYyService {

    @Autowired
    private ClyyMapper clyyMapper;

    @Override
    protected Mapper<Clyy> getBaseMapper() {
        return clyyMapper;
    }
}
