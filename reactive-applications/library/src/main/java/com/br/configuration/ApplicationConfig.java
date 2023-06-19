package com.br.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class ApplicationConfig  {

    String urlAuth = "http://localhost:8082/api/auth";
    String urlMessage = "http://localhost:8083/api/notifications/send-email";
    @Bean
    public WebClient webClientAuth() {
        return WebClient.create();
    }

    @Bean
    public WebClient webClientMessage() {
        return WebClient.create(urlMessage);
    }

}
