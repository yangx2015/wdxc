package com.ldz.biz.module.mapper;

import java.util.List;

import com.ldz.biz.module.bean.gpsSJInfo;
import com.ldz.biz.module.model.ClSbyxsjjl;

import tk.mybatis.mapper.common.Mapper;

public interface ClSbyxsjjlMapper extends Mapper<ClSbyxsjjl> {
	
	List<ClSbyxsjjl> historyTrajectory(gpsSJInfo gpssjinfo);
	
/*	
	List<ClSbyxsjjl> gpsInit();*/
}