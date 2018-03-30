package com.ldz.biz.module.bean;

import java.util.List;

import com.ldz.biz.module.model.ClCl;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 *  用于编辑校巴/车辆 线路排班管理模型
 */
@Getter
@Setter
@ToString
public class XbXlPb {

	/**
	 * id
	 */
	private String id;

	/**
	 * 线路名称
	 */
	private String xlmc;
	/**
	 * 车辆id集合
	 */
	private String clidlist;
	/**
	 * 车辆对象集合
	 */
	private List<ClCl> clList;
}
