package com.bsoft.xnsmservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;

@SpringBootApplication
@ServletComponentScan
public class XnsmserviceApplication {

	public static void main(String[] args) {
//		test git.
		SpringApplication.run(XnsmserviceApplication.class, args);
	}

}
