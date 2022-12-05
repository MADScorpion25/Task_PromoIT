import {Component, useState} from "react";
import AppNavbar from "./AppNavbar";
import {Button, ButtonGroup, Table} from "reactstrap";
import {Link} from "react-router-dom";
import axios from "axios";

class UserEdit extends Component{
    emptyItem = {
        login: '',
        password: ''
    };

    constructor(props) {
        super(props);
        this.state = {
            item: this.emptyItem,
        };
        this.handleChange = this.handleChange.bind(this);
        this.handleSubmit = this.handleSubmit.bind(this);
    }

    async componentDidMount() {
        const user = await (await fetch(`/users/info/${this.props.match.params.id}`,
            {
                headers: {Authorization: `Bearer ${sessionStorage.getItem("session-token")}`}
            })).json();
        this.setState({item: user});
    }

    handleChange(event) {
        const target = event.target;
        const value = target.value;
        const name = target.name;
        let item = {...this.state.item};
        item[name] = value;
        this.setState({item});
    }

    async handleSubmit(event) {
        event.preventDefault();
        const {item} = this.state;

        await fetch('/users/edit/' + item.id, {
            method: 'PUT',
            headers: {
                Authorization: `Bearer ${sessionStorage.getItem("session-token")}`,
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(item),
        });
        this.props.history.push('/');
    }

    render() {
        const {item} = this.state;

        return (<div>
            <AppNavbar/>
            <div className="edit-block">
                <h2>Edit User</h2>
                <form className="edit-form" onSubmit={this.handleSubmit}>
                    <div>
                        <label htmlFor="login">Login</label>
                        <input type="text" name="login" id="login" value={item.login || ''} required={true}
                               onChange={this.handleChange} autoComplete="login"/>
                    </div>
                    <div>
                        <label htmlFor="password">Password</label>
                        <input type="password" name="password" id="password" value={item.password || ''}
                               onChange={this.handleChange} autoComplete="password"/>
                    </div>
                    <button className="create-btn" type="submit">Save</button>{' '}
                    <button className="read-more-btn" to="/">Cancel</button>
                </form>
            </div>
        </div>)
    }
}export default UserEdit