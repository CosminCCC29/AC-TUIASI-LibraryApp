package com.example.app.views;

import com.example.app.controllers.AuthorController;
import com.example.app.models.dtos.Author;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import java.util.LinkedList;
import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class AuthorModelAssembler implements RepresentationModelAssembler<Author, EntityModel<Author>> {

    @Override
    public EntityModel<Author> toModel(Author author) {

        return new EntityModel<>(author).add(
                linkTo(methodOn(AuthorController.class).getAuthor(author.getId())).withSelfRel(),
                linkTo(methodOn(AuthorController.class).deleteAuthor(author.getId())).withSelfRel().withType("DELETE")
        );
    }

    @Override
    public CollectionModel<EntityModel<Author>> toCollectionModel(Iterable<? extends Author> authors) {

        List<EntityModel<Author>> bookList = new LinkedList<>();
        for(Author author : authors)
            bookList.add(this.toModel(author));

        return new CollectionModel<>(bookList).add(
                linkTo(AuthorController.class).withSelfRel(),
                linkTo(methodOn(AuthorController.class).postAuthor(new Author())).withRel("addAuthor").withType("POST")
        );
    }
}


