package com.ldz.wechat.module.service.impl;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ldz.util.bean.ApiResponse;
import com.ldz.wechat.base.BaseServiceImpl;
import com.ldz.wechat.module.mapper.SysYjfkMapper;
import com.ldz.wechat.module.model.SysYjfk;
import com.ldz.wechat.module.service.YjService;

import tk.mybatis.mapper.common.Mapper;


@Service
public class YjServiceImpl extends BaseServiceImpl<SysYjfk,String> implements YjService{
    @Autowired
    private SysYjfkMapper yjfkMapper;
    @Override
    protected Mapper<SysYjfk> getBaseMapper() {
        return yjfkMapper;
    }
    
	@Override
	public ApiResponse<String> saveEntity(SysYjfk entity) {
		//默认待处理
		entity.setZt("00");
		entity.setYjId(genId());
		entity.setCjsj(new Date());
		save(entity);
		return ApiResponse.saveSuccess();
	}

	@Override
	public ApiResponse<String> updateEntity(SysYjfk entity) {
		
		entity.setXgsj(new Date());
		update(entity);
		return ApiResponse.updateSuccess();
	}
}
