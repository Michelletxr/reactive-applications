package com.br;
import org.redisson.Redisson;
import org.redisson.api.RedissonReactiveClient;
import org.redisson.config.Config;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.reactive.config.EnableWebFlux;

@SpringBootApplication
@EnableDiscoveryClient
@EnableWebFlux
public class LibraryReactiveApplication {
	//@Autowired
	//private WebSocketHandler webSocketHandler;
	public static void main(String[] args) {
		SpringApplication.run(LibraryReactiveApplication.class, args);
	}

	String instanceId;

	String port;
	@GetMapping("/instanceId")
	public String check_instance() {
		return String.format("Running instance %s in port %s", instanceId, port);
	}


/*	@Bean
	public RouterFunction<ServerResponse> htmlRouter(
			@Value("") Resource html) {
		return route(GET("/"), request
				-> ok().contentType(MediaType.TEXT_HTML).syncBody(html)
		);
	}

	@Bean
	public HandlerMapping webSocketHandlerMapping() {
		Map<String, WebSocketHandler> map = new HashMap<>();
		map.put("/chat", webSocketHandler);

		SimpleUrlHandlerMapping handlerMapping = new SimpleUrlHandlerMapping();
		handlerMapping.setOrder(1);
		handlerMapping.setUrlMap(map);
		return handlerMapping;
	}

	@Bean
	public WebSocketHandlerAdapter handlerAdapter() {
		return new WebSocketHandlerAdapter(webSocketService());
	}

	@Bean
	public WebSocketService webSocketService() {
		return new HandshakeWebSocketService(new ReactorNettyRequestUpgradeStrategy());
	}*/

}

