package com.example.app.services;

import com.example.app.interfaces.BookShelfApiRequestServiceInterface;
import com.example.app.models.dtos.Book;
import com.example.app.models.dtos.BookAndStock;
import com.example.app.models.dtos.Token;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class RestTemplateBookShelfService implements BookShelfApiRequestServiceInterface {

    private final RestTemplate restTemplate;
    private final String BOOK_SHELF_SERVICE_URL = "http://localhost:8080/bookshelf/bookstorage/books";
    private final String MAKE_ORDER_URI = "/validate-and-confirm-order";

    public RestTemplateBookShelfService()
    {
        this.restTemplate = new RestTemplateBuilder().build();
    }

    public ResponseEntity<List<Book>> getAllBooksByISBNRequest(Token token, List<String> ISBNs) {
        HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.setContentType(MediaType.APPLICATION_JSON);
        requestHeaders.setBearerAuth(token.getToken());

        return restTemplate.getForEntity(BOOK_SHELF_SERVICE_URL, (Class<List<Book>>) ((Class)List.class), ISBNs);
    }

    public ResponseEntity<List<BookAndStock>> validateOrderStockRequest(Token token, List<BookAndStock> bookAndStockList)
    {
        HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.setContentType(MediaType.APPLICATION_JSON);
        requestHeaders.setBearerAuth(token.getToken());

        HttpEntity<List<BookAndStock>> requestEntity = new HttpEntity<>(bookAndStockList, requestHeaders);

        return restTemplate.postForEntity(BOOK_SHELF_SERVICE_URL+MAKE_ORDER_URI, requestEntity, (Class<List<BookAndStock>>) (Class)List.class);
    }
}
