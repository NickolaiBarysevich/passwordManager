import React from "react";
import './Credentials.css'

//todo remove dev version
class Credentials extends React.Component {

    constructor(props) {
        super(props);
        this.state = {
            id: this.props.id,
            title: this.props.title,
            username: '',
            password: '',
            description: '',
            readOnly: true,
            show: false,
            errorMessage: null,
            showPassword: false,
            showLoader: false,
            showDelete: false
        }
    }

    toggleValues = () => {
        if (!this.state.show) {
            this.setState({
                showLoader: true
            });
            this.requestCredentialsDetails(() => this.setState({
                show: true,
                showLoader: false
            }));
        } else {
            this.setState({
                show: false
            })
        }
    };

    requestCredentialsDetails = (callback) => {
        fetch('/api/v1/credentials/' + this.state.id, {
            method: 'GET',
            headers: {
                'Content-type': 'application/json',
                'Accept': 'application/json'
            }
        })
            .then(response => response.json())
            .then(data => {
                if (data.serviceCode) {
                    console.log(data)
                } else {
                    this.setState({
                        title: data.title,
                        username: data.username,
                        password: data.password,
                        description: data.description,
                    });
                    if (callback instanceof Function) {
                        callback()
                    }
                }
            })
    };

    toggleEdit = () => {
        if (!this.state.readOnly) {
            this.updateRequest();
        }
        this.setState({
            readOnly: !this.state.readOnly
        })
    };

    deleteRequest = () => {
        fetch('/api/v1/credentials/' + this.state.id, {
            method: 'DELETE',
            headers: {
                'Content-type': 'application/json',
                'Accept': 'application/json'
            }
        }).then(response => {
            if (response.status === 200) {
                return JSON.parse('{}');
            } else {
                return response.json;
            }
        }).then(data => {
            if (data.serviceCode) {
                this.setState({
                    errorMessage: data.message
                })
            } else {
                this.setState({
                    errorMessage: null
                });
                this.props.updateCredentialsList();
            }
        })
    };

    updateRequest = () => {
        fetch('/api/v1/credentials/' + this.state.id, {
            method: 'PUT',
            headers: {
                'Content-type': 'application/json',
                'Accept': 'application/json'
            },
            body: JSON.stringify({
                title: this.state.title,
                username: this.state.username,
                password: this.state.password,
                description: this.state.description
            })
        }).then(response => {
            if (response.status === 200) {
                return JSON.parse('{}');
            } else {
                return response.json;
            }
        }).then(data => {
            if (data.serviceCode) {
                this.setState({
                    errorMessage: data.message
                })
            } else {
                this.requestCredentialsDetails();
                this.setState({
                    errorMessage: null
                })
            }
        })
    };

    applyWritableInputStyle = () => {
        return !this.state.readOnly
            ? {border: '1px solid #424242'}
            : {border: '1px solid transparent', borderBottom: '1px solid #424242'}
    };

    render() {
        return (
            <div id={this.props.id} className="credentials-card">
                <input className='credentials-card-title' type='text' readOnly={this.state.readOnly}
                       value={this.state.title} onChange={e => this.setState({title: e.target.value})}/>
                <div className='credentials-card-btn-group'>
                    <div className='loader' style={
                        !this.state.showLoader
                            ? {visibility: 'hidden'}
                            : {visibility: 'visible'}}>
                        <span/><span/><span/>
                    </div>
                    <div>
                        {this.state.show &&

                        <a className='credentials-card-title-btn' onClick={this.toggleEdit}>
                            {this.state.readOnly ? 'edit' : 'save'}
                        </a>}
                        <a className='credentials-card-title-btn' onClick={this.toggleValues}>
                            {!this.state.show ? 'show' : 'hide'}
                        </a>
                        <a className='credentials-card-title-btn' onClick={() => this.setState({
                            showDelete: !this.state.showDelete
                        })}>
                            {this.state.showDelete ? 'cancel' : 'delete'}
                        </a>
                    </div>
                </div>
                {
                    this.state.show && !this.state.showDelete
                    && <div style={{display: 'flex', flexDirection: 'column', maxWidth: '12rem'}}>
                        <label className='credentials-card-label'>
                            <div>Username&nbsp;
                                <i className="far fa-copy" title='Copy to clipboard'
                                   onClick={() => {
                                       navigator.clipboard.writeText(this.state.username)
                                   }}/>
                            </div>
                            <input type='text' className='credentials-card-input' style={this.applyWritableInputStyle()}
                                   readOnly={this.state.readOnly} value={this.state.username}
                                   onChange={e => this.setState({username: e.target.value})}/>
                        </label>
                        <label className='credentials-card-label'>
                            <div>Password&nbsp;
                                <i className="far fa-copy" title='Copy to clipboard'
                                   onClick={() => {
                                       navigator.clipboard.writeText(this.state.password)
                                   }}/>
                                &nbsp;
                                <i className={!this.state.showPassword ? "far fa-eye" : "far fa-eye-slash"}
                                   onClick={() => this.setState({showPassword: !this.state.showPassword})}
                                   title={!this.state.showPassword ? "Show password" : "Hide password"}/>
                            </div>
                            <input type={this.state.showPassword ? 'text' : 'password'}
                                   className='credentials-card-input' style={this.applyWritableInputStyle()}
                                   readOnly={this.state.readOnly} value={this.state.password}
                                   onChange={e => this.setState({password: e.target.value})}/>
                        </label>
                        <label className='credentials-card-label'>
                            <div>Description</div>
                            <textarea className='credentials-card-description' style={this.applyWritableInputStyle()}
                                      readOnly={this.state.readOnly}
                                      onChange={e => this.setState({description: e.target.value})}>
                                {this.state.description}
                            </textarea>
                        </label>
                    </div>
                }
                {
                    this.state.showDelete
                    && <div>
                        <h3>Delete {this.state.title}?</h3>
                        <div>
                            <button className='delete-btn' onClick={this.deleteRequest}>Yes</button>
                            <button className='delete-btn' onClick={() => this.setState({
                                showDelete: !this.state.showDelete
                            })}>No
                            </button>
                        </div>
                    </div>
                }
                {
                    this.state.errorMessage
                    && <div>
                        ${this.state.errorMessage}
                        <div className='close-btn' onClick={() => this.setState({errorMessage: null})}>X</div>
                    </div>
                }
            </div>
        );
    }
}

export default Credentials;