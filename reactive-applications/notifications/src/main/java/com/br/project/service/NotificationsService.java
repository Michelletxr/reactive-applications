package com.br.project.service;

import com.br.project.model.NotificationsModel;
import com.br.project.repository.NotificationsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import java.time.LocalDateTime;

@Service
public class NotificationsService {

    @Autowired
    private JavaMailSender emailSender;
    @Autowired
    private NotificationsRepository repository;
    public Mono<String> sendEmail(NotificationsModel.NotificationDto msg) {

        NotificationsModel email = new NotificationsModel(msg.emailFrom(),
                msg.emailTo(), msg.title(), msg.text());
        email.setSendDateEmail(LocalDateTime.now());
        Boolean send = null;

        try{
            SimpleMailMessage message = new SimpleMailMessage();
           // message.setFrom(email.getEmailFrom());
            message.setTo(email.getEmailTo());
            message.setSubject(email.getTitle());
            message.setText(email.getText());
            emailSender.send(message);
            email.setSendEmail(true);
            send = true;
        } catch (MailException e){
            System.out.println(e);
            send = false;
            email.setSendEmail(false);
        }

        if(send){
            return repository.save(email).then(Mono.just("Email enviado com sucesso!"));
        }else{
            return Mono.just("erro ao enviar email!");
        }
    }
}
