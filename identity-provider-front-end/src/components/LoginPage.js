import React from 'react';
import ReactDOM from 'react-dom';
import LoginForm from './LoginForm';

class LoginPage extends React.Component {
    
    constructor(props) {
        super(props);
    }

    render() {
        return (
            <>
            <h2> Login form </h2>           
            <LoginForm />
            </>
        )
    }
}

export default LoginPage;
