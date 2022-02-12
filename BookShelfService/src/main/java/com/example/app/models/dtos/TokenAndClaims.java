package com.example.app.models.dtos;

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
    private int sub;
    private String role;
}
