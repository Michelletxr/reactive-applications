package com.br.controller;

import com.br.model.Book;
import com.br.service.BookServiceCacheTemplate;
import com.br.service.BookServiceCacheTemplateClientSide;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

@RestController
@RequestMapping("/api/cache")
public class CacheController {

    @Autowired
    BookServiceCacheTemplate serviceCache;

    @Autowired
    BookServiceCacheTemplateClientSide serviceClientCache;

    //cache-aside
    @GetMapping(value = "bookCache")
    public Flux<Book> findALlBookCache(){
        return serviceCache.getAll();
    }
    @GetMapping(value = "bookCache/{id}")
    public Mono<Book> findBookIdCache(@PathVariable UUID id){
        return serviceCache.get(id);
    }

    @DeleteMapping(value = "bookCache/{id}")
    public Mono<Void> deleteBookCache(@PathVariable UUID id){
        return serviceCache.delete(id);
    }

    @GetMapping(value = "bookClientCache")
    public Flux<Book> findALlBookClientCache(){
        return serviceClientCache.getAll();
    }
    @GetMapping(value = "bookClientCache/{id}")
    public Mono<Book> findBookIdClientCache(@PathVariable UUID id){
        return serviceClientCache.get(id);
    }

    @DeleteMapping(value = "bookClientCache/{id}")
    public Mono<Void> deleteBookClientCache(@PathVariable UUID id){
        return serviceClientCache.delete(id);
    }
}
