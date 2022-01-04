package com.example.app.views;

import com.example.app.controllers.BookController;
import com.example.app.models.dtos.BookBrief;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import java.util.LinkedList;
import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class BookBriefModelAssembler implements RepresentationModelAssembler<BookBrief, EntityModel<BookBrief>> {

    @Override
    public EntityModel<BookBrief> toModel(BookBrief bookBrief) {

        return new EntityModel<>(bookBrief).add(
                linkTo(methodOn(BookController.class).getBook(bookBrief.getISBN())).withSelfRel()
        );
    }

    @Override
    public CollectionModel<EntityModel<BookBrief>> toCollectionModel(Iterable<? extends BookBrief> bookBriefs) {

        List<EntityModel<BookBrief>> bookBriefList = new LinkedList<>();
        for(BookBrief bookBrief : bookBriefs)
            bookBriefList.add(this.toModel(bookBrief));

        return new CollectionModel<>(bookBriefList).add(
                linkTo(BookController.class).withSelfRel()
        );
    }
}
