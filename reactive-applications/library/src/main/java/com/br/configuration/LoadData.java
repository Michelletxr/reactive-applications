package com.br.configuration;
import com.br.model.Book;
import com.br.repository.BookRepository;
import com.br.repository.UserRepository;
import com.br.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

//preciso terminar o chat
//adicionar spring cloud
//

@Component
public class LoadData implements CommandLineRunner {
    @Autowired
    BookRepository bookRepository;

    @Autowired
    UserService userService;

    @Autowired
    UserRepository userRepository;
    @Override
    public void run(String... args) throws Exception {

        List<Book> books = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            books.add(new Book(UUID.randomUUID(), UUID.randomUUID(),
                    "livro_teste", "suspense", 10, 5));

        }

        Flux<Book> booksFlux = (Flux<Book>) Flux.fromIterable(books)
                .flatMap(bookRepository::save);
        booksFlux.thenMany(bookRepository.findAll())
                .subscribe(System.out::println);
        /**
        Flux<Book> booksFlux = (Flux<Book>) Flux.just(new Book(UUID.randomUUID(),
                "livro_teste", "suspense", 10, 5))
                .flatMap(bookRepository::save);
        Mono<User> user = userService.getUser("root").flatMap(userRepository::save);

        user.flatMap(user1 -> {
            Flux.just(new Book(UUID.randomUUID(),
                            "livro_teste", "suspense", 10, 5))
                    .flatMap(bookRepository::save);
        });
        booksFlux.thenMany(bookRepository.findAll())
                .subscribe(System.out::println);**/

    }
}
