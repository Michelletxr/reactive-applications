package com.br.controller;
import com.br.model.UserModel;
import com.br.service.UserCache;
import com.br.service.UserService;
import com.google.gson.Gson;
import io.github.resilience4j.retry.annotation.Retry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

@RestController
@RequestMapping("/api/lib/user")

public class UserController {

    @Autowired
    UserService userService;
    @Autowired
    UserCache userCache;


    @GetMapping(value="{id}", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Mono<String> getUser(@PathVariable UUID id){
        return userService.getUser(id).doOnNext(e-> System.out.println("resultado" + e));
    }

    @GetMapping( produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<UserModel> findAllUsers(){
        return userService.findAll();
    }

    @CircuitBreaker(name= "circuitBreakerService")
    //@Retry(name = "retfryService")
    @GetMapping( value = "auth-check/resilience", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Mono<String> resilienteAuthCheck(){
        return userService.authCheck();
    }

    @GetMapping( value = "auth-check", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Mono<String> authCheck(){
        return userService.authCheck();
    }

    @GetMapping(value="users", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    @Retry(name = "retfryService", fallbackMethod = "fallbackCache")
    public Flux<String> getAllUsers(){
        return userService.getAllUsers();
    }

    @CircuitBreaker(name= "circuitBreakerService", fallbackMethod = "fallbackRegister")
    @Retry(name = "retryService", fallbackMethod = "fallbackRegister")
    @PostMapping(value="register", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Mono<String> register(@RequestBody UserService.UserRegister user){
        return userService.registryUser(user)
                .flatMap(e-> {
                    try {
                        Gson gson = new Gson();
                        String jsonString = e.replaceAll("data:", "");
                        UserModel object = gson.fromJson(jsonString, UserModel.class);
                        return userService.save(object)
                                .then(userService.sendNotifications(user.email()))
                                .then(Mono.just("Usuário "+object.getUserName()+ "registrado!"));
                    } catch (Exception ex) {
                        throw new RuntimeException(ex);
                    }
                }).doOnNext(e-> System.out.println("resultado" + e));
    }

    public Mono<String> fallback(Throwable error){
        System.out.println("falback test");
        return Mono.just("fallback: Serviço de autentificação insdisponível");

    }

    public Flux<UserModel> fallbackCache(Throwable error){
        System.out.println("falback: carregando usuários do cache;");
        return userCache.getAll();
    }

    public Mono<String> fallbackRegister(Throwable error){
        System.out.println("falback: erro no registro de usário;");
        return Mono.just("No momento não é possível registrar novo usário, tente mais tarde.");

    }
}
