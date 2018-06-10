package com.ldz.biz.module.bean;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DdTjTxReturn {

	private List<String> date;

	private List<String> count;

	private String startTime;
	private String endTime;

}
