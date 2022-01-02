package com.example.app.models.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Book {

    private String ISBN;
    private String title;
    private String genre;
    private String publisher;
    private int year;
    private double price;
    private String description;
    private int stock;
}
