package ro.tuiasi.uac.services;

import org.springframework.security.crypto.password.Pbkdf2PasswordEncoder;
import org.springframework.stereotype.Service;
import ro.tuiasi.uac.interfaces.HashingServiceInterface;

@Service
public class PBKDF2HashingService implements HashingServiceInterface {
    private Pbkdf2PasswordEncoder encoder;

    public PBKDF2HashingService() {
        this.encoder = new Pbkdf2PasswordEncoder();
    }

    @Override
    public String encode(String password) {
        return encoder.encode(password);
    }
}
