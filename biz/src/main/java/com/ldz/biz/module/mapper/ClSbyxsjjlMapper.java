package com.ldz.biz.module.mapper;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.ldz.biz.module.bean.SafedrivingModel;
import com.ldz.biz.module.bean.gpsSJInfo;
import com.ldz.biz.module.model.ClSbyxsjjl;

import tk.mybatis.mapper.common.Mapper;

public interface ClSbyxsjjlMapper extends Mapper<ClSbyxsjjl> {
	
	List<ClSbyxsjjl> historyTrajectory(gpsSJInfo gpssjinfo);
	
/*	
	List<ClSbyxsjjl> gpsInit();*/
	
	List<SafedrivingModel> Safedriving(Map<String,Object> param);
	
	List<ClSbyxsjjl> findByCphAndTime(@Param("cph") String cph, @Param("start") Date start, @Param("end") Date end);
	
}