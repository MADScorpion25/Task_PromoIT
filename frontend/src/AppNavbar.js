import {Component} from "react";
import React from 'react'

class AppNavbar extends Component{

    constructor(props) {
        super(props);
        this.state = {
            item: this.emptyItem
        };
        this.handleSubmit = this.handleSubmit.bind(this);
    }

    async handleSubmit(event) {
        event.preventDefault();

        localStorage.clear();
        sessionStorage.clear();
        window.location.href = '/';
        this.render()
    }
    render() {
        let isLoggedIn = false
        let login = sessionStorage.getItem("user-login")
        let role = sessionStorage.getItem("user-role")
        if(login !== "" && login !== null && login !== undefined)isLoggedIn = true
        return <nav>
            <ul className="nav-panel">
                <li><a className="logo" href="/">MARSCARS</a></li>

                    {role === "ADMIN" ? (
                    <div className="nav-links">
                        <li><a className="nav-a" href="/users/admins">ADMINS</a></li>
                        <li><a className="nav-a" href="/users/dealers">DEALERS</a></li>
                        <li><a className="nav-a" href="/users/clients">CLIENTS</a></li>
                    </div>
                    ) : "" }
                    {role === "DEALER" ? (
                        <div className="nav-links">
                            <li><a className="nav-a" href="/models">MODELS</a></li>
                            <li><a className="nav-a" href="/orders">ORDERS</a></li>
                            <li><a className="nav-a" href="/configurations">CONFIGS</a></li>
                        </div>
                        ) : ""}
                    {role === "CLIENT" || role === null ? (
                        <div className="nav-links">
                            <li><a className="nav-a" href="/models">MODELS</a></li>
                            <li><a className="nav-a" href="/orders">ORDERS</a></li>
                        </div>
                    ) : ""}
                {!isLoggedIn ? (
                    <li><button className="button-sign-in"><a href="/auth/sign-in">SIGN IN</a></button></li>
                ) : (
                    <div className="nav-links">
                        <li>{login}</li>
                        <li><button className="button-sign-in" onClick={this.handleSubmit}>LOGOUT</button></li>
                    </div>
                )}

            </ul>
        </nav>
    }
}export default AppNavbar