package com.example.app.models.dbentities.primarykeys;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class AuthorsBooksPK implements Serializable {

    @Column(name = "authors_id")
    private int authors_id;
    @Column(name = "books_isbn")
    private String books_isbn;

    protected AuthorsBooksPK() {
    }

    public AuthorsBooksPK(int authors_id, String books_isbn) {
        this.authors_id = authors_id;
        this.books_isbn = books_isbn;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AuthorsBooksPK that = (AuthorsBooksPK) o;
        return authors_id == that.authors_id && Objects.equals(books_isbn, that.books_isbn);
    }

    @Override
    public int hashCode() {
        return Objects.hash(authors_id, books_isbn);
    }

    public void setAuthors_id(int authors_id) {
        this.authors_id = authors_id;
    }

    public int getAuthors_id() {
        return authors_id;
    }

    public void setBooks_ISBN(String books_ISBN) {
        this.books_isbn = books_ISBN;
    }

    public String getBooks_ISBN() {
        return books_isbn;
    }
}
