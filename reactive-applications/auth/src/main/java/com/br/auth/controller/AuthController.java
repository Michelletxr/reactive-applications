package com.br.auth.controller;
import com.br.auth.Service.AuthService;
import com.br.auth.model.User;
import com.br.auth.security.JWTConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import java.util.UUID;

@RestController
@RequestMapping(value = "/api/auth")
public class AuthController {
    @Autowired
    private AuthService service;
    @Autowired
    private JWTConfig jwtConfig;
    record Login(String username, String password){}

    @PostMapping(value= "/login", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Mono<ResponseEntity<String>> login(@RequestBody  Login login){
        return service.verifyLogin(login.username, login.password).map(
                user -> new ResponseEntity<>(jwtConfig.generateTokenAcess(user), HttpStatus.OK))
                .defaultIfEmpty(ResponseEntity.badRequest().build());
    }

    @GetMapping(value = "user", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<User> findAllUsers(){
        return service.findAll();
    }

    @PostMapping(value = "user",produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<User> save(@RequestBody User.UserRecord user){
        return service.Save(service.buildUserResponseToUser(user));
    }

    @GetMapping(value = "/{id}", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Mono<AuthService.UserResponse> findById(@PathVariable UUID id){
        return service.findById(id);
    }

    @GetMapping(value = "/user/{login}", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<User> findByLogin(@PathVariable String login){
        return service.findUserByLogin(login);
    }

    @DeleteMapping(value = "/{id}", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Mono<Boolean> delete(@PathVariable UUID id){
        return service.delete(id);
    }
}
