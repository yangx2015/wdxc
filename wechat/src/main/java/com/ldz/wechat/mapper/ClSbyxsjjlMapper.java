package com.ldz.wechat.mapper;

import java.util.List;

import com.ldz.wechat.bean.gpsSJInfo;
import com.ldz.wechat.model.ClSbyxsjjl;

import tk.mybatis.mapper.common.Mapper;

public interface ClSbyxsjjlMapper extends Mapper<ClSbyxsjjl> {
	
	List<ClSbyxsjjl> historyTrajectory(gpsSJInfo gpssjinfo);
}