package com.example.app.models.dbentities;

import com.example.app.models.dtos.Author;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Set;

@Entity
@Table(name = "authors")
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class AuthorEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    @NotNull(message = "An author firstname must be provided")
    @Column(length = 60)
    private String firstname;
    @NotNull(message = "An author lastname must be provided.")
    @Column(length = 60)
    private String lastname;

    public AuthorEntity(Author author) {
        this.id = author.getId();
        this.firstname = author.getFirstname();
        this.lastname = author.getLastname();
    }
}
