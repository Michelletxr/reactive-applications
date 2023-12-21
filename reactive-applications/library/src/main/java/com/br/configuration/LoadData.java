package com.br.configuration;
import com.br.model.Book;
import com.br.repository.BookRepository;
import com.br.repository.UserRepository;
import com.br.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

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
        Flux<Book> bookFlux = bookRepository.findAll();

        bookFlux.collectList()
                .subscribe(bookList -> {
                    if (bookList.isEmpty()) {
                        books.add(new Book(UUID.randomUUID(), UUID.randomUUID(),
                                "livro_1", "suspense", 10, 5));
                        books.add(new Book(UUID.randomUUID(), UUID.randomUUID(),
                                "livro_2", "suspense", 20, 10));
                        books.add(new Book(UUID.randomUUID(), UUID.randomUUID(),
                                "livro_3", "suspense", 30, 8));
                        System.out.println("haaaa");
                        Flux.fromIterable(books)
                                .flatMap(bookRepository::save)
                                .subscribe(); // Adiciona o subscribe para ativar a operação de salvamento
                    } else {
                        Flux.fromIterable(bookList)
                                .doOnNext(book -> System.out.println(book))
                                .subscribe();
                    }
                });


    }

}



