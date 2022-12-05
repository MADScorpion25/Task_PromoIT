import React, { Component } from 'react';
import './App.css';
import Home from './Home';
import { BrowserRouter as Router, Route, Switch } from 'react-router-dom';
import CarEdit from "./CarEdit";
import ConfigurationEdit from "./ConfigurationEdit";
import ReviewPage from "./ReviewPage";
import LoginPage from "./LoginPage";
import RegisterPage from "./RegisterPage";
import AdminsList from "./AdminsList";
import DealersList from "./DealersList";
import ClientsList from "./ClientsList";
import ModelsList from "./ModelsList";
import ModelConfigurationsList from "./ModelConfigurationsList";
import ConfigurationsList from "./ConfigurationsList";
import OrdersList from "./OrdersList";
import OrderEdit from "./OrderEdit";
import ReviewEdit from "./ReviewEdit";
import UserEdit from "./UserEdit";
class App extends Component {
    render() {
        return (
            <Router>
                <Switch>
                    <Route path='/' exact={true} component={Home}/>
                    <Route path='/models-info/:id' component={ModelConfigurationsList}/>
                    <Route path='/models/:id' component={CarEdit}/>
                    <Route path='/models' component={ModelsList}/>
                    <Route path='/orders/:id' component={OrderEdit}/>
                    <Route path='/orders' component={OrdersList}/>
                    <Route path='/configurations/:id' exact={true} component={ConfigurationEdit}/>
                    <Route path='/configurations' exact={true} component={ConfigurationsList}/>
                    <Route path='/reviews/:id' component={ReviewPage}/>
                    <Route path='/reviews-edit/:id' component={ReviewEdit}/>
                    <Route path='/auth/sign-in' component={LoginPage}/>
                    <Route path='/auth/register' component={RegisterPage}/>
                    <Route path='/users/clients/:id' component={UserEdit}/>
                    <Route path='/users/dealers/:id' component={UserEdit}/>
                    <Route path='/users/admins/:id' component={UserEdit}/>
                    <Route path='/users/admins' component={AdminsList}/>
                    <Route path='/users/dealers' component={DealersList}/>
                    <Route path='/users/clients' component={ClientsList}/>
                </Switch>
            </Router>
        )
    }
}

export default App;