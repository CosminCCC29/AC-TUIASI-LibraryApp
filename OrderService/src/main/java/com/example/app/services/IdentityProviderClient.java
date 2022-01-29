package com.example.app.services;

import com.example.wsdl.AuthenticateUserRequest;
import com.example.wsdl.AuthenticateUserResponse;
import com.example.wsdl.ValidateTokenRequest;
import com.example.wsdl.ValidateTokenResponse;
import org.springframework.ws.client.core.support.WebServiceGatewaySupport;

public class IdentityProviderClient extends WebServiceGatewaySupport {

    public AuthenticateUserResponse authenticateUser(AuthenticateUserRequest authenticateUserRequest)
    {
        return (AuthenticateUserResponse) getWebServiceTemplate().marshalSendAndReceive(authenticateUserRequest);
    }

    public ValidateTokenResponse validateToken(ValidateTokenRequest validateTokenRequest)
    {
        return (ValidateTokenResponse) getWebServiceTemplate().marshalSendAndReceive(validateTokenRequest);
    }
}
