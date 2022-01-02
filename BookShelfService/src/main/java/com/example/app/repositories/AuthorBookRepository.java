package com.example.app.repositories;

import com.example.app.models.dbentities.AuthorBookEntity;
import com.example.app.models.dbentities.primarykeys.AuthorsBooksPK;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface AuthorBookRepository extends CrudRepository<AuthorBookEntity, AuthorsBooksPK> {

    @Query
    (value = "SELECT * FROM authors_books WHERE books_isbn = :isbn", nativeQuery = true)
    Iterable<AuthorBookEntity> findBookAuthors(@Param(value = "isbn") String isbn);


}
