package com.example.stagdocs;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.PropertySource;

@SpringBootApplication
@PropertySource("classpath:secrets.properties")
public class StagDocsApplication {

	public static void main(String[] args) {
		SpringApplication.run(StagDocsApplication.class, args);
	}

}
