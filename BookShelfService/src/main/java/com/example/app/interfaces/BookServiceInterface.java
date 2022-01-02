package com.example.app.interfaces;

import com.example.app.models.dbentities.BookEntity;
import com.example.app.models.dtos.AuthorBookJoin;
import com.example.app.models.dtos.Book;
import com.example.app.models.dtos.BookAndStock;
import com.example.app.repositories.query.BasicQueryCriteria;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface BookServiceInterface {
    List<Book> getBooks(BasicQueryCriteria queryCriteria);
    List<Book> getAllBooksByISBN(List<String> ISBNs);
    Optional<Book> getBook(String ISBN);
    Book putBook(String ISBN, Book book) throws Exception;
    void deleteBook(String ISBN) throws Exception;
    List<AuthorBookJoin> postAuthorsToBook(String ISBN, List<Integer> authors_ids) throws Exception;
    List<Book> updateBooksStock(List<BookAndStock> bookAndStockList, Boolean addingStock) throws Exception;
}
