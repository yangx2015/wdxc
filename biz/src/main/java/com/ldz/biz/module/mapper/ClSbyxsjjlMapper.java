package com.ldz.biz.module.mapper;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.ldz.biz.module.bean.SafedrivingModel;
import com.ldz.biz.module.bean.gpsSJInfo;
import com.ldz.biz.module.model.ClSbyxsjjl;

import tk.mybatis.mapper.common.Mapper;

public interface ClSbyxsjjlMapper extends Mapper<ClSbyxsjjl> {
	
	List<ClSbyxsjjl> historyTrajectory(gpsSJInfo gpssjinfo);
	
/*	
	List<ClSbyxsjjl> gpsInit();*/
	
	List<SafedrivingModel> Safedriving();
	
	List<ClSbyxsjjl> findByCphAndTime(@Param("cph") String cph, @Param("start") Date start, @Param("end") Date end);
	
}