package com.ldz.wechat.module.service.impl;

import com.ldz.util.bean.ApiResponse;
import com.ldz.util.bean.SimpleCondition;
import com.ldz.util.commonUtil.Des;
import com.ldz.util.commonUtil.EncryptUtil;
import com.ldz.util.exception.RuntimeCheck;
import com.ldz.wechat.base.BaseServiceImpl;
import com.ldz.wechat.module.mapper.SysClkPtyhMapper;
import com.ldz.wechat.module.model.SysYh;
import com.ldz.wechat.module.service.YhService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.common.Mapper;


@Service
public class YhServiceImpl extends BaseServiceImpl<SysYh, String> implements YhService {
	Logger log = LogManager.getLogger(this);
	@Autowired
	private SysClkPtyhMapper baseMapper;


	@Override
	protected Class<SysYh> getEntityCls(){
		return SysYh.class;
	}
	
	@Override
	protected Mapper<SysYh> getBaseMapper() {
		return baseMapper;
	}
	@Override
	public SysYh selectByKey(String userId){
		return baseMapper.selectByPrimaryKey(userId);
	}
}