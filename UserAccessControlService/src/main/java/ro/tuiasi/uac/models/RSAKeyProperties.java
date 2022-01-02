package ro.tuiasi.uac.models;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import ro.tuiasi.uac.interfaces.filemanagers.FileIOManagerServiceInterface;
import ro.tuiasi.uac.interfaces.useraccesscontrol.KeyConverterServiceInterface;
import ro.tuiasi.uac.services.filemanagers.FileIOManagerService;
import ro.tuiasi.uac.services.keyconverters.RSAKeyConverterService;

import javax.annotation.PostConstruct;
import java.security.PrivateKey;
import java.security.PublicKey;

/*
    Configuration class for Private/Public key pair
*/
@Component
@ConfigurationProperties(prefix = "key.set")
public class RSAKeyProperties {

    @Value("${key.set.publicKeyFile}")
    private String publicKeyFile;
    @Value("${key.set.privateKeyFile}")
    private String privateKeyFile;

    @Getter
    private PublicKey publicKey;
    @Getter
    private PrivateKey privateKey;

    @PostConstruct
    private void createRSAKey() throws Exception {
        FileIOManagerServiceInterface fileIOManagerService = new FileIOManagerService();
        KeyConverterServiceInterface keyConverterService = new RSAKeyConverterService();

        // Reading keys from files and convert them to Private/Public key objects for further use
        publicKey = keyConverterService.getPublicKey(fileIOManagerService.readFile(publicKeyFile));
        privateKey = keyConverterService.getPrivateKey(fileIOManagerService.readFile(privateKeyFile));

        // fileIOManagerService.deleteFile(publicKeyFile);
        // fileIOManagerService.deleteFile(privateKeyFile);
    }
}
