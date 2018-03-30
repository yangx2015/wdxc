package com.ldz.biz.module.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ldz.sys.exception.RuntimeCheck;
import com.ldz.biz.module.mapper.ClClMapper;
import com.ldz.biz.module.model.ClCl;
import com.ldz.biz.module.service.ClService;
import com.ldz.sys.base.BaseServiceImpl;
import com.ldz.sys.model.SysJg;
import com.ldz.sys.model.SysYh;
import com.ldz.sys.service.JgService;
import com.ldz.util.bean.ApiResponse;

import tk.mybatis.mapper.common.Mapper;
@Service
public class ClServiceImpl extends BaseServiceImpl<ClCl,String> implements ClService{
    @Autowired
    private ClClMapper entityMapper;
    @Autowired
    private JgService jgService;
    @Autowired
    private ClService clService;

    @Override
    protected Mapper<ClCl> getBaseMapper() {
        return entityMapper;
    }

    @Override
    protected Class<?> getEntityCls(){
        return ClCl.class;
    }

    @Override
    public ClCl findByOrgCode(String code) {
        List<ClCl> jgs = findEq(ClCl.InnerColumn.clId,code);
        if (jgs.size() == 0)return null;
        return jgs.get(0);
    }

    @Override
    public List<ClCl> getOrgCarList(String orgCode) {
        List<ClCl> carList = clService.findEq(ClCl.InnerColumn.jgdm,orgCode);
        return carList;
    }

    @Override
    public ApiResponse<String> saveEntity(ClCl entity) {
    	 SysYh user = getCurrentUser();
         SysJg org = jgService.findByOrgCode(user.getJgdm());
         Date now = new Date();
         entity.setCjr(getOperateUser());
         entity.setClId(genId());
         entity.setCjsj(now);
         entity.setJgdm(user.getJgdm());
         entity.setJgmc(org.getJgmc());
         save(entity);
        return ApiResponse.saveSuccess();
    }

	@Override
	public ApiResponse<String> updateEntity(ClCl entity) {
		  ClCl findById = findById(entity.getClId());
	        RuntimeCheck.ifNull(findById,"未找到记录");
	        entity.setXgr(getOperateUser());
	        entity.setXgsj(new Date());
	        update(entity);
		return ApiResponse.success();
	}

}
