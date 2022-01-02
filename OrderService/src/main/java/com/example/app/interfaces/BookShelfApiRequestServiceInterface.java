package com.example.app.interfaces;

import com.example.app.models.dtos.Book;
import com.example.app.models.dtos.BookAndStock;
import org.springframework.http.ResponseEntity;

import java.util.LinkedList;
import java.util.List;

public interface BookShelfApiRequestServiceInterface {

    ResponseEntity<List<Book>> getAllBooksByISBNRequest(List<String> ISBNs);

    ResponseEntity<List<BookAndStock>> validateOrderStockRequest(List<BookAndStock> bookAndStockList);

}







