package com.br.controller;
import com.br.model.Book;
import com.br.model.Author;
import com.br.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import java.util.UUID;

@RestController
@RequestMapping("/api/lib")
public class BookController {
    @Autowired
    BookService serviceBooks;

    @Value("${server.port}")
    String port;
    @GetMapping(value="portService")
    public String check_instance() {
        return String.format("library running instance in port %s", port);
    }
    @GetMapping(value = "book")
    public Flux<Book.BookDto> findAllBooks(){
        return serviceBooks.findAll();
    }

    @GetMapping(value = "book/{id}", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Mono<ResponseEntity> findBook(@PathVariable UUID id){
        return serviceBooks.findBookById(id)
                .map(book -> new ResponseEntity(book, HttpStatus.OK))
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @PutMapping(value = "book/{id}", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Mono<Book> updateBook(@PathVariable UUID id, @RequestBody Book.BookDto bookRequest){
        return serviceBooks.update(id, bookRequest);
    }

    @PostMapping(value = "book", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<Book> saveBook(@RequestBody @Validated Book.BookDto bookDto){
        return serviceBooks.save(serviceBooks.buildBookDtoToBook(bookDto));
    }

    @DeleteMapping(value = "book/{id}", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Mono deleteBook(@PathVariable UUID id){
            return serviceBooks.delete(id);
    }

    @DeleteMapping(value = "book")
    public Mono<Void> deleteAllBook(){
        return serviceBooks.deleteAll();
    }


}
