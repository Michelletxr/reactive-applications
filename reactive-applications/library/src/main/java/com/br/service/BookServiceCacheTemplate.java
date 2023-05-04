package com.br.service;

import com.br.model.Book;
import com.br.repository.BookRepository;
import com.br.util.CacheTemplate;
import org.redisson.api.RMapReactive;
import org.redisson.api.RedissonReactiveClient;
import org.redisson.codec.TypedJsonJacksonCodec;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Service
public class BookServiceCacheTemplate extends CacheTemplate<UUID, Book> {
    @Autowired
    BookRepository  repository;

    private RMapReactive<UUID, Book> map;

    public BookServiceCacheTemplate(RedissonReactiveClient client) {
       this.map = client.getMap("/books/", new TypedJsonJacksonCodec(UUID.class, Book.class));

    }

    @Override
    protected Mono<Book> getFromSource(UUID id) {
        return repository.findById(id);
    }

    @Override
    protected Mono<Book> getFromCache(UUID uuid) {
         return map.get(uuid);
    }

    @Override
    protected Mono<Book> updateSource(UUID id, Book book) {
        return this.repository.findById(id)
                .doOnNext(p -> book.setId(id))
                .flatMap(p -> this.repository.save(book));
    }

    @Override
    protected Mono<Book> updateCache(UUID id, Book book) {
        return this.map.fastPut(id, book).thenReturn(book);
    }

    @Override
    protected Mono<Void> deleteFromSource(UUID id) {
        return this.repository.deleteById(id);
    }

    @Override
    protected Mono<Void> deleteFromCache(UUID id) {
        return this.map.fastRemove(id).then();
    }
}


