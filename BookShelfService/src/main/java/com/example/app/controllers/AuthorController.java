package com.example.app.controllers;

import com.example.app.interfaces.AuthorServiceInterface;
import com.example.app.models.dtos.Author;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/bookshelf/bookstorage/authors")
public class AuthorController {

    @Autowired
    private AuthorServiceInterface authorService;

    @GetMapping(value = "")
    public ResponseEntity<List<Author>> getAuthors() {
        return ResponseEntity.status(HttpStatus.OK).body(authorService.getAuthors());
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<Author> getAuthor(@PathVariable int id) {
        Optional<Author> authorOptional = authorService.getAuthor(id);
        if(authorOptional.isEmpty())
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        else
            return ResponseEntity.status(HttpStatus.OK).body(authorOptional.get());
    }

    @PostMapping(value = "")
    public ResponseEntity<Author> postAuthor(@RequestBody Author author) {
        // Find the URI and URL
        try {
            Author addedAuthor = authorService.postAuthor(author);
            return ResponseEntity.status(HttpStatus.CREATED).body(addedAuthor);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).build();
        }
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<?> deleteAuthor(@PathVariable int id) {
        try {
            authorService.deleteAuthor(id);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
}
