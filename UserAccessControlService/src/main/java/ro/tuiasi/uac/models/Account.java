package ro.tuiasi.uac.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

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
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private int id;
    private String email;
    private String password;
    private String role;

    public Account()
    {
        id = 0;
    }
}

