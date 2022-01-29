package ro.tuiasi.uac.services;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import org.springframework.stereotype.Service;
import ro.tuiasi.uac.interfaces.JwtParserServiceInterface;

import java.security.PublicKey;
import java.util.Date;

/*
    Class defined for validating and parsing tokens
*/
@Service
public class HS256JwtParserService implements JwtParserServiceInterface<String> {

    public Jws<Claims> validateSignatureAndGetTokenParser(String token, String secretKey) {
        return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token);
    }

    @Override
    public boolean isTokenValid(String token, String secretKey) {
        try{
            return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody().getExpiration().after(new Date(System.currentTimeMillis()));
        }
        catch (Exception exception)
        {
            return false;
        }
    }
}
