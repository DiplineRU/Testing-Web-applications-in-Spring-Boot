package com.example.Testing_Web_applications_in_Spring_Boot;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@OpenAPIDefinition
public class IntroToSqlApplication {

	public static void main(String[] args) {
		SpringApplication.run(IntroToSqlApplication.class, args);
	}

}
