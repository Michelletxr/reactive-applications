package com.br.controller;
import com.br.model.UserModel;
import com.br.service.UserService;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

@RestController
@RequestMapping("/api/lib/user")

public class UserController {

    @Autowired
    UserService userService;

    @GetMapping( value = "auth-check", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Mono<String> authCheck(){
        return userService.authCheck();
    }
    @GetMapping( produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<UserModel> findAllUsers(){
        return userService.findAll();
    }

    @GetMapping(value="{id}", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Mono<String> getUser(@PathVariable UUID id){
        return userService.getUser(id).doOnNext(e-> System.out.println("resultado" + e));
    }

    @GetMapping(value="users", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<String> getAllUsers(){
        return userService.getAllUsers();
    }


    @PostMapping(value="register", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Mono<String> register(@RequestBody UserService.UserRegister user){
        return userService.registryUser(user)
                .flatMap(e-> {
                    try {
                        Gson gson = new Gson();
                        String jsonString = e.replaceAll("data:", "");
                        UserModel object = gson.fromJson(jsonString, UserModel.class);
                        return userService.save(object)
                                .then(userService.sendNotifications(user.email()));
                    } catch (Exception ex) {
                        throw new RuntimeException(ex);
                    }
                }).doOnNext(e-> System.out.println("resultado" + e));
    }
}
