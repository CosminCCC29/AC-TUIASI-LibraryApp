package ro.tuiasi.uac.services.keyconverters;

import org.springframework.stereotype.Service;
import ro.tuiasi.uac.interfaces.useraccesscontrol.KeyConverterServiceInterface;

import java.nio.charset.Charset;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

/*
    Class defined for converting key pairs from raw bytes to private/public key java objects
*/
@Service
public class RSAKeyConverterService implements KeyConverterServiceInterface {

    final String ENCRYPT_ALGORITHM = "RSA";

    @Override
    public PrivateKey getPrivateKey(byte[] bytes) throws NoSuchAlgorithmException, InvalidKeySpecException {
        String stringKey = new String(bytes, Charset.defaultCharset());
        stringKey = stringKey.replace("-----BEGIN PRIVATE KEY-----", "").replaceAll(System.lineSeparator(), "").replace("-----END PRIVATE KEY-----", "");

        bytes = Base64.getDecoder().decode(stringKey);
        PKCS8EncodedKeySpec spec = new PKCS8EncodedKeySpec(bytes);
        KeyFactory factory = KeyFactory.getInstance(ENCRYPT_ALGORITHM);
        return factory.generatePrivate(spec);
    }

    @Override
    public PublicKey getPublicKey(byte[] bytes) throws NoSuchAlgorithmException, InvalidKeySpecException {
        String stringKey = new String(bytes, Charset.defaultCharset());
        stringKey = stringKey.replace("-----BEGIN PUBLIC KEY-----", "").replaceAll(System.lineSeparator(), "").replace("-----END PUBLIC KEY-----", "").strip();

        bytes = Base64.getDecoder().decode(stringKey);
        X509EncodedKeySpec spec = new X509EncodedKeySpec(bytes);
        KeyFactory factory = KeyFactory.getInstance(ENCRYPT_ALGORITHM);
        return factory.generatePublic(spec);
    }
}
