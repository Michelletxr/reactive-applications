package com.br.service;
import com.br.model.UserModel;
import com.br.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.support.WebClientAdapter;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.publisher.Sinks;
import java.util.UUID;

@Service
public class UserService {
    @Autowired
    WebClient webClientAuth  = WebClient.create("http://localhost:8080/auth-server");
    @Autowired
    WebClient webClientMessage = WebClient.create("http://localhost:8080/messager-server-reactive");;
    HttpServiceProxyFactory httpServiceProxyFactoryAuth = HttpServiceProxyFactory
            .builder(WebClientAdapter.forClient(webClientAuth))
            .build();
    HttpServiceProxyFactory httpServiceProxyFactoryMessage = HttpServiceProxyFactory
            .builder(WebClientAdapter.forClient(webClientMessage))
            .build();
    AuthService authService = httpServiceProxyFactoryAuth
            .createClient(AuthService.class);
    NotificationService notificationService = httpServiceProxyFactoryMessage
            .createClient(NotificationService.class);

    @Autowired
    private UserRepository userRepository;
    Sinks.One<Object> sinkone = Sinks.one();

    private String emailFrom = "michelle.teixeira.124@ufrn.edu.br";
    private String title = "Registo";
    private String text= "Seja bem-vindo a livraria virtual!";

    public record UserRegister(String userName, String login, String password, String email){}
    public record SendEmail(String emailFrom, String emailTo, String title, String text){}

    //checar serviço de autentificação
    public Mono<String> authCheck(){
        return authService.checkInstance();
    };

    //mandar notificação de boas vindas
    public Mono<String> sendNotifications(String emailTo){
        return notificationService.sendNotifications(new SendEmail(emailFrom, emailTo, title, text));
    }

    //cadastro no sistema
    public Mono<String> registryUser(UserRegister user){
        return authService.registryUser(user);
    }

    //pegar usuario no sistema
    public Mono<String> getUser(UUID id){
        return authService.getUser(id);
    }

    public Flux<String> getAllUsers(){ return authService.getAllUsers(); }

    public Flux<UserModel> findAll() {
        return userRepository.findAll();
    }
    public Mono<UserModel> save(UserModel user){
        System.out.println("user id:"+ user.getId());
        return userRepository.save(user);
    }
}
