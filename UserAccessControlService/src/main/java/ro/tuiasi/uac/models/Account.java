package ro.tuiasi.uac.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import net.bytebuddy.dynamic.loading.InjectionClassLoader;

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
    private Integer id;
    private String email;
    private String password;

    public Account()
    {
        id = 0;
    }
}

