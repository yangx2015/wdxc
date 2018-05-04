package com.ldz.biz.module.mapper;

import java.util.List;

import com.ldz.biz.module.bean.DdTongjiTJ;
import com.ldz.biz.module.model.ClDd;

import tk.mybatis.mapper.common.Mapper;

public interface ClDdMapper extends Mapper<ClDd> {
	List<ClDd> DdTongji(DdTongjiTJ dd);
}