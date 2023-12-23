package com.br.controller;

import com.br.model.Book;
import com.br.model.UserModel;
import com.br.service.BookServiceCache;
import com.br.service.UserServiceCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import java.util.UUID;

@RestController
@RequestMapping("/api/cache")
public class CacheController {

    @Autowired
    BookServiceCache bookServiceCache;

    @Autowired
    UserServiceCache userServiceCache;

    @GetMapping(value = "userCache")
    public Flux<UserModel> findAllUserCache(){return userServiceCache.getAll();}
    //cache-aside
    @GetMapping(value = "bookCache")
    public Flux<Book> findALlBookCache(){
        return bookServiceCache.getAll();
    }
    @GetMapping(value = "bookCache/{id}")
    public Mono<Book> findBookIdCache(@PathVariable UUID id){
        return bookServiceCache.get(id);
    }
    @DeleteMapping(value = "bookCache/{id}")
    public Mono<Void> deleteBookCache(@PathVariable UUID id){
        return bookServiceCache.delete(id);
    }

}
