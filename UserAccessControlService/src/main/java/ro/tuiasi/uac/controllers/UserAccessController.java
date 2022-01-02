package ro.tuiasi.uac.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ro.tuiasi.uac.interfaces.useraccesscontrol.JwtFactoryServiceInterface;
import ro.tuiasi.uac.interfaces.useraccesscontrol.JwtParserServiceInterface;
import ro.tuiasi.uac.models.Credentials;
import ro.tuiasi.uac.models.RSAKeyProperties;
import ro.tuiasi.uac.models.Token;
import java.util.HashMap;

@RestController
@RequestMapping("uac")
public class UserAccessController {

    @Autowired
    RSAKeyProperties rsaKeyProperties;

    @Autowired
    JwtFactoryServiceInterface jwtFactoryService;

    @Autowired
    JwtParserServiceInterface jwtParserService;

    @PostMapping("/authenticate")
    public ResponseEntity<Token> authenticateUser(@RequestBody Credentials credentials) {
        boolean validCredentials = credentials.getEmail().equals("email@gmail.com") && credentials.getPassword().equals("password");

        if(validCredentials)
        {
            String token = jwtFactoryService.generateToken(new HashMap<>(){{ put("role", "USER"); }}, rsaKeyProperties.getPrivateKey());
            return ResponseEntity.status(HttpStatus.CREATED).body(new Token(token));
        }
        else
        {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }

    @PostMapping("/validate-token")
    public ResponseEntity<?> validateToken(@RequestBody Token token)
    {
        if(jwtParserService.isTokenValid(token.getToken(), rsaKeyProperties.getPublicKey()))
            return ResponseEntity.status(HttpStatus.OK).build();
        else
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }
}
