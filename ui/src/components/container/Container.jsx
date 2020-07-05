import React from 'react';
import './Container.css'
import Navigation from "../navigation/Navigation";
import AddCredentialsForm from "../addCredentialsForm/AddCredentialsForm";

class Container extends React.Component {

    constructor(props) {
        super(props);
        this.state = {
            showAddForm: false
        }
    }

    toggleAddFrom = () => {
        this.setState({
            showAddForm: !this.state.showAddForm
        })
    };

    showOverlay = () => {
        return this.state.showAddForm
            ? {backgroundColor: '#42424287', zIndex: 0}
            : {backgroundColor: 'transparent', zIndex: -100}
    };

    render() {
        return (
            <div className='container'>
                <aside>
                    <Navigation toggleAddForm={this.toggleAddFrom} showAddForm={this.state.showAddForm}/>
                </aside>
                <main>
                    <div className='overlay' style={this.showOverlay()} onClick={this.toggleAddFrom}/>
                    <AddCredentialsForm
                        showAddForm={this.state.showAddForm} updateCredentialsList={this.props.updateCredentialsList}/>
                    {this.props.children}
                </main>
            </div>
        );
    }
}

export default Container;