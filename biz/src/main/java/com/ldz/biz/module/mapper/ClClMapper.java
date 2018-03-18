package com.ldz.biz.module.mapper;

import com.ldz.biz.module.model.ClCl;

import tk.mybatis.mapper.common.Mapper;

public interface ClClMapper extends Mapper<ClCl> {
	/*
	 * 通过车辆id找到对应的电子围栏
	 */
	ClCl seleByZdbh(String deviceId);
}