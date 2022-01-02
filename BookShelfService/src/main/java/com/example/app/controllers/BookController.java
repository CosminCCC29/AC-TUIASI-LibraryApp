package com.example.app.controllers;

import com.example.app.interfaces.BookServiceInterface;
import com.example.app.models.dtos.AuthorBookJoin;
import com.example.app.models.dtos.Book;
import com.example.app.models.dtos.BookAndStock;
import com.example.app.repositories.query.MySQLBookQueryCriteria;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/bookshelf/bookstorage/books")
public class BookController {

    @Autowired
    private BookServiceInterface bookService;

    @Operation(summary = "Get all books with optional filters")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Retrieves all requested books")
    })
    @GetMapping(value = "")
    public ResponseEntity<List<Book>> getBooks(@RequestParam Map<String, String> params) {
        return ResponseEntity.status(HttpStatus.OK).body(bookService.getBooks(new MySQLBookQueryCriteria(params)));
    }

    @GetMapping(value = "", params = "ISBNs")
    public ResponseEntity<List<Book>> getAllBooksByISBN(@RequestParam List<String> ISBNs)
    {
        List<Book> listOfBook = bookService.getAllBooksByISBN(ISBNs);
        return ResponseEntity.status(HttpStatus.OK).body(listOfBook);
    }

    @Operation(summary = "Get a book with a specific ISBN")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Retrieves the book"),
            @ApiResponse(responseCode = "404", description = "The book does not exists")
    })
    @GetMapping(value = "/{ISBN}")
    public ResponseEntity<Book> getBook(@PathVariable String ISBN) {

        Optional<Book> optionalBook = bookService.getBook(ISBN);
        if(optionalBook.isEmpty())
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        else
            return ResponseEntity.status(HttpStatus.OK).body(optionalBook.get());
    }

    @Operation(summary = "Store a book with a specific ISBN")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "The book was created and replaced another book"),
            @ApiResponse(responseCode = "201", description = "A new book was created"),
            @ApiResponse(responseCode = "409", description = "The book's constraints violated"),
    })
    @PutMapping(value = "/{ISBN}")
    public ResponseEntity<Book> putBook(@PathVariable String ISBN, @RequestBody Book book) {
        try {
            return ResponseEntity.status(HttpStatus.CREATED).body(bookService.putBook(ISBN, book));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).build();
        }
    }

    @Operation(summary = "Delete a book with a specific ISBN")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "The book was deleted successfully"),
            @ApiResponse(responseCode = "404", description = "The book does not exists")
    })
    @DeleteMapping(value = "/{ISBN}")
    public ResponseEntity<?> deleteBook(@PathVariable String ISBN) {
        try {
            bookService.deleteBook(ISBN);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }
        catch (Exception exception)
        {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @Operation(summary = "Add a list of author to a specific book")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Authors were successfully added"),
            @ApiResponse(responseCode = "406", description = "Incorect body format, one of the authors does not exists or book does not exist")
    })
    @PostMapping(value = "/{ISBN}/authors")
    public ResponseEntity<List<AuthorBookJoin>> postAuthorsToBook(@PathVariable String ISBN, @RequestBody Map<String, Object> authors_ids) {
        List<Integer> authors_ids_as_array = (List<Integer>)authors_ids.get("authors_ids");
        // Find the URI and URL
        try {
            return ResponseEntity.status(HttpStatus.CREATED).body(bookService.postAuthorsToBook(ISBN, authors_ids_as_array));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).build();
        }
    }

    @Operation(summary = "Validate an order and decrease stock")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Order validated and stock decreased"),
            @ApiResponse(responseCode = "406", description = "Invalid books or not enough stock")
    })
    @PostMapping("validate-order-stock")
    public ResponseEntity<List<BookAndStock>> confirmOrderStock(@RequestBody List<BookAndStock> bookAndStockList) {
        try
        {
            List<Book> updatedBooks = bookService.updateBooksStock((List<BookAndStock>) bookAndStockList, false);
            return ResponseEntity.status(HttpStatus.OK).body((List<BookAndStock>) bookAndStockList);
        }
        catch (Exception exception)
        {
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).build();
        }
    }
}
