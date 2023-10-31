package com.group1.mstory;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan 
public class MstoryApplication {

	public static void main(String[] args) {
		SpringApplication.run(MstoryApplication.class, args);
	}
}
