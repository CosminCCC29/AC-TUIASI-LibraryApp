package com.example.app.models.dbentities;

import com.example.app.models.dbentities.primarykeys.AuthorsBooksPK;
import com.example.app.models.dtos.AuthorBookJoin;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "authors_books")
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class AuthorBookEntity {

    @EmbeddedId
    private AuthorsBooksPK authorBookPK;

    @NotNull(message = "An index of the author must be provided")
    private int author_index;

    public AuthorBookEntity(int authors_id, String isbn, int index) {
        authorBookPK = new AuthorsBooksPK(authors_id, isbn);
        this.author_index = index;
    }

    public AuthorBookEntity(AuthorBookJoin authorBookJoin) {
        authorBookPK = new AuthorsBooksPK(authorBookJoin.getAuthors_id(), authorBookJoin.getBooks_ISBN());
        this.author_index = authorBookJoin.getAuthor_index();
    }
}
