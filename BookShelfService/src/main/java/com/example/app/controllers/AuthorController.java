package com.example.app.controllers;

import com.example.app.interfaces.AuthorServiceInterface;
import com.example.app.models.dtos.Author;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;


@RestController
@Tag(name = "Authors", description = "Endpoints for managing authors")
@RequestMapping("/bookshelf/bookstorage/authors")
public class AuthorController {

    @Autowired
    private AuthorServiceInterface authorService;

    @Autowired
    private RepresentationModelAssembler<Author, EntityModel<Author>> authorModelAssembler;

    @Operation(summary = "Get all authors", security = @SecurityRequirement(name = "TokenAuth"))
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Retrieves all requested authors", content = { @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = Author.class))) })
    })
    @GetMapping(value = "")
    public ResponseEntity<CollectionModel<EntityModel<Author>>> getAuthors() {
        return ResponseEntity.status(HttpStatus.OK).body(authorModelAssembler.toCollectionModel(authorService.getAuthors()));
    }

    @Operation(summary = "Get an author by id", security = @SecurityRequirement(name = "TokenAuth"))
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Retrieves the author", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = Author.class))}),
            @ApiResponse(responseCode = "404", description = "The author does not exists", content = @Content)
    })
    @GetMapping(value = "/{id}")
    public ResponseEntity<EntityModel<Author>> getAuthor(@PathVariable int id) {
        Optional<Author> authorOptional = authorService.getAuthor(id);
        if (authorOptional.isEmpty())
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        else
            return ResponseEntity.status(HttpStatus.OK).body(authorModelAssembler.toModel(authorOptional.get()));
    }

    @Operation(summary = "Store an author", security = @SecurityRequirement(name = "TokenAuth"))
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "A new book was created", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = Author.class))}),
            @ApiResponse(responseCode = "406", description = "The author's constraints have been violated", content = @Content)
    })
    @PostMapping(value = "")
    public ResponseEntity<EntityModel<Author>> postAuthor(@RequestBody Author author) {
        // Find the URI and URL
        try {
            Author addedAuthor = authorService.postAuthor(author);
            return ResponseEntity.status(HttpStatus.CREATED).body(authorModelAssembler.toModel(addedAuthor));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).build();
        }
    }

    @Operation(summary = "Delete an author with a specific id", security = @SecurityRequirement(name = "TokenAuth"))
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "The author was deleted successfully", content = @Content),
            @ApiResponse(responseCode = "404", description = "The author does not exists", content = @Content)
    })
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
