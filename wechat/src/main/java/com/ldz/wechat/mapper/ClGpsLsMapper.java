package com.ldz.wechat.mapper;

import java.util.List;

import com.ldz.wechat.bean.gpsSJInfo;
import com.ldz.wechat.model.ClGpsLs;

import tk.mybatis.mapper.common.Mapper;

public interface ClGpsLsMapper extends Mapper<ClGpsLs> {
	
	List<ClGpsLs> getZdbhAllLsGps(gpsSJInfo info);
}