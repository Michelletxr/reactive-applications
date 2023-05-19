package com.br.configuration;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.socket.WebSocketHandler;
import org.springframework.web.reactive.socket.WebSocketSession;
import reactor.core.publisher.Mono;


public class MySocketHandler implements WebSocketHandler {

    @Override
    public Mono<Void> handle(WebSocketSession session)
    {
        return session
                .send( session.receive()
                        .map(msg -> "RECEIVED ON SERVER :: " + msg.getPayloadAsText())
                        .map(session::textMessage)
                );
    }
}
