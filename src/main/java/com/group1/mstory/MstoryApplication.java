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

	// @Bean
    // public CommandLineRunner commandLineRunner(ApplicationContext ctx) {
    //     return args -> {
    //         System.out.println("Let's inspect the beans provided by Spring Boot:");
    //         String[] beanNames = ctx.getBeanDefinitionNames();
    //         Arrays.sort(beanNames);
    //         for (String beanName : beanNames) {
    //             System.out.println(beanName);
    //         }

    //     };
    // }

}
