package com.ldz.wechat.mapper;

import java.util.List;

import com.ldz.wechat.bean.PbClXlmodel;
import com.ldz.wechat.bean.PbInfo;
import com.ldz.wechat.bean.XbXlPb;

import tk.mybatis.mapper.common.Mapper;

public interface PbInfoMapper extends Mapper<PbInfo> {
	/*
	 * 通过日期,线路类型,车辆车型获取所有排班的信息
	 */
	
	List<PbInfo> selectBydate(PbClXlmodel pnbclxlmodel);
	/*
	 * 通过日期,线路类型获取所有线路的排班情况
	 */
	List<XbXlPb> selectXbPb(PbClXlmodel pnbclxlmodel);
}