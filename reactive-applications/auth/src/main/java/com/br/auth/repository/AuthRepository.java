package com.br.auth.repository;
import com.br.auth.model.User;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Flux;

import java.util.UUID;

public interface AuthRepository extends ReactiveMongoRepository<User, UUID> {
    //Mono<User> findByUserName(String username);
    Flux<User> findByLogin(String login);
}


