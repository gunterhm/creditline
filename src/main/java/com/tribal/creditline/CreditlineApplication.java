package com.tribal.creditline;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@ComponentScan("com.tribal.creditline")
@EntityScan("com.tribal.creditline.model.persist")
@EnableJpaRepositories("com.tribal.creditline.repository")
public class CreditlineApplication {

	public static void main(String[] args) {
		SpringApplication.run(CreditlineApplication.class, args);
	}

}
