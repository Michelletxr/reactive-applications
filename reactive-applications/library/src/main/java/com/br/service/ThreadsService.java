package com.br.service;

import com.br.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;

@Service
public class ThreadsService {
    @Autowired
    BookRepository bookRepository;
    private ExecutorService executorService = Executors.newThreadPerTaskExecutor(new ThreadFactory() {
        @Override
        public Thread newThread(Runnable r) {
            Thread t = Thread.ofVirtual().name("Virtual-Thread em ação Num:"+System.nanoTime()).unstarted(r);
            //t.setDaemon(true);
            //t.setPriority(Thread.MIN_PRIORITY);
            return t;
        }
    });
    public Flux<String> getBooksProjectLoom(){
        return bookRepository.findAll()
                .map(b ->
                        "Livro retornado: " + b.getName() + " |  Thread: " + Thread.currentThread()
                )
                .subscribeOn(Schedulers.fromExecutorService(executorService));
    }

    public Flux<String> getBooksParallel(){
        return bookRepository.findAll()
                .map(b ->
                        "Livro retornado: " + b.getName() + " |  Thread: " + Thread.currentThread()
                )
                .subscribeOn(Schedulers.parallel());
    }


}


