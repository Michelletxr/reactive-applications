package com.br.service;

import com.br.model.UserModel;
import com.br.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.publisher.Sinks;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;

@Service
public class UserService {
    @Autowired
    WebClient webClientAuth = WebClient.create("http://localhost:8082/api/auth/");

    @Autowired
    WebClient webClientEmail = WebClient.create("http://localhost:8083/api/notifications/send-email");

    @Autowired
    private UserRepository userRepository;

    Sinks.One<Object> sinkone = Sinks.one();

    private String emailFrom = "michelle.teixeira.124@ufrn.edu.br";
    private String title = "Registo";
    private String text= "Seja bem-vindo a livraria virtual!";

    public record UserRegister(String userName, String login, String password, String email){}
    public record SendEmail(String emailFrom, String emailTo, String title, String text){}

    //mandar notificação de boas vindas
    public Mono<String> sendNotifications(String emailTo){
        return  this.webClientEmail.post()
                .uri("http://localhost:8083/api/notifications/send-email")
                .body(Mono.just(new SendEmail(emailFrom, emailTo, title, text)), SendEmail.class)
                .retrieve()
                .bodyToMono(String.class)
                .doOnNext(e-> System.out.println(e));
    }

    //cadastro no sistema
    public Mono<String> registryUser(UserRegister user){
        return this.webClientAuth.post()
                .uri("http://localhost:8082/api/auth/user")
                .body(Mono.just(user), UserRegister.class)
                .retrieve()
                .bodyToMono(String.class);
    }

    //pegar usuario no sistema
    public Mono<String> getUser(UUID id){
        return this.webClientAuth
                .get()
                .uri("http://localhost:8082/api/auth/0118ab11-180c-4812-b433-04cb1663476b")
                .retrieve()
                .bodyToMono(String.class)
                .doOnNext(e-> System.out.println("resultado1" + e));
    }

    public Flux<UserModel> findAll() {
        return userRepository.findAll();
    }
    public Mono<UserModel> save(UserModel user){
        System.out.println("user id:"+ user.getId());
        return userRepository.save(user);
    }
}
