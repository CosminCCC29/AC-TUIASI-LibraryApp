package com.example.app.services;

import com.example.app.errorhandlers.BookShelfApiErrorHandler;
import com.example.app.interfaces.BookShelfApiRequestServiceInterface;
import com.example.app.models.dtos.Book;
import com.example.app.models.dtos.BookAndStock;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class RestTemplateBookShelfService implements BookShelfApiRequestServiceInterface {

    private final RestTemplate restTemplate;
    private final String bookShelfServiceUrl = "http://localhost:8080/bookshelf/bookstorage/books";
    private final String makeOrderUri = "/validate-order-stock";

    public RestTemplateBookShelfService()
    {
        this.restTemplate = new RestTemplateBuilder().build();
    }

    public ResponseEntity<List<Book>> getAllBooksByISBNRequest(List<String> ISBNs) {
        return restTemplate.getForEntity(bookShelfServiceUrl, (Class<List<Book>>) ((Class)List.class), ISBNs);
    }

    public ResponseEntity<List<BookAndStock>> validateOrderStockRequest(List<BookAndStock> bookAndStockList)
    {
        HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<List<BookAndStock>> requestEntity = new HttpEntity<>(bookAndStockList, requestHeaders);

        return restTemplate.postForEntity(bookShelfServiceUrl+makeOrderUri, requestEntity, (Class<List<BookAndStock>>) (Class)List.class);
    }
}
