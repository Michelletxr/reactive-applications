package com.br.project.model;


import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Document
public class NotificationsModel{
    @Id
    private UUID id;
    private String username;
    private String emailFrom;
    private String emailTo;
    private String title;
    private String text;
    private LocalDateTime sendDateEmail;
    private Boolean sendEmail;
    @Builder
    public NotificationsModel(String emailFrom, String emailTo, String title, String text){
        this.emailFrom = emailFrom;
        this.emailTo = emailTo;
        this.text = text;
        this.title = title;
        this.id = UUID.randomUUID();
    }
    public record NotificationDto(String emailFrom, String emailTo, String title, String text){}
}