package ro.tuiasi.uac.models;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import ro.tuiasi.uac.interfaces.FileIOManagerServiceInterface;
import ro.tuiasi.uac.services.FileIOManagerService;

import javax.annotation.PostConstruct;
import java.nio.charset.StandardCharsets;

/*
    Configuration class for Private/Public key pair
*/
@Component
@ConfigurationProperties(prefix = "key.set")
public class HSKeyProperties {

    @Value("${secret.key.file}")
    private String SECRET_KEY_FILE;

    @Setter
    @Getter
    private String secretKey;

    @PostConstruct
    private void createRSAKey() throws Exception {
        FileIOManagerServiceInterface fileIOManagerService = new FileIOManagerService();

        // Reading secret key from file
        byte[] keyBytes = fileIOManagerService.readFile(SECRET_KEY_FILE);
        secretKey = new String(keyBytes, StandardCharsets.UTF_8);

        // fileIOManagerService.deleteFile(SECRET_KEY_FILE);
    }
}
