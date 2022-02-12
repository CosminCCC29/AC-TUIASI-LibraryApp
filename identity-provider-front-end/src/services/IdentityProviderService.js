import SoapBodyBuilder from "./SoapBodyBuilder"

class IdentityProviderService 
{
    constructor() {
        this.AUTHENTICATE_USER_URL = "http://localhost:8082/identity-provider"
        this.soapBodyBuilder = new SoapBodyBuilder()
    }
    
    autheticateUserRequest(credentials) {
        
        return fetch(this.AUTHENTICATE_USER_URL, {
            method: "POST",
            headers: {
                "Content-Type": "text/xml",
                "SOAPAction": "#POST",
            },
            body: this.soapBodyBuilder.createAuthenticateUserRequestBody(credentials)
        })
    }
}

export default IdentityProviderService;