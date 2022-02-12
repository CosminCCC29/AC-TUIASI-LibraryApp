import { useState } from "react";
import IdentityProviderService from "../services/IdentityProviderService";
import Credentials from "../models/Credentials"

function LoginForm() {

    const [errorMessages, setErrorMessages] = useState({});
    const [isSubmitted, setIsSubmitted] = useState(false);
    const identityProviderService = new IdentityProviderService()

    const errors = {
        credentials: "Invalid credentials",
        no_connection: "Can't connect to server"
    };

    const renderErrorMessage = (name) => name === errorMessages.name && (
        <div className="error">{errorMessages.message}</div>
    );

    const authenticateUser = (event) => {
        //Prevent page reload
        event.preventDefault();

        var { email, password } = document.forms[0];

        let emailFromForm = email.value
        let passwordFromForm = password.value
        
        identityProviderService.autheticateUserRequest(new Credentials(emailFromForm, passwordFromForm))
        .then(  (response) => (response.status == 200)? setIsSubmitted(true) : setErrorMessages({ name: "credentials", message: errors.credentials }) )
        .catch( (error) => setErrorMessages({ name: "no_connection", message: errors.no_connection }) )
    };

    const renderForm = (
        <div className="login-form">
            <form onSubmit = {authenticateUser}>
                <div className = "input-container">
                    <label>Email</label>
                    <input type="text" name="email" required />
                </div>

                <div className="input-container">
                    <label>Password</label>
                    <input type="password" name="password" required />
                </div>
                {renderErrorMessage("credentials")}
                {renderErrorMessage("no_connection")}
                                
                <div className="button-container">
                    <input type="submit" />
                </div>
            </form>
        </div>
    );
    
    return (
        <div className="login-form">
            {isSubmitted ? <div>User successfully logged in <br></br><a href="http://localhost:3000/login">Click here for redirection</a></div> : renderForm}
        </div>
    );
}

export default LoginForm;
