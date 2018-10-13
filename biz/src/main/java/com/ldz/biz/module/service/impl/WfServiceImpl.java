package com.ldz.biz.module.service.impl;

import org.apache.commons.lang.StringUtils;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ldz.biz.module.mapper.ClWfMapper;
import com.ldz.biz.module.model.ClCl;
import com.ldz.biz.module.model.ClWf;
import com.ldz.biz.module.service.ClService;
import com.ldz.biz.module.service.WfService;
import com.ldz.sys.base.BaseServiceImpl;
import com.ldz.util.bean.ApiResponse;
import com.ldz.util.exception.RuntimeCheck;

import tk.mybatis.mapper.common.Mapper;

@Service
public class WfServiceImpl extends BaseServiceImpl<ClWf, String> implements WfService{
    @Autowired
    private ClWfMapper entityMapper;
    @Autowired
    private ClService clService;

    @Override
    protected Mapper<ClWf> getBaseMapper() {
        return entityMapper;
    }

    @Override
    protected Class<?> getEntityCls(){
        return ClWf.class;
    }

	@Override
	public ApiResponse<String> saveEntity(ClWf entity) {
		if ("10".equals(entity.getWfType())){
			//车辆违法
			ClCl clxx = this.clService.findById(entity.getWfCl());
			RuntimeCheck.ifNull(clxx, "车辆信息不存在！");
		}
		RuntimeCheck.ifBlank(entity.getWfDate(), "请先选择违法日期！");
		if (StringUtils.isBlank(entity.getWfMoney()) || !StringUtils.isNumeric(entity.getWfMoney())){
			entity.setWfMoney("0");
		}
		if (StringUtils.isBlank(entity.getWfScoring()) || !StringUtils.isNumeric(entity.getWfScoring())){
			entity.setWfScoring("0");
		}
		if (StringUtils.isNotBlank(entity.getWfDate())){
			entity.setWfDate(DateTime.parse(entity.getWfDate().substring(0, 10)).plusDays(1).toString("yyyy-MM-dd"));
		}
		
		entity.setId(genId());
		save(entity);
		return ApiResponse.saveSuccess();
	}
	
	@Override
	public ApiResponse<String> updateWfzt(String id) {
		ClWf wfxx = this.findById(id);
		RuntimeCheck.ifNull(wfxx, "违法信息不存在！");
		//将违法状态修改为已处理
		wfxx.setWfStatus(new Short("1"));
		
		update(wfxx);
		return ApiResponse.saveSuccess();
	}
}
