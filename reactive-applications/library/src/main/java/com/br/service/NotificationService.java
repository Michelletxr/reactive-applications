package com.br.service;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.service.annotation.PostExchange;
import reactor.core.publisher.Mono;

public interface NotificationService {
    @PostExchange("/send-email")
    Mono<String> sendNotifications(@RequestBody UserService.SendEmail emailTo);
}
