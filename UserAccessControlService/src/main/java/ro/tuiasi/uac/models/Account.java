package ro.tuiasi.uac.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/*
    DTO class for user account
*/
@Setter
@Getter
@AllArgsConstructor
@Entity
@Table(name = "accounts")
public class Account {
    @Id
    private Integer id;
    private String email;
    private String password;
    private String role;

    public Account()
    {
        id = 0;
    }
}

