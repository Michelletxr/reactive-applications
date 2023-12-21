package com.br.repository;

import com.br.model.Book;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

import java.util.UUID;
public interface BookRepository extends ReactiveMongoRepository<Book, UUID> {
}
