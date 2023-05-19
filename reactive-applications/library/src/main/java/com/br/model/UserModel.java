package com.br.model;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.UUID;

@Data
@Document
public class UserModel {

    @Id
    private UUID id;
    private String userName;
    private String email;

    @Builder
    public UserModel(UUID id, String userName, String email) {
        this.id = id;
        this.userName = userName;
        this.email = email;
    }
}
