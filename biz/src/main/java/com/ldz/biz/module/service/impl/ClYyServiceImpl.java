package com.ldz.biz.module.service.impl;

import com.ldz.biz.module.mapper.ClyyMapper;
import com.ldz.biz.module.model.Clyy;
import com.ldz.biz.module.service.ClYyService;
import com.ldz.sys.base.BaseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

@Service
public class ClYyServiceImpl extends BaseServiceImpl<Clyy,String> implements ClYyService {

    @Autowired
    private ClyyMapper clyyMapper;

    @Override
    protected Mapper<Clyy> getBaseMapper() {
        return clyyMapper;
    }

    @Override
    public void saveBatch(List<Clyy> clyys) {
        clyys.forEach(clyy -> clyy.setId(genId()));
        clyyMapper.insertList(clyys);

    }
}
