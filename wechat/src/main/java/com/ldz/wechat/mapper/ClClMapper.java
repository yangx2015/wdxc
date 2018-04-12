package com.ldz.wechat.mapper;

import java.util.List;

import com.ldz.wechat.model.ClCl;

import tk.mybatis.mapper.common.Mapper;

public interface ClClMapper extends Mapper<ClCl> {
	/*
	 * 通过车辆id找到对应的电子围栏
	 */
	ClCl seleByZdbh(String deviceId);
	
	/*
	 * 通过车辆id集合获取车俩信息
	 * 
	 */
	List<ClCl> getAllClInfo(List<String> list);
}