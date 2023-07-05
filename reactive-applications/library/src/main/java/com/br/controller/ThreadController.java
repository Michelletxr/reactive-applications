package com.br.controller;

import com.br.service.ThreadsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/thread")
public class ThreadController {
    @Autowired
    ThreadsService threadsService;
    //threads
   /* @GetMapping(value = "book/threads", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<String> getBooksThreads(){
       return threadsService.getBooksProjectLoom();
   }*/
    /***public Flux<String> getBooksThreads(){
     return threadsService.getBooksParallel();
     }**/
}
