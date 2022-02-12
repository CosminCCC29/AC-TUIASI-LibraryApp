package com.example.app.interfaces;

import com.example.app.models.dtos.Book;
import com.example.app.models.dtos.BookAndStock;
import com.example.app.models.dtos.Token;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface BookShelfApiRequestServiceInterface {

    ResponseEntity<List<Book>> getAllBooksByISBNRequest(Token token, List<String> ISBNs);

    ResponseEntity<List<BookAndStock>> validateOrderStockRequest(Token token, List<BookAndStock> bookAndStockList);

}







