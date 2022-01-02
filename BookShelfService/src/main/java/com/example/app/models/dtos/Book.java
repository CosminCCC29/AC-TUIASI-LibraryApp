package com.example.app.models.dtos;

import com.example.app.models.dbentities.BookEntity;
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

    public Book(BookEntity bookEntity) {
        this.ISBN = bookEntity.getISBN();
        this.title = bookEntity.getTitle();
        this.genre = bookEntity.getGenre();
        this.publisher = bookEntity.getPublisher();
        this.year = bookEntity.getYear();
        this.price = bookEntity.getPrice();
        this.description = bookEntity.getDescription();
        this.stock = bookEntity.getStock();
    }

    @Override
    public String toString() {
        String bookToString = "{" +
                "'ISBN':'" + ISBN + '\'' +
                ",'title':'" + title + '\'' +
                ",'genre':'" + genre + '\'' +
                ",'publisher':'" + publisher + '\'' +
                ",'year':" + year +
                ",'price':" + price +
                ",'description':'" + description + '\'' +
                '}';
        return bookToString.replace('\'', '"');
    }
}
