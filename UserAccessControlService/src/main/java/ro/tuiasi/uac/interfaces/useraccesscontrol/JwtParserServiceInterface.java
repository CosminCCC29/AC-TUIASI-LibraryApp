package ro.tuiasi.uac.interfaces.useraccesscontrol;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;

import java.security.PublicKey;

public interface JwtParserServiceInterface {
    Jws<Claims> validateSignatureAndGetTokenParser(String token, PublicKey publicKey);
    boolean isTokenValid(String token, PublicKey publicKey);
}
