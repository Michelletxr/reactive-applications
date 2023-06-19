package com.br.model;

import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;
import java.util.UUID;

@Document
public class Author {
    String id_user;
    List<String> nomes_livros;
}
