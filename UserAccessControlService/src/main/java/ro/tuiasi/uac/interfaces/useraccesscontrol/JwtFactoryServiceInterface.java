package ro.tuiasi.uac.interfaces.useraccesscontrol;

import java.security.PrivateKey;
import java.util.Map;

public interface JwtFactoryServiceInterface {
    String generateToken(Map<String, Object> claims, PrivateKey privateKey);
}
