package com.example.app.services;

import com.example.app.interfaces.AuthorServiceInterface;
import com.example.app.models.dbentities.AuthorEntity;
import com.example.app.models.dtos.Author;
import com.example.app.repositories.AuthorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AuthorService implements AuthorServiceInterface {

    @Autowired
    private AuthorRepository authorRepository;

    @Override
    public List<Author> getAuthors() {
        List<AuthorEntity> authorEntityList = (List<AuthorEntity>) authorRepository.findAll();
        return authorEntityList.stream().map(Author::new).collect(Collectors.toList());
    }

    @Override
    public Optional<Author> getAuthor(int id) {
        return authorRepository.findById(id).map(Author::new);
    }

    @Override
    public Author postAuthor(Author author) throws Exception {
        // Converting Book to BookEntity
        AuthorEntity authorEntity = new AuthorEntity(author);
        try {
            AuthorEntity savedAuthorEntity = authorRepository.save(authorEntity);
            return new Author(savedAuthorEntity);
            //return ResponseEntity.status(HttpStatus.CREATED).body(Map.of("self_uri", url + "/" + savedAuthorEntity.getId(), "parent_url", url, "author", savedAuthorEntity));
        } catch (Exception exception) {
            throw new Exception(exception.getMessage());
            //return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(Map.of("Error cause", exception.getMessage()));
        }
    }

    @Override
    public void deleteAuthor(int id) throws Exception {
        try {
            authorRepository.deleteById(id);
        } catch (DataAccessException dataAccessException) {
            throw new Exception(dataAccessException.getMessage());
            // return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        //return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
