class IdentityProviderService 
{
    constructor() {
        this.AUTHENTICATE_USER_URL = "http://localhost:8082/identity-provider"
    }
    
    loginRequest() {
        // fetch(this.AUTHENTICATE_USER_URL, {
        //     method: "POST",
        //     headers: {
        //         "Content-Type": "text/xml",
        //         "SOAPAction": "#POST",
        //     },
        //     body:
        //     "<Envelope xmlns=\"http://schemas.xmlsoap.org/soap/envelope/\">"
        //         <Body>
        //             <AuthenticateUserRequest xmlns="http://spring.io/guides/gs-producing-web-service">
        //                 <CredentialsData>
        //                     <email>cosmin@gmail.com</email>
        //                     <password>123456</password>
        //                 </CredentialsData>
        //             </AuthenticateUserRequest>
        //         </Body>
        //     </Envelope>"
        // })
    }
}