package com.br.util;

import com.br.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;
import org.springframework.util.MimeTypeUtils;
import reactor.core.publisher.Mono;

@Component
public class Supliers {

    @Autowired
    private StreamBridge streamBridge;

    private String log_event = "log-out-0";
    private String message_event = "send-out-0";

    public Mono<Boolean> processLog(String log){
        return Mono.just(streamBridge.send(log_event, log));
    }

    public Mono<Boolean> processMessage(String emailFrom, String emailTo, String title, String text){
        Message<UserService.SendEmail> emailEvent = MessageBuilder
                .withPayload(new UserService.SendEmail(emailFrom, emailTo, title, text))
                .setHeader(MessageHeaders.CONTENT_TYPE, MimeTypeUtils.APPLICATION_JSON).build();
        return Mono.just(streamBridge.send(message_event, emailEvent));
    }

}
