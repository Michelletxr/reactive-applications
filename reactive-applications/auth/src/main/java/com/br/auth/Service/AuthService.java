package com.br.auth.Service;
import com.br.auth.model.User;
import com.br.auth.repository.AuthRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import java.util.UUID;

@Service
public class AuthService {
    @Autowired
    AuthRepository repository;

    public record UserResponse(UUID id, String userName, String email){};
    public Mono<UserResponse> findById(UUID id){
         return repository.findById(id)
                 .flatMap(user ->
                         Mono.just(new UserResponse(user.getId(), user.getUserName(), user.getEmail()))
                 );

    }
    public Mono<User> Save(User user){
         return  repository.save(user);
    }

    public Flux<User> findUserByLogin(String login){
        return repository.findByLogin(login);
    }

    public Mono<Boolean> delete(UUID id){
        return repository.findById(id)
                .flatMap(user -> repository.delete(user)
                        .then(Mono.just(true)))
                .defaultIfEmpty(false);
    }

    public Mono<User> verifyLogin(String username, String password){
        /*return repository.findBy(username).flatMap( user -> {
            if(user.getPassword() == password){
                return Mono.just(user);
            }else{
                return Mono.error(new RuntimeException("username e senha não conferem"));
            }
        });*/
        return null;
    }

    public User buildUserResponseToUser(User.UserRecord user){
        return User.builder()
                .userName(user.userName())
                .email(user.email())
                .login(user.login())
                .password(user.password())
                .build();
    }

    public Mono<UserResponse> buildUserResponse(Object user){
        User userResponse = (User) user;
        return Mono.just(new UserResponse(userResponse.getId(), userResponse.getUserName(), userResponse.getEmail()));
    }

    public Flux<Object> findAll() {
        return repository.findAll().flatMap( e -> buildUserResponse(e));
    }
}
