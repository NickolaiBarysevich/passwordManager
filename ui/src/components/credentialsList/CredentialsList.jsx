import React from "react";
import './CredentialsList.css'
import Credentials from "../credentials/Credentials";

class CredentialsList extends React.Component {

    constructor(props) {
        super(props);
        this.state = {
            credentialsList: []
        }
    }

    componentDidMount() {
        this.requestForCredentialsList();
        this.props.onRef(this)
    }

    componentWillUnmount() {
        this.props.onRef(undefined)
    }

    requestForCredentialsList = () => {
        fetch('/api/v1/credentials', {
            method: 'GET',
            headers: {
                'Content-type': 'application/json',
                'Accept': 'application/json'
            }
        }).then(response => {
            if (response.code === 200) {
                return response.json();
            } else {
                return response.json();
            }
        }).then(data => {
                this.setState({
                    credentialsList: data
                })
            })
    };

    render() {
        let credArray = this.state.credentialsList.map(c =>
            <Credentials id={c.id} title={c.title} updateCredentialsList={this.requestForCredentialsList}/>
        );
        return (
            <div className='credentials-container'>
                {credArray}
            </div>
        );
    }
}


export default CredentialsList;