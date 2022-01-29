package ro.tuiasi.uac.interfaces;

import java.security.PrivateKey;
import java.util.Map;

public interface JwtFactoryServiceInterface<T> {
    String generateToken(Map<String, Object> claims, T templateKey);
}
