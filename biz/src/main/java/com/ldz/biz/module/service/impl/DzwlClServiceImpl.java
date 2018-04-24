package com.ldz.biz.module.service.impl;

import com.github.pagehelper.PageInfo;
import com.ldz.biz.module.mapper.ClDzwlMapper;
import com.ldz.biz.module.model.ClDzwl;
import com.ldz.biz.module.service.DzwlService;
import com.ldz.util.bean.ApiResponse;
import com.ldz.sys.base.BaseServiceImpl;
import com.ldz.biz.module.mapper.ClDzwlClMapper;
import com.ldz.biz.module.model.ClDzwlCl;
import com.ldz.biz.module.service.DzwlClService;
import com.ldz.util.bean.SimpleCondition;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collector;
import java.util.stream.Collectors;

@Service
public class DzwlClServiceImpl extends BaseServiceImpl<ClDzwlCl,String> implements DzwlClService{
    @Autowired
    private ClDzwlClMapper entityMapper;
    @Autowired
    private DzwlService dzwlService;

    @Override
    protected Mapper<ClDzwlCl> getBaseMapper() {
        return entityMapper;
    }

    @Override
    protected Class<?> getEntityCls(){
        return ClDzwlCl.class;
    }

    @Override
    public ApiResponse<String> saveEntity(ClDzwlCl entity) {
        save(entity);
        return ApiResponse.saveSuccess();
    }


    @Override
    protected void afterPager(PageInfo<ClDzwlCl> resultPage){
        if (resultPage == null || resultPage.getList() == null ||resultPage.getList().size() == 0){
            return;
        }
        Set<String> wlIds = resultPage.getList().stream().map(ClDzwlCl::getWlId).collect(Collectors.toSet());
        List<ClDzwl> dzwls = dzwlService.findIn(ClDzwl.InnerColumn.id,wlIds);
        if (dzwls.size() == 0)return;
        Map<String,ClDzwl> dzwlMap = dzwls.stream().collect(Collectors.toMap(ClDzwl::getId,p->p));
        for (ClDzwlCl dzwlCl : resultPage.getList()) {
            String wlId = dzwlCl.getWlId();
            if (StringUtils.isEmpty(wlId))continue;
            ClDzwl dzwl = dzwlMap.get(wlId);
            if (dzwl == null)continue;
            dzwlCl.setWlmc(dzwl.getWlmc());
        }
    }
}
