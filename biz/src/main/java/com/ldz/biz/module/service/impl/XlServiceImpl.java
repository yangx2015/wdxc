package com.ldz.biz.module.service.impl;

import com.ldz.biz.module.mapper.ClXlMapper;
import com.ldz.biz.module.mapper.ClZnzpMapper;
import com.ldz.biz.module.model.ClXl;
import com.ldz.biz.module.model.ClXlzd;
import com.ldz.biz.module.service.XlService;
import com.ldz.biz.module.service.XlzdService;
import com.ldz.sys.base.BaseServiceImpl;
import com.ldz.util.bean.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.common.Mapper;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class XlServiceImpl extends BaseServiceImpl<ClXl,String> implements XlService {
    @Autowired
    private ClXlMapper entityMapper;
    @Autowired
    private ClZnzpMapper znzpMapper;
    @Autowired
    private XlzdService xlzdService;
    @Autowired
    private XlService xlService;

    @Override
    protected Mapper<ClXl> getBaseMapper() {
        return entityMapper;
    }

    @Override
    protected Class<?> getEntityCls(){
        return ClXl.class;
    }

    @Override
    public ApiResponse<String> saveEntity(ClXl entity) {
        save(entity);
        return ApiResponse.saveSuccess();
    }

    @Override
    public List<ClXl> getByZdId(String zdId) {
        List<ClXlzd> xlzds = xlzdService.findEq(ClXlzd.InnerColumn.zdId,zdId);
        if (xlzds.size() == 0)return new ArrayList<>();
        List<String> xlIds = xlzds.stream().map(ClXlzd::getXlId).collect(Collectors.toList());
        return xlService.findIn(ClXl.InnerColumn.id,xlIds);
    }

}
