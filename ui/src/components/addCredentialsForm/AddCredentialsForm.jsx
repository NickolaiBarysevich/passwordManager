import React from "react";
import './AddCredentialsForm.css'

class AddCredentialsForm extends React.Component {

    constructor(props) {
        super(props);
        this.state = {
            title: null,
            username: null,
            password: null,
            description: null,
            errorMessage: null,
            isSaved: false
        }
    }

    sendSaveRequest = (e) => {
        e.preventDefault();
        fetch("/api/v1/credentials", {
            method: 'POST',
            cache: 'no-cache',
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
        })
            .then(response =>
                response.status === 201
                    ? JSON.parse('{}')
                    : response.json()
            )
            .then(data => {
                console.log(data);
                if (data.serviceCode) {
                    this.setState({
                        errorMessage: data.message
                    })
                } else {
                    this.setState({
                        errorMessage: null,
                        isSaved: true
                    });
                    this.props.updateCredentialsList();
                }
            })
    };

    handleTitle = (e) => {
        this.setState({
            title: e.target.value
        })
    };

    handleUsername = (e) => {
        this.setState({
            username: e.target.value
        })
    };

    handlePassword = (e) => {
        this.setState({
            password: e.target.value
        })
    };

    handleDescription = (e) => {
        this.setState({
            description: e.target.value
        })
    };

    addStylesWrapper = () => {
        return this.props.showAddForm
            ? {
                left: '160px',
            }
            : {
                left: '-280px',
            };
    };


    hideMessagesOnClose = () => {
        if (!this.props.showAddForm) {
            this.setState({
                error: false,
                errorMessage: null,
                isSaved: false
            })
        }
    };

    showError = () => {
        if (this.state.errorMessage) {
            return <div className='info-block error'>
                {this.state.errorMessage}
                <div className='close-btn' onClick={() => this.setState({errorMessage: null})}>X</div>
            </div>
        }
    };

    showSaved = () => {
        if (this.state.isSaved) {
            return <div className='info-block success'>
                Credentials saved!
                <div className='close-btn' onClick={() => this.setState({isSaved: false})}>X</div>
            </div>
        }
    };

    render() {
        return (
            <div className='form-wrapper' style={this.addStylesWrapper()} onClick={this.props.toggleAddForm}>
                <form className='add-form' onSubmit={this.sendSaveRequest}>
                    {this.showError()}
                    <label className='add-form-label'>
                        Title:
                        <br/>
                        <input id='add-title' type='text' onChange={this.handleTitle}/>
                    </label>
                    <label className='add-form-label'>
                        Username:
                        <br/>
                        <input id='add-username' type='text' onChange={this.handleUsername}/>
                    </label>
                    <label className='add-form-label'>
                        Password:
                        <br/>
                        <input id='add-password' type='password' onChange={this.handlePassword}/>
                    </label>
                    <label id='textarea-label' className='add-form-label'>
                        Description:
                        <br/>
                        <textarea id='add-description' className='description-textarea'
                                  onChange={this.handleDescription}/>
                    </label>
                    <button className='save-btn' type='submit'>Save</button>
                    {this.showSaved()}
                </form>
            </div>
        );
    }
}

export default AddCredentialsForm;