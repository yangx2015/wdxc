package com.ldz.wechat.bean;

import java.math.BigDecimal;
import java.util.Date;

import lombok.Getter;
import lombok.Setter;

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
