package com.ldz.biz.module.bean;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CsTxTj {

	List<String> list = new ArrayList<String>(){
        /**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		{
            add("08:00:00-10:00:00");
            add("10:00:00-12:00:00");
            add("12:00:00-14:00:00");
            add("14:00:00-16:00:00");
            add("16:00:00-18:00:00");
            add("18:00:00-20:00:00");
            add("20:00:00-22:00:00");
        }
    };
	
	private List<Integer> count;
}
