package com.br.project;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.reactive.config.EnableWebFlux;

@SpringBootApplication
@EnableWebFlux
public class SendNotificationApplication {
	public static void main(String[] args) {
		SpringApplication.run(SendNotificationApplication.class, args);
	}

}
