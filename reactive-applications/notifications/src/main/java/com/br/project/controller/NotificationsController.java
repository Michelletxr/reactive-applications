package com.br.project.controller;
import com.br.project.model.NotificationsModel;
import com.br.project.service.NotificationsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;


@RestController
@RequestMapping(value = "api/notifications")
public class NotificationsController {
    @Autowired
    NotificationsService service;

    @PostMapping(value = "send-email")
    public Mono<String> createEmail(@RequestBody NotificationsModel.NotificationDto msg) {
        return service.sendEmail(msg);
    }

    @GetMapping
    public Mono<String> createMessagerServer() {
        return Mono.just("serviço para envio de email");
    }

}



