package com.officecleaners.WorkWise;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories(basePackages = "com.officecleaners.workwise.repository")
public class WorkWiseApplication {

	public static void main(String[] args) {
		SpringApplication.run(WorkWiseApplication.class, args);
	}

}
