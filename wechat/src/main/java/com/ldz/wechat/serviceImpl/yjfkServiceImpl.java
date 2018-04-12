package com.ldz.wechat.serviceImpl;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ldz.sys.base.BaseServiceImpl;
import com.ldz.util.bean.ApiResponse;
import com.ldz.wechat.mapper.yjfkMapper;
import com.ldz.wechat.model.yjfk;
import com.ldz.wechat.service.yjfkService;

import tk.mybatis.mapper.common.Mapper;
@Service
public class yjfkServiceImpl extends BaseServiceImpl<yjfk, String> implements yjfkService {

	@Autowired
	private yjfkMapper yjfkMapper;
	
	
	@Override
	public ApiResponse<String> saveEntity(yjfk entity) {
		entity.setYjId(genId());
		entity.setCjsj(new Date());
		entity.setCjr(getOperateUser());
		save(entity);
		return ApiResponse.saveSuccess();
	}

	@Override
	public ApiResponse<String> updateEntity(yjfk entity) {
		entity.setYjId(genId());
		entity.setXgr(getOperateUser());
		entity.setXgsj(new Date());
		save(entity);
		return ApiResponse.updateSuccess();
	}

	@Override
	protected Mapper<yjfk> getBaseMapper() {
		return yjfkMapper;
	}

}
