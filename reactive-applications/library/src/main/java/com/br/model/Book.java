package com.br.model;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.util.UUID;

@Data
@Document
@AllArgsConstructor
@NoArgsConstructor
public class Book {
    @Id
    private UUID id;
    private String name;
    private String genre;
    private int numberPages;
    private float rating;
    private UUID id_user;

    @Builder
    public Book(UUID id, UUID id_user, String name, String genre, int numberPages, float rating) {
        this.id = id == null? UUID.randomUUID(): id;
        this.name = name;
        this.genre = genre;
        this.numberPages = numberPages;
        this.rating = rating;
        this.id_user = id_user;
    }
    public record BookDto(String name, UUID id_user, String genre, int numberPages, float rating){

    }




}
