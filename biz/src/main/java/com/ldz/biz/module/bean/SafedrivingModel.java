package com.ldz.biz.module.bean;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class SafedrivingModel {

	private String sjxm;

	private int speedupCount;

	private int speedownCount;

	private int wheelCount;

	private int overspeedCount;

	private String cph;

	private int total;

}
