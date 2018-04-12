package com.ldz.wechat.serviceImpl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ldz.sys.base.BaseServiceImpl;
import com.ldz.sys.base.LimitedCondition;
import com.ldz.sys.exception.RuntimeCheck;
import com.ldz.sys.model.SysJg;
import com.ldz.sys.model.SysYh;
import com.ldz.sys.service.JgService;
import com.ldz.util.bean.ApiResponse;
import com.ldz.wechat.mapper.ClCssdMapper;
import com.ldz.wechat.model.ClCssd;
import com.ldz.wechat.service.CssdService;

import tk.mybatis.mapper.common.Mapper;

@Service
public class CssdServiceImpl extends BaseServiceImpl<ClCssd,String> implements CssdService{
    @Autowired
    private ClCssdMapper entityMapper;
    @Autowired
    private JgService jgService;

    @Override
    protected Mapper<ClCssd> getBaseMapper() {
        return entityMapper;
    }

    @Override
    protected Class<?> getEntityCls(){
        return ClCssd.class;
    }

    @Override
    public ApiResponse<String> saveEntity(ClCssd entity) {
        SysYh user = getCurrentUser();
        SysJg org = jgService.findByOrgCode(user.getJgdm());
        Date now = new Date();
        entity.setCjr(getOperateUser());
        entity.setCjsj(now);
        entity.setId(genId());
        entity.setJgdm(user.getJgdm());
        entity.setJgmc(org.getJgmc());
        save(entity);
        return ApiResponse.saveSuccess();
    }

    @Override
    public ApiResponse<String> updateEntity(ClCssd entity) {
        ClCssd oldRecord = findById(entity.getId());
        RuntimeCheck.ifNull(oldRecord,"未找到记录");
        entity.setXgr(getOperateUser());
        entity.setXgsj(new Date());
        update(entity);
        return ApiResponse.success();
    }

    @Override
    public ApiResponse<List<ClCssd>> getByCx(String cx) {
        // 自带机构筛选条件
        LimitedCondition condition = new LimitedCondition(ClCssd.class);
        condition.eq(ClCssd.InnerColumn.cx,cx);
        List<ClCssd> cssds = findByCondition(condition);
        return ApiResponse.success(cssds);
    }
}
