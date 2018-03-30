package com.ldz.biz.module.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ldz.biz.module.mapper.ClDzwlClMapper;
import com.ldz.biz.module.mapper.ClDzwlMapper;
import com.ldz.biz.module.model.ClCl;
import com.ldz.biz.module.model.ClDzwl;
import com.ldz.biz.module.model.ClDzwlCl;
import com.ldz.biz.module.service.ClService;
import com.ldz.biz.module.service.DzwlService;
import com.ldz.sys.base.BaseServiceImpl;
import com.ldz.sys.constant.Dict;
import com.ldz.sys.exception.RuntimeCheck;
import com.ldz.sys.model.SysJg;
import com.ldz.sys.model.SysYh;
import com.ldz.sys.service.JgService;
import com.ldz.util.bean.ApiResponse;
import com.ldz.util.bean.SimpleCondition;

import tk.mybatis.mapper.common.Mapper;

@Service
public class DzwlServiceImpl extends BaseServiceImpl<ClDzwl,String> implements DzwlService{
    @Autowired
    private ClDzwlMapper entityMapper;
    @Autowired
    private JgService jgService;
    @Autowired
    private ClService clService;
    @Autowired
    private ClDzwlClMapper dzwlClMapper;

    @Override
    protected Mapper<ClDzwl> getBaseMapper() {
        return entityMapper;
    }

    @Override
    protected Class<?> getEntityCls(){
        return ClDzwl.class;
    }

    @Override
    public ApiResponse<String> saveEntity(ClDzwl entity) {
        SysYh user = getCurrentUser();
        SysJg org = jgService.findByOrgCode(user.getJgdm());
        Date now = new Date();
        String wlmc = entity.getWlmc();
        RuntimeCheck.ifTrue(ifExists("wlmc",wlmc),"围栏名称已存在");
        entity.setCjr(getOperateUser());
        entity.setCjsj(now);
        entity.setId(genId());
        entity.setJgdm(user.getJgdm());
        entity.setJgmc(org.getJgmc());
        entity.setZt(Dict.CommonStatus.VALID.getCode());
        save(entity);
        return ApiResponse.saveSuccess();
    }

    @Override
    public ApiResponse<String> updateEntity(ClDzwl entity) {
        ClDzwl oldRecord = findById(entity.getId());
        RuntimeCheck.ifNull(oldRecord,"记录不存在");
        String wlmc = entity.getWlmc();
        if (!oldRecord.getWlmc().equals(entity.getWlmc())){
            RuntimeCheck.ifTrue(ifExists("wlmc",wlmc),"围栏名称已存在");
        }
        entity.setXgr(getOperateUser());
        entity.setXgsj(new Date());
        update(entity);
        return ApiResponse.success();
    }

    /**
     * 设置车辆电子围栏
     *
     * @param clId
     * @param wlIds
     * @return
     */
    @Override
    public ApiResponse<String> setCarDzwl(String clId, List<String> wlIds) {
        RuntimeCheck.ifBlank(clId,"车辆id不能为空");
        ClCl car = clService.findById(clId);
        RuntimeCheck.ifNull(car,"未找到车辆");

        SimpleCondition condition = new SimpleCondition(ClDzwlCl.class);
        condition.eq(ClDzwlCl.InnerColumn.clId,clId);
        dzwlClMapper.deleteByExample(condition);

        for (String wlId : wlIds) {
            ClDzwlCl dzwlCl = new ClDzwlCl();
            dzwlCl.setClId(clId);
            dzwlCl.setWlId(wlId);
            dzwlClMapper.insertSelective(dzwlCl);
        }
        return ApiResponse.success();
    }
}
