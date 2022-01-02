package ro.tuiasi.uac.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/*
    DTO class for user credentials
*/
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Credentials {
    private String email;
    private String password;
}
