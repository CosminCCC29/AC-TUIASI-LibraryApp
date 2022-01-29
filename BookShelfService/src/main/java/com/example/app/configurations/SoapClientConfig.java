package com.example.app.configurations;

import com.example.app.services.IdentityProviderClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;

@Configuration
public class SoapClientConfig {

    @Bean
    public Jaxb2Marshaller marshaller(){
        Jaxb2Marshaller jaxb2Marshaller = new Jaxb2Marshaller();
        jaxb2Marshaller.setContextPath("com.example.wsdl");
        return jaxb2Marshaller;
    }

    @Bean
    public IdentityProviderClient articleClient(Jaxb2Marshaller jaxb2Marshaller) {
        IdentityProviderClient articleClient = new IdentityProviderClient();
        articleClient.setDefaultUri("http://localhost:8082/identity-provider");
        articleClient.setMarshaller(jaxb2Marshaller);
        articleClient.setUnmarshaller(jaxb2Marshaller);
        return articleClient;
    }
}
