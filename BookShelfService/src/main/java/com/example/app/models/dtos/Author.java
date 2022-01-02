package com.example.app.models.dtos;

import com.example.app.models.dbentities.AuthorEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Author {

    private int id;
    private String firstname;
    private String lastname;

    public Author(AuthorEntity authorEntity) {
        this.id = authorEntity.getId();
        this.firstname = authorEntity.getFirstname();
        this.lastname = authorEntity.getLastname();
    }

    @Override
    public String toString() {
        String authorToString = "{" +
                "'id':'" + id +
                ",'firstname':'" + firstname + '\'' +
                ",'lastname':'" + lastname + '\'' +
                '}';
        return authorToString.replace('\'', '"');
    }
}
