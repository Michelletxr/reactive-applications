package com.br.auth.security;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.br.auth.model.User;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.util.Date;
@Component
public class JWTConfig {
    public static final String SECRET = "e25485f8-48c7-45ae-9312-f785e85d7727";
    public static final long EXPIRATION_TIME = 3600_000; //1 hours
    public static final String TOKEN_PREFIX = "Bearer ";
    public static final String HEADER_STRING = "Authorization";

    public String  generateTokenAcess(User user){
        String token = JWT.create()
                .withSubject(user.getUserName())
                .withExpiresAt(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .sign(Algorithm.HMAC512(SECRET));
        return token;
    }
}

