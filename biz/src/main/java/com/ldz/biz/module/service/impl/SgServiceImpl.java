package com.ldz.biz.module.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ldz.biz.module.mapper.ClSgMapper;
import com.ldz.biz.module.mapper.ClSgwjMapper;
import com.ldz.biz.module.model.ClSg;
import com.ldz.biz.module.model.ClSgwj;
import com.ldz.biz.module.service.SgService;
import com.ldz.sys.base.BaseServiceImpl;
import com.ldz.util.exception.RuntimeCheck;
import com.ldz.sys.model.SysYh;
import com.ldz.util.bean.ApiResponse;
import com.ldz.util.bean.SimpleCondition;

import tk.mybatis.mapper.common.Mapper;

@Service
public class SgServiceImpl extends BaseServiceImpl<ClSg,String> implements SgService{
    @Autowired
    private ClSgMapper entityMapper;
   
    @Autowired
    private ClSgwjMapper sgwjMapper;

    @Override
    protected Mapper<ClSg> getBaseMapper() {
        return entityMapper;
    }

    @Override
    protected Class<?> getEntityCls(){
        return ClSg.class;
    }

    @Override
    public ApiResponse<String> saveEntity(ClSg entity) {
        SysYh user = getCurrentUser();
        Date now = new Date();
        entity.setCjr(getOperateUser());
        entity.setCjsj(now);
        entity.setId(genId());
        entity.setJgdm(user.getJgdm());
        save(entity);
        return ApiResponse.saveSuccess();
    }

    @Override
    public ApiResponse<String> updateEntity(ClSg entity) {
        ClSg oldRecord = findById(entity.getId());
        RuntimeCheck.ifNull(oldRecord,"未找到记录");
        entity.setXgr(getOperateUser());
        update(entity);
        return ApiResponse.success();
    }

    @Override
    public List<ClSgwj> getSgwj(String sgId) {
        if (StringUtils.isEmpty(sgId))return new ArrayList<>();
        SimpleCondition condition = new SimpleCondition(ClSgwj.class);
        condition.eq(ClSgwj.InnerColumn.sgId,sgId);
        return sgwjMapper.selectByExample(condition);
    }

    @Override
    public void setSgwj(ClSg sg) {
        List<ClSgwj> sgwjs = getSgwj(sg.getId());
        sg.setSgwjs(sgwjs);
    }
}
