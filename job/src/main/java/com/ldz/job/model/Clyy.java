package com.ldz.job.model;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
/*
 * 百度鹰眼返回的数据模型
 */
@Getter
@Setter
@ToString
@Table(name = "CL_YY")
public class Clyy implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/*
	 * 主键
	 */
	@Id
	@Column(name="ID")
	private String id;
	/*
	 * 终端编号 对应百度鹰眼的entity_name
	 */
	@Column(name = "ZDBH")
	private String zdbh;
	/*
	 * 经度
	 */
	@Column(name = "LONGITUDE")
	private BigDecimal longitude;
	/*
	 * 纬度
	 */
	@Column(name = "LATITUDE")
	private BigDecimal latitude;
	/*
	 * 上传点位的时间
	 */
	@Column(name = "LOCTIME")
	private String loc_time;
	/*
	 * 预留字段
	 */
	@Column(name = "OBJECTKEY")
	private String object_key;
	/*
	 * 方向角
	 */
	@Column(name = "DIRECTION")
	private String direction;
	/*
	 * 速度
	 */
	@Column(name = "SPEED")
	private BigDecimal speed;

}
