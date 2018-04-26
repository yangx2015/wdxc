package com.ldz.biz.module.service.impl;

import com.ldz.biz.module.mapper.ClCdMapper;
import com.ldz.biz.module.model.ClCd;
import com.ldz.biz.module.service.CdService;
import com.ldz.sys.base.BaseServiceImpl;
import com.ldz.sys.exception.RuntimeCheck;
import com.ldz.sys.model.SysJg;
import com.ldz.sys.model.SysYh;
import com.ldz.sys.service.JgService;
import com.ldz.util.bean.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.common.Mapper;

import java.util.Date;

@Service
public class CdServiceImpl extends BaseServiceImpl<ClCd,String> implements CdService{
    @Autowired
    private ClCdMapper entityMapper;
    @Autowired
    private JgService jgService;
    @Override
    protected Mapper<ClCd> getBaseMapper() {
        return entityMapper;
    }

    @Override
    protected Class<?> getEntityCls(){
        return ClCd.class;
    }

    @Override
    public ApiResponse<String> saveEntity(ClCd entity) {
    	RuntimeCheck.ifBlank(entity.getCdmc(), "请输入车队名称");
    	RuntimeCheck.ifBlank(entity.getDzxm(), "请输入队长姓名");
//    	RuntimeCheck.ifBlank(entity.getSjhm(), "请输入手机号码");
    	RuntimeCheck.ifBlank(entity.getZt(), "请输入状态");
    	 SysYh user = getCurrentUser();
         SysJg org = jgService.findByOrgCode(user.getJgdm());
         Date now = new Date();
         entity.setCjr(getOperateUser());
         entity.setCjsj(now);
         entity.setJgdm(user.getJgdm());
         entity.setJgmc(org.getJgmc());
         entity.setCdbh(genId());
         save(entity);
        return ApiResponse.saveSuccess();
    }

	@Override
	public ApiResponse<String> updateEntity(ClCd entity) {
		RuntimeCheck.ifBlank(entity.getCdmc(), "请输入车队名称");
    	RuntimeCheck.ifBlank(entity.getDzxm(), "请输入队长姓名");
//    	RuntimeCheck.ifBlank(entity.getSjhm(), "请输入手机号码");
    	RuntimeCheck.ifBlank(entity.getZt(), "请输入状态");
		 ClCd findById = findById(entity.getCdbh());
	        RuntimeCheck.ifNull(findById,"未找到记录");
	        entity.setXgr(getOperateUser());
	        entity.setXgsj(new Date());
	        update(entity);
		return ApiResponse.success();
	}
}
