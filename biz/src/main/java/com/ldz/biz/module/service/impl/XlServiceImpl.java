package com.ldz.biz.module.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ldz.sys.exception.RuntimeCheck;
import com.ldz.biz.module.mapper.ClXlMapper;
import com.ldz.biz.module.model.ClXl;
import com.ldz.biz.module.model.ClXlzd;
import com.ldz.biz.module.service.XlService;
import com.ldz.biz.module.service.XlzdService;
import com.ldz.sys.base.BaseServiceImpl;
import com.ldz.sys.model.SysJg;
import com.ldz.sys.model.SysYh;
import com.ldz.sys.service.JgService;
import com.ldz.util.bean.ApiResponse;

import tk.mybatis.mapper.common.Mapper;

@Service
public class XlServiceImpl extends BaseServiceImpl<ClXl,String> implements XlService {
    @Autowired
    private ClXlMapper entityMapper;
    @Autowired
    private JgService jgService; 
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
    	 SysYh user = getCurrentUser();
         SysJg org = jgService.findByOrgCode(user.getJgdm());
         Date now = new Date();
         entity.setCjr(getOperateUser());
         entity.setCjsj(now);
         entity.setJgdm(user.getJgdm());	
         entity.setJgmc(org.getJgmc());
         entity.setId(genId());
         save(entity);
        return ApiResponse.saveSuccess();
    }

	@Override
	public ApiResponse<String> updateEntity(ClXl entity) {
		ClXl findById = findById(entity.getId());
	        RuntimeCheck.ifNull(findById,"未找到记录");
	        entity.setXgr(getOperateUser());
	        entity.setXgsj(new Date());
	        update(entity);
		return ApiResponse.success();
	}
    @Override
    public List<ClXl> getByZdId(String zdId) {
        List<ClXlzd> xlzds = xlzdService.findEq(ClXlzd.InnerColumn.zdId,zdId);
        if (xlzds.size() == 0)return new ArrayList<>();
        List<String> xlIds = xlzds.stream().map(ClXlzd::getXlId).collect(Collectors.toList());
        return xlService.findIn(ClXl.InnerColumn.id,xlIds);
    }


}
