package com.br.repository;

import com.br.model.Author;
import com.br.model.Book;
import org.springframework.data.mongodb.repository.Aggregation;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Flux;

import java.util.UUID;
public interface BookRepository extends ReactiveMongoRepository<Book, UUID> {

    @Aggregation("{ $group : { _id : $id_user, nomes_livros : { $push : $name } } }")
    Flux<Author> groupById_user();
}
