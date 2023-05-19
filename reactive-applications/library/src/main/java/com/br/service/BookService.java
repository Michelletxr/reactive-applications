package com.br.service;
import com.br.model.Book;
import com.br.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import java.util.UUID;
@Service
public class BookService {
    @Autowired
    BookRepository bookRepository;

    public Mono<Book> save(Book book) {
        return bookRepository.save(book);
    }
    public Mono<Book> update(UUID id, Book.BookDto bookDto){
        return bookRepository.findById(id).flatMap( book -> {
            book.setName(bookDto.name());
            book.setGenre(bookDto.genre());
            book.setRating(bookDto.rating());
            book.setNumberPages(bookDto.numberPages());
            book.setId_user(bookDto.id_user());
            bookRepository.save(book);
            return  Mono.just(book);
        });
    }

    public Mono<Book> findBookById(UUID id) {
        return bookRepository.findById(id);
    }
    public Flux<Book> findAll() {
        return bookRepository.findAll();
    }
    public Mono<Boolean> delete(UUID id) {
        return bookRepository.findById(id)
                .flatMap(book ->
                    bookRepository.delete(book).then(Mono.just(true))
                ).defaultIfEmpty(false);
    }

    public Book buildBookDtoToBook(Book.BookDto bookDto){
        return Book.builder()
                .name(bookDto.name())
                .genre(bookDto.genre())
                .numberPages(bookDto.numberPages())
                .rating(bookDto.rating())
                .id_user(bookDto.id_user())
                .build();
    }

    public Mono<Void> deleteAll() {
        return bookRepository.deleteAll();
    }
}