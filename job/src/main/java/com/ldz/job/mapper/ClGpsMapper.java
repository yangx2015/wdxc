package com.ldz.job.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.ldz.job.model.ClGps;

import tk.mybatis.mapper.common.Mapper;

public interface ClGpsMapper extends Mapper<ClGps> {
	/*
	 * 批量插入gps实时数据
	 */
	void saveGpsList(@Param("gpsList") List<Object> gpsList);
}