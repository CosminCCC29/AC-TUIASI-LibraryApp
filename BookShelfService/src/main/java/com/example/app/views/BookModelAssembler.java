package com.example.app.views;

import com.example.app.controllers.BookController;
import com.example.app.models.dtos.Book;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@Component
public class BookModelAssembler implements RepresentationModelAssembler<Book, EntityModel<Book>> {

    @Override
    public EntityModel<Book> toModel(Book book) {
        return EntityModel.of(book,
                linkTo(methodOn(BookController.class).getBook(book.getISBN())).withSelfRel()//,
                // linkTo(methodOn(BookController.class).getBooks(Map.of())).withRel("books")
        );
    }
}
