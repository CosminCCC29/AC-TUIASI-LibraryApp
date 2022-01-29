package ro.tuiasi.uac.services;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Service;
import ro.tuiasi.uac.interfaces.JwtFactoryServiceInterface;

import javax.annotation.PostConstruct;
import java.util.Base64;
import java.util.Date;
import java.util.Map;
import java.util.UUID;

/*
    Class defined for generating tokens
*/
@Service
public class HS256JwtFactoryService implements JwtFactoryServiceInterface<String> {

    private long expirationTimeOffset;

    @PostConstruct
    public void initFactoryParameters() {
        // 15 minutes in miliseconds
        expirationTimeOffset = 900000;
    }

    private static String createJTI() {
        return new String(Base64.getEncoder().encode(UUID.randomUUID().toString().getBytes()));
    }

    public String generateToken(Map<String, Object> claims, String secretKey) {
        Date issuedAtDate = new Date(System.currentTimeMillis());
        return Jwts.builder()
                .setClaims(claims)
                .setId(createJTI())
                .setIssuedAt(issuedAtDate)
                .setExpiration(new Date(issuedAtDate.getTime() + expirationTimeOffset))
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();
    }
}
