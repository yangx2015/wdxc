package com.ldz.biz.module.mapper;

import java.util.List;

import com.ldz.biz.module.bean.gpsSJInfo;
import com.ldz.biz.module.model.ClGpsLs;

import tk.mybatis.mapper.common.Mapper;

public interface ClGpsLsMapper extends Mapper<ClGpsLs> {
	
	List<ClGpsLs> getZdbhAllLsGps(gpsSJInfo info);
}