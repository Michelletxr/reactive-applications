package com.br.service;

import com.br.model.UserModel;
import com.br.repository.UserRepository;
import com.br.util.CacheTemplate;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.redisson.api.RMapReactive;
import org.redisson.api.RedissonReactiveClient;
import org.redisson.codec.TypedJsonJacksonCodec;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.support.WebClientAdapter;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import java.util.UUID;

@Service
public class UserServiceCache extends CacheTemplate<UUID, UserModel> {
    @Autowired
    WebClient webClientAuth  = WebClient.create("http://localhost:8080/auth-server");
    HttpServiceProxyFactory httpServiceProxyFactoryAuth = HttpServiceProxyFactory
            .builder(WebClientAdapter.forClient(webClientAuth))
            .build();
    AuthService authService = httpServiceProxyFactoryAuth
            .createClient(AuthService.class);

    @Autowired
    UserRepository repository;

    private RMapReactive<UUID, UserModel> map;

    public UserServiceCache(RedissonReactiveClient client) {
        this.map = client.getMap("/users-asid", new TypedJsonJacksonCodec(UUID.class, UserModel.class));

    }
    @Override
    protected Mono<UserModel> getFromSource(UUID uuid) {return null;}

    @Override
    protected Mono<UserModel> getFromCache(UUID uuid) {return null;}

    @Override
    protected Mono<UserModel> updateSource(UUID id, UserModel userModel) {
        return this.repository.findById(id)
                .doOnNext(p -> userModel.setId(id))
                .flatMap(p -> this.repository.save(userModel));
    }

    @Override
    protected Mono<UserModel> updateCache(UUID id, UserModel userModel) {
        System.out.println("update cache");
        return this.map.fastPut(id, userModel).thenReturn(userModel);
    }

    @Override
    protected Mono<Void> deleteFromSource(UUID uuid) {return null;}

    @Override
    protected Mono<Void> deleteFromCache(UUID uuid) {return null;}

    @Override
    protected Flux<UserModel> getAllFromCache() {return map.valueIterator();}

    @Override
    protected Flux<UserModel> getAllFromSource() {
        return authService.getAllUsers()
                .flatMap(e-> {
                    UserModel user = buildStringValue(e);
                    return updateCache(user.getId(), user);
                });
    }

    private UserModel buildStringValue(String json) {
        ObjectMapper mapper = new ObjectMapper();
        UserModel results = null;
        try {
            results = mapper.readValue(json, UserModel.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        return (UserModel) results;
    }
}
