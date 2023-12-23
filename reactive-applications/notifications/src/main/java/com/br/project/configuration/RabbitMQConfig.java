package com.br.project.configuration;


import com.br.project.model.NotificationsModel;
import com.br.project.service.NotificationsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.amqp.core.Queue;

import java.util.function.Consumer;
import java.util.function.Function;


@Configuration
public class RabbitMQConfig {
    @Autowired
    NotificationsService service;


    /*@Bean
    public Consumer<NotificationsModel.NotificationDto> receive(){
        return str -> service.sendEmail(str).doOnNext(e -> System.out.println(e));
    }*/

}
