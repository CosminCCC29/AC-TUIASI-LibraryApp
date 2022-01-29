package com.example.app.interfaces;

import com.example.app.models.dtos.Token;
import com.example.app.models.dtos.TokenAndClaims;
import org.springframework.http.ResponseEntity;

public interface IdentityProviderAPIServiceInterface {

    ResponseEntity<TokenAndClaims> validateTokenRequest(Token token);
}
