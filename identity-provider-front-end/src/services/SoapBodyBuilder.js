import Account from "../models/Account"
import Credentials from "../models/Credentials"

import xmlbuilder from "xmlbuilder"

class SoapBodyBuilder {

    constructor() {
        
        const envelopeXmlns = "http://schemas.xmlsoap.org/soap/envelope/"

        this.body = xmlbuilder.create('Envelope')
        this.body.att('xmlns', envelopeXmlns)
    }

    createAddAccountRequestBody(account) {
        
        // if(typeof(account) != Account)
        //     throw "Wrong account data format";

        const requestXmlns = "http://spring.io/guides/gs-producing-web-service"

        this.body.ele('AddAccountRequest', {'xmlns': requestXmlns})
        
        const accountData = xmlbuilder.create('AccountData')
        accountData.ele('id', account.id)
        accountData.ele('email', account.email)
        accountData.ele('password', account.password)
        accountData.ele('role', account.role)
        
        this.body.ele(accountData)
        this.body.end({ pretty: true })

        return this.body;
    }

    createAuthenticateUserRequestBody(credentials) {
        
        // console.log(typeof(credentials))
        // if(typeof(credentials) != Credentials)
        //     throw "Wrong credentials data format";

        const requestXmlns = "http://spring.io/guides/gs-producing-web-service"

        this.body.ele('Body')
        .ele('AuthenticateUserRequest', {'xmlns': requestXmlns})
        .ele('CredentialsData')
        .ele('email', credentials.email).up()
        .ele('password', credentials.password)

        return this.body.end({ pretty: true });
    }
}


export default SoapBodyBuilder;