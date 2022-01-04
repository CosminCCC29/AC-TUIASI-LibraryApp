package com.example.app.views;

import com.example.app.controllers.BookController;
import com.example.app.models.dtos.Book;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class BookModelAssembler implements RepresentationModelAssembler<Book, EntityModel<Book>> {

    @Override
    public EntityModel<Book> toModel(Book book) {

        return new EntityModel<>(book).add(
                linkTo(methodOn(BookController.class).getBook(book.getISBN())).withSelfRel(),
                linkTo(methodOn(BookController.class).deleteBook(book.getISBN())).withSelfRel().withType("DELETE"),
                linkTo(methodOn(BookController.class).postAuthorsToBook(book.getISBN(), Map.of())).withRel("addAuthor").withType("POST")
                );
    }

    @Override
    public CollectionModel<EntityModel<Book>> toCollectionModel(Iterable<? extends Book> books) {

        List<EntityModel<Book>> bookList = new LinkedList<>();
        for(Book book : books)
            bookList.add(this.toModel(book));

        return new CollectionModel<>(bookList).add(
                linkTo(BookController.class).withSelfRel()
        );
    }
}
