package com.br.auth.model;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Objects;
import java.util.UUID;
@Data
@Document
public class User{
    @Id
    private UUID id;
    private String userName;
    @Indexed(unique = true)
    private String login;
    private String password;
    private String email;
    @Builder
    public User(UUID id, String userName, String login, String password, String email) {
        this.id = Objects.isNull(id)? UUID.randomUUID(): id;
        this.userName = userName;
        this.login = login;
        this.password = password;
        this.email = email;
    }

    public record UserRecord(String userName, String login, String password, String email){}
    public record UserResponse(UUID id, String userName, String email){};
}
