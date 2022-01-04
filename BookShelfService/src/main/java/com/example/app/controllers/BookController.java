package com.example.app.controllers;

import com.example.app.interfaces.BookServiceInterface;
import com.example.app.models.dtos.AuthorBookJoin;
import com.example.app.models.dtos.Book;
import com.example.app.models.dtos.BookAndStock;
import com.example.app.models.dtos.BookBrief;
import com.example.app.repositories.query.MySQLBookQueryCriteria;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;


// Swagger: http://localhost:8080/swagger-ui.html
@RestController
@Tag(name = "Books", description = "Endpoints for managing books")
@RequestMapping("/bookshelf/bookstorage/books")
public class BookController {

    @Autowired
    private BookServiceInterface bookService;

    @Autowired
    private RepresentationModelAssembler<Book, EntityModel<Book>> bookModelAssembler;

    @Autowired
    private RepresentationModelAssembler<BookBrief, EntityModel<BookBrief>> bookBriefEntityModelAssembler;

    @Operation(summary = "Get all books with optional filters")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Retrieves all requested books", content = { @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(oneOf = { Book.class, BookBrief.class }))) })
    })
    @GetMapping(value = "")
    public ResponseEntity<CollectionModel<?>> getBooks(@RequestParam Map<String, String> params) {
        boolean printVerbose = Boolean.parseBoolean(params.getOrDefault("verbose", String.valueOf(false)));

        if (!printVerbose) {
            List<BookBrief> briefBookList = bookService.getBooks(new MySQLBookQueryCriteria(params)).stream().map(book -> new BookBrief(book.getISBN(), book.getTitle(), book.getGenre())).collect(Collectors.toList());
            return ResponseEntity.status(HttpStatus.OK).body(bookBriefEntityModelAssembler.toCollectionModel(briefBookList));
        } else {
            List<Book> bookList = bookService.getBooks(new MySQLBookQueryCriteria(params));
            return ResponseEntity.status(HttpStatus.OK).body(bookModelAssembler.toCollectionModel(bookList));
        }
    }

    @Operation(summary = "Get a book by ISBN")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Retrieves the book", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = Book.class))}),
            @ApiResponse(responseCode = "404", description = "The book does not exists", content = @Content)
    })
    @GetMapping(value = "/{ISBN}")
    public ResponseEntity<EntityModel<Book>> getBook(@PathVariable String ISBN) {

        Optional<Book> optionalBook = bookService.getBook(ISBN);
        if (optionalBook.isEmpty())
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        else {
            return ResponseEntity.status(HttpStatus.OK).body(bookModelAssembler.toModel(optionalBook.get()));
        }
    }

    @Operation(summary = "Get a set of books by ISBNs")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Retrieves requested books", content = { @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = Book.class))) })
    })
    @GetMapping(value = "/", params = "ISBNs")
    public ResponseEntity<CollectionModel<EntityModel<Book>>> getAllBooksByISBN(@RequestParam List<String> ISBNs) {
        List<Book> listOfBook = bookService.getAllBooksByISBN(ISBNs);
        return ResponseEntity.status(HttpStatus.OK).body(bookModelAssembler.toCollectionModel(listOfBook));
    }

    @Operation(summary = "Store a book with a specific ISBN")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "The book was created and replaced another book", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = Book.class))}),
            @ApiResponse(responseCode = "201", description = "A new book was created", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = Book.class))}),
            @ApiResponse(responseCode = "409", description = "The book's constraints have been violated", content = @Content)
    })
    @PutMapping(value = "/{ISBN}")
    public ResponseEntity<EntityModel<Book>> putBook(@PathVariable String ISBN, @RequestBody Book book) {
        try {
            Book savedBook = bookService.putBook(ISBN, book);
            return ResponseEntity.status(HttpStatus.CREATED).body(bookModelAssembler.toModel(savedBook));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).build();
        }
    }

    @Operation(summary = "Delete a book with a specific ISBN")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "The book was deleted successfully", content = @Content),
            @ApiResponse(responseCode = "404", description = "The book does not exists", content = @Content)
    })
    @DeleteMapping(value = "/{ISBN}")
    public ResponseEntity<?> deleteBook(@PathVariable String ISBN) {
        try {
            bookService.deleteBook(ISBN);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        } catch (Exception exception) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @Operation(summary = "Add a list of author to a specific book")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Authors were successfully added", content = {@Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = AuthorBookJoin.class)))}),
            @ApiResponse(responseCode = "406", description = "Incorect body format, one of the authors does not exists or book does not exist", content = @Content)
    })
    @PostMapping(value = "/{ISBN}/authors")
    public ResponseEntity<List<AuthorBookJoin>> postAuthorsToBook(@PathVariable String ISBN, @RequestBody Map<String, Object> authors_ids) {
        try {
            List<Integer> authors_ids_as_array = (List<Integer>) authors_ids.get("authors_ids");
            return ResponseEntity.status(HttpStatus.CREATED).body(bookService.postAuthorsToBook(ISBN, authors_ids_as_array));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).build();
        }
    }

    @Operation(summary = "Make an order")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Order validated and stock decreased", content = {@Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = BookAndStock.class)))}),
            @ApiResponse(responseCode = "406", description = "Invalid books or not enough stock", content = @Content)
    })
    @PostMapping("make-order")
    public ResponseEntity<List<BookAndStock>> confirmOrderStock(@RequestBody List<BookAndStock> bookAndStockList) {
        try {
            List<Book> updatedBooks = bookService.updateBooksStock(bookAndStockList, false);
            return ResponseEntity.status(HttpStatus.OK).body(bookAndStockList);
        } catch (Exception exception) {
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).build();
        }
    }
}
