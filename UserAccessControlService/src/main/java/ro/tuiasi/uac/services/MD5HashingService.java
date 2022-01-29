package ro.tuiasi.uac.services;

import lombok.SneakyThrows;
import org.springframework.context.annotation.Primary;
import org.springframework.security.crypto.password.Pbkdf2PasswordEncoder;
import org.springframework.stereotype.Service;
import ro.tuiasi.uac.interfaces.HashingServiceInterface;

import javax.xml.bind.DatatypeConverter;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;

@Service
@Primary
public class MD5HashingService implements HashingServiceInterface {
    private final MessageDigest encoder;

    @SneakyThrows
    public MD5HashingService() {
        this.encoder = MessageDigest.getInstance("MD5");
    }

    @Override
    public String encode(String password) {
        byte[] digest = encoder.digest(password.getBytes(StandardCharsets.UTF_8));
        return DatatypeConverter.printHexBinary(digest);
    }
}