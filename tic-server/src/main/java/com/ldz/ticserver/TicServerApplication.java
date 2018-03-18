package com.ldz.ticserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

import com.ldz.ticserver.bean.ThreadPoolBean;

@SpringBootApplication
@EnableConfigurationProperties({ThreadPoolBean.class} ) // 开启配置属性支持  
public class TicServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(TicServerApplication.class, args);
	}
}
