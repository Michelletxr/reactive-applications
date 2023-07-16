package com.imd.function.Configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.function.Function;
import java.util.function.Supplier;

@Configuration
public class FunctionConfig {
    @Bean
    public Function<String, String> log(){
        return str -> {
            return "LOG:" +  LocalDateTime.now()+ ":" + str;
        };
    }


}
