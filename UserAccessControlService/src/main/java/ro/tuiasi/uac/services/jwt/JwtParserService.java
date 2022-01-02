package ro.tuiasi.uac.services.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import org.springframework.stereotype.Service;
import ro.tuiasi.uac.interfaces.useraccesscontrol.JwtParserServiceInterface;

import java.security.PublicKey;
import java.util.Date;

/*
    Class defined for validating and parsing tokens
*/
@Service
public class JwtParserService implements JwtParserServiceInterface {

    public Jws<Claims> validateSignatureAndGetTokenParser(String token, PublicKey publicKey) {
        return Jwts.parser().setSigningKey(publicKey).parseClaimsJws(token);
    }

    public boolean isTokenValid(String token, PublicKey publicKey)
    {
        try{
            return Jwts.parser().setSigningKey(publicKey).parseClaimsJws(token).getBody().getExpiration().after(new Date(System.currentTimeMillis()));
        }
        catch (Exception exception)
        {
            return false;
        }
    }
}
