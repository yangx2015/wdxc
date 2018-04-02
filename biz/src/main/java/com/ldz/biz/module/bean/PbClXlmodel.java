package com.ldz.biz.module.bean;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.Getter;
import lombok.Setter;

/**
 * 用于处理车辆/校巴 排班管理 条件查询
 */
@Getter
@Setter
public class PbClXlmodel {


	private String clcx;

	private String lulx;

	private String date2;
	
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date date;
	
	
}
