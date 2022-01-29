package ro.tuiasi.uac.controllers;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.spring.guides.gs_producing_web_service.*;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;
import ro.tuiasi.uac.interfaces.AccountServiceInterface;
import ro.tuiasi.uac.interfaces.HashingServiceInterface;
import ro.tuiasi.uac.interfaces.JwtFactoryServiceInterface;
import ro.tuiasi.uac.interfaces.JwtParserServiceInterface;
import ro.tuiasi.uac.models.*;

import java.util.HashMap;
import java.util.Optional;

@Endpoint
public class UserAccessController {

    @Autowired
    private HSKeyProperties hs256KeyProperties;

    @Autowired
    private JwtFactoryServiceInterface<String> jwtFactoryService;

    @Autowired
    private JwtParserServiceInterface<String> jwtParserService;

    @Autowired
    private AccountServiceInterface accountService;

    @Autowired
    private HashingServiceInterface hashingService;

    private static final String NAMESPACE_URI = "http://spring.io/guides/gs-producing-web-service";

    @SneakyThrows
    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "AuthenticateUserRequest")
    @ResponsePayload
    public AuthenticateUserResponse authenticateUser(@RequestPayload AuthenticateUserRequest authenticateUserRequest) {

        CredentialsData credentialsData = authenticateUserRequest.getCredentialsData();
        Credentials credentials = new Credentials(credentialsData.getEmail(), credentialsData.getPassword());

        Optional<Account> optionalAccount = accountService.getAccountByCredentials(credentials.getEmail(), hashingService.encode(credentials.getPassword()));
        boolean validCredentials = optionalAccount.isPresent();

        if(validCredentials)
        {
            Account account = optionalAccount.get();
            String token = jwtFactoryService.generateToken(new HashMap<>(){{ put("sub", account.getId()); put("role", account.getRole()); }}, hs256KeyProperties.getSecretKey());
            // return ResponseEntity.status(HttpStatus.CREATED).body(new TokenAndClaims(token, account.getId(), account.getRole()));

            TokenAndClaimsData tokenAndClaimsData = new TokenAndClaimsData();
            tokenAndClaimsData.setToken(token);
            tokenAndClaimsData.setAccountId(account.getId());
            tokenAndClaimsData.setRole(account.getRole());

            AuthenticateUserResponse authenticateUserResponse = new AuthenticateUserResponse();
            authenticateUserResponse.setTokenAndClaimsData(tokenAndClaimsData);
            return authenticateUserResponse;
        }
        else
        {
            // return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
            throw new Exception("Invalid credentials!");
        }
    }

    @SneakyThrows
    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "ValidateTokenRequest")
    @ResponsePayload
    public ValidateTokenResponse validateToken(@RequestPayload ValidateTokenRequest validateTokenRequest)
    {
        TokenData tokenData = validateTokenRequest.getTokenData();
        Token token = new Token(tokenData.getToken());

        Jws<Claims> tokenClaims = null;
        try{
            tokenClaims = jwtParserService.validateSignatureAndGetTokenParser(token.getToken(), hs256KeyProperties.getSecretKey());
        }
        catch (Exception exception)
        {
            // return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
            throw new Exception("User not authorized!");
        }

        TokenAndClaims tokenAndClaims = new TokenAndClaims();
        try {
//            tokenAndClaims.setToken(token.getToken());
//            tokenAndClaims.setAccountId(tokenClaims.getBody().get("sub", Integer.class));
//            tokenAndClaims.setRole(tokenClaims.getBody().get("role", String.class));

            // return ResponseEntity.status(HttpStatus.OK).body(tokenAndClaims);

            TokenAndClaimsData tokenAndClaimsData = new TokenAndClaimsData();
            tokenAndClaimsData.setToken(token.getToken());
            tokenAndClaimsData.setAccountId(tokenClaims.getBody().get("sub", Integer.class));
            tokenAndClaimsData.setRole(tokenClaims.getBody().get("role", String.class));

            ValidateTokenResponse validateTokenResponse = new ValidateTokenResponse();
            validateTokenResponse.setTokenAndClaimsData(tokenAndClaimsData);
            return validateTokenResponse;
        }
        catch (Exception exception)
        {
            // return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
            throw new Exception("User not authorized!");
        }
    }
}
