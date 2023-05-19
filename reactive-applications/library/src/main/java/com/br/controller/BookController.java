package com.br.controller;
import com.br.model.Book;
import com.br.service.BookService;
import com.br.service.ThreadsService;
import org.springframework.beans.factory.annotation.Autowired;
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
    @Autowired
    ThreadsService threadsService;

    @GetMapping(value = "book", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<String> findAllBooks(){
        return serviceBooks.findAll()
                .map(e-> "livro: " + e.getName());
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

    @GetMapping(value = "book/threads", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<String> getBooksThreads(){
       return threadsService.getBooksProjectLoom();
   }
    /***public Flux<String> getBooksThreads(){
        return threadsService.getBooksParallel();
    }**/
}
