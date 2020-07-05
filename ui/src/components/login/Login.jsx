import React from "react";
import './Login.css'
import {Link} from 'react-router-dom'

const Login = () => {

    const printErrorIfExists = () => {
        let search = window.location.search;
        let params = new URLSearchParams(search);
        let error = params.get('error');
        if (error) {
            return (
                <div className='error-message'>
                    Bad credentials
                </div>
            );
        }
    };

    const printMessageOnLogout = () => {
        let search = window.location.search;
        let params = new URLSearchParams(search);
        let logout = params.get('logout');
        if (logout !== null) {
            return (
                <div className='info-block success'>
                    You have been logout.
                </div>
            );
        }
    };

    const printMessageOnRegistered = () => {
        let search = window.location.search;
        let params = new URLSearchParams(search);
        let logout = params.get('registered');
        if (logout !== null) {
            return (
                <div className='info-block success'>
                    You can now login!
                </div>
            );
        }
    };

    return (
        <div className='login-container'>
            <form className='login-form' action='/login' method='POST'>
                <h2>Please sign in</h2>
                {printErrorIfExists()}
                {printMessageOnLogout()}
                {printMessageOnRegistered()}
                <label>
                    Username:
                    <br/>
                    <input id='username' name='username' type='text' autoFocus/>
                </label>
                <label>
                    Password:
                    <br/>
                    <input id='password' name='password' type='password'/>
                </label>
                <div className='login-btn-group'>
                    <button className='submit-btn' type='submit'>Submit</button>
                    <Link to="/registration" className='registration-btn'>Registration</Link>
                </div>
            </form>
        </div>
    );
};


export default Login;