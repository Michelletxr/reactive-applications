package com.br.project.controller;
import com.br.project.model.NotificationsModel;
import com.br.project.service.NotificationsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import javax.validation.Valid;

@RestController
@RequestMapping(value = "api/notifications")
public class NotificationsController {
    @Autowired
    NotificationsService service;

    @PostMapping(value = "send-email")
    public Mono<String> createEmail(@RequestBody @Valid NotificationsModel.NotificationDto msg) {
        return service.sendEmail(msg);
    }

    @GetMapping(value = "send-email" )
    public Mono<String> createHello() {
        return Mono.just("hola");
    }


}



