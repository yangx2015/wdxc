package com.ldz.biz.module.bean;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Date;

@Getter
@Setter
public class websocketInfo {

	private String clid;
	
	private String cph;
	
	private String speed;
	
	private String eventType;
	
	private Date  time;
	
	private BigDecimal bdjd;
	
	private BigDecimal bdwd;
	
	
}
