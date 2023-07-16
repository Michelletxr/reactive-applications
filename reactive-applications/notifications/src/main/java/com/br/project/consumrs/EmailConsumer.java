package com.br.project.consumrs;

import com.br.project.model.NotificationsModel;
import com.br.project.service.NotificationsService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;

@Component
public class EmailConsumer {
    @Autowired
    NotificationsService service;
   /* @RabbitListener(queues = "${spring.rabbitmq.queue}")
    public void listerner(@RequestBody NotificationsModel.NotificationDto notificationDto){
        service.sendEmail(notificationDto).doOnNext(e-> System.out.println(e));
    }*/
}
