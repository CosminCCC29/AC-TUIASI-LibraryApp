package com.example.app.models.dbentities;

import com.example.app.models.dtos.Book;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConstructorBinding;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "books")
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class BookEntity {

    @Id
    @Column(length = 13)
    private String ISBN;
    @NotNull(message = "A book title must be provided.")
    @Column(length = 80)
    private String title;
    @NotNull(message = "A book publisher must be provided.")
    @Column(length = 60)
    private String publisher;
    @NotNull(message = "A book genre must be provided.")
    @Column(length = 40)
    private String genre;
    @NotNull(message = "A book year of publication must be provided.")
    private int year;
    @NotNull(message = "A book price must be provided.")
    private double price;
    private String description;
    @Column(columnDefinition = "integer default 0")
    @NotNull(message = "A book stock must be provided.")
    private int stock;

    public BookEntity(Book book) {
        this.ISBN = book.getISBN();
        this.title = book.getTitle();
        this.genre = book.getGenre();
        this.publisher = book.getPublisher();
        this.year = book.getYear();
        this.price = book.getPrice();
        this.description = book.getDescription();
        this.stock = book.getStock();
    }
}
