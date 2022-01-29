package ro.tuiasi.uac.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/*
    DTO class for tokens
*/
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TokenAndClaims {
    private String token;
    private int accountId;
    private String role;
}
