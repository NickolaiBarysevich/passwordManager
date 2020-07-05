import React from 'react';
import {Switch, Route} from 'react-router-dom'
import './App.css';
import Container from './components/container/Container'
import CredentialsList from "./components/credentialsList/CredentialsList";
import Login from "./components/login/Login";
import Registration from "./components/registration/Registration";
import ErrorPage from "./components/errorPage/ErrorPage";

class App extends React.Component {

    updateCredList = () => {
        this.child.requestForCredentialsList()
    };

    render() {
        const credList = <CredentialsList onRef={ref => this.child = ref}/>;
        return (
            <Container updateCredentialsList={this.updateCredList}>
                <Switch>
                    <Route path='/auth/login' component={Login}/>
                    <Route exact path='/' component={() => credList}/>
                    <Route path='/registration' component={Registration}/>
                    <Route path='*' component={ErrorPage}/>
                </Switch>
            </Container>
        );
    }
}

export default App;