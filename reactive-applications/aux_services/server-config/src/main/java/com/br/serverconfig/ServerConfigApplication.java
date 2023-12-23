package com.br.serverconfig;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.config.server.EnableConfigServer;


@SpringBootApplication
@EnableConfigServer
public class ServerConfigApplication {

	public static void main(String[] args) {
		SpringApplication.run(ServerConfigApplication.class, args);
	}

}
