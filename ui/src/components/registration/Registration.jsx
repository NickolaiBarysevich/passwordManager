import React from "react";
import InputMask from 'react-input-mask';
import './Registration.css'
import {Link, withRouter} from "react-router-dom";

class Registration extends React.Component {

    constructor(props) {
        super(props);
        this.phoneInput = React.createRef();
        this.state = {
            username: null,
            password: null,
            repeatPassword: null,
            countryCode: null,
            email: null,
            errorMessage: null
        }
    }

    printErrorIfExists = () => {
        if (this.state.errorMessage) {
            return (
                <div className='error-message'>
                    {this.state.errorMessage}
                </div>
            );
        }
    };

    registrationRequest = (e) => {
        e.preventDefault();
        if (this.state.password === this.state.repeatPassword) {
            fetch('/api/v1/auth/registration', {
                method: 'POST',
                headers: {
                    'Content-type': 'application/json',
                    'Accept': 'application/json'
                },
                body: JSON.stringify({
                    username: this.state.username,
                    password: this.state.password,
                    email: this.state.email
                })
            }).then(response => {
                return response.status === 200
                    ? JSON.parse('{}')
                    : response.json()
            })
                .then(data => {
                    if (data.serviceCode) {
                        this.setState({
                            errorMessage: data.message
                        });
                    } else {
                        this.props.history.push("/auth/login?registered");
                    }
                })
        } else {
            this.setState({
                errorMessage: 'Passwords do not match!'
            })
        }
    };

    render() {
        return (
            <div className='login-container'>
                <form className='registration-form' onSubmit={this.registrationRequest}>
                    <h2>Please sign up</h2>
                    {this.printErrorIfExists()}
                    <label>
                        Username:
                        <br/>
                        <input type='text' minLength={3} maxLength={64} value={this.state.username} required
                               onChange={e => this.setState({username: e.target.value})}/>
                    </label>
                    <label>
                        Password:
                        <br/>
                        <input type='password' minLength={8} maxLength={256} value={this.state.password} required
                               onChange={e => this.setState({password: e.target.value})}/>
                    </label>
                    <label>
                        Repeat password:
                        <br/>
                        <input type='password' value={this.state.repeatPassword} required
                               onChange={e => this.setState({repeatPassword: e.target.value})}
                               style={this.state.repeatPassword !== this.state.password
                                   ? {border: '1px solid red'}
                                   : {border: '1px solid black'}}/>
                    </label>
                    <label>
                        Email:
                        <input type='email' value={this.state.email} required
                               onChange={e => this.setState({email: e.target.value})}/>
                    </label>
                    <div className='login-btn-group'>
                        <button type='submit' className='submit-btn'
                                disabled={(!this.state.password || !this.state.repeatPassword)
                                || (this.state.password !== this.state.repeatPassword)}>
                            Submit
                        </button>
                        <Link to="/auth/login" className='registration-btn'>Sign in</Link>
                    </div>
                </form>
            </div>
        )
    }
}

export default withRouter(Registration);