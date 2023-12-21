package com.br.service;
import com.br.model.UserModel;
import com.br.repository.UserRepository;
import com.br.util.Supliers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.support.WebClientAdapter;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import java.util.UUID;

@Service
public class UserService {
    @Autowired
    Supliers supliers;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    UserCache userCache;
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

    @Value("${EMAIL_SENDER}")
    private String emailSender;
    private String title = "Registro de usuário";
    private String text= "Seja bem-vindo a livraria virtual!";

    public record UserRegister(String userName, String login, String password, String email){}
    public record SendEmail(String emailFrom, String emailTo, String title, String text){}

    //checar serviço de autentificação
    public Mono<String> authCheck(){
        return authService.checkInstance();
    };
    //mandar notificação de boas vindas
    public Mono<String> sendNotifications(String emailTo){
        return notificationService.sendNotifications(new SendEmail(emailSender, emailTo, title, text));
    }
    //cadastro no sistema
    public Mono<String> registryUser(UserRegister user){
        return authService.registryUser(user);
    }
    //pegar usuario no sistema
    public Mono<String> getUser(UUID id){
        return authService.getUser(id);
    }

    public Flux<String> getAllUsers(){return authService.getAllUsers();}

    public Flux<UserModel> findAll() {return userRepository.findAll();}

    public Mono<UserModel> save(UserModel user){
        System.out.println("user id:"+ user.getId());
        return userRepository.save(user);
    }

    public Mono<Boolean> processNewUserMessage(String emailTo){
        return supliers.processMessage(emailSender, emailTo, title, text);
    }

   // @CircuitBreaker(name= "circuitBreakerService", fallbackMethod = "fallbackCircuitBreaker")
    public Mono<UserModel> Response() {
        return webClientAuth.get()
                .uri("http://localhost:8080/auth-server")
                .retrieve()
                .bodyToMono(UserModel.class)
                .doOnError(ex -> {
                    throw new RuntimeException("the exception message is - "+ex.getMessage());
                });
    }

}
