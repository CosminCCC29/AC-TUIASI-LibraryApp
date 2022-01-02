package com.example.app.interfaces;

import com.example.app.models.dtos.Author;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface AuthorServiceInterface {
    List<Author> getAuthors();
    Optional<Author> getAuthor(int id);
    Author postAuthor(Author author) throws Exception;
    void deleteAuthor(int id) throws Exception;
}
