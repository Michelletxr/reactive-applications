package com.br.service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.service.annotation.GetExchange;
import org.springframework.web.service.annotation.PostExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import java.util.UUID;

public interface AuthService {
    @GetExchange("/api/auth/{id}")
    Mono<String> getUser(@PathVariable UUID id);

    @GetExchange("/api/auth/user")
    Flux<String> getAllUsers();

    @PostExchange("/api/auth/user")
    Mono<String> registryUser(@RequestBody UserService.UserRegister user);

    @GetExchange("/api/auth")
    Mono<String> checkInstance();

}
