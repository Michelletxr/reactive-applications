package com.br.auth.configuration;

import com.br.auth.model.User;
import com.br.auth.repository.AuthRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;

@Component
public class LoadData implements CommandLineRunner {
    @Autowired
    AuthRepository authRepository;
    @Override
    public void run(String... args) throws Exception {

        Flux<User> booksFlux = (Flux<User>) Flux.just(new User(null,
                        "root", "root", "root", "root@example.com"))
                .flatMap(authRepository::save);

        booksFlux.thenMany(authRepository.findAll())
                .subscribe(System.out::println);

    }
}

