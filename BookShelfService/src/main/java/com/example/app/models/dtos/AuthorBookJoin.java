package com.example.app.models.dtos;

import com.example.app.models.dbentities.AuthorBookEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class AuthorBookJoin {

    private int authors_id;
    private String books_ISBN;
    private int author_index;

    public AuthorBookJoin(AuthorBookEntity authorBookEntity)
    {
        this.authors_id = authorBookEntity.getAuthorBookPK().getAuthors_id();
        this.books_ISBN = authorBookEntity.getAuthorBookPK().getBooks_ISBN();
        this.author_index = authorBookEntity.getAuthor_index();
    }
}
