package com.ldz.biz.module.bean;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

/*
 * 司机出车统计条形图模型
 */
@Getter
@Setter
public class CcTjTx {

	private List<String> sjxm;
	
	private List<Integer> count;
}
