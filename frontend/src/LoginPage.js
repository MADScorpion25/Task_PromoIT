import {Component} from "react";

class LoginPage extends Component{
    emptyItem = {
        login: '',
        password: ''
    };
    testVar = {
        token: "0000000000",
        login: "",
        role: ""
    };

    constructor(props) {
        super(props);
        this.state = {
            item: this.emptyItem

        };
        this.handleChange = this.handleChange.bind(this);
        this.handleSubmit = this.handleSubmit.bind(this);
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
        let setResp = this;
        await fetch('/auth/sign-in', {
            method: 'POST',
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(item),
        })
            .then(function(response) {
                return response.json();
            })
            .then(function(response) {
                setResp.setState({ responseData: response });
            });
        if(this.state.responseData["login"] !== undefined){
            sessionStorage.setItem("session-token", this.state.responseData["token"])
            sessionStorage.setItem("user-login", this.state.responseData["login"])
            sessionStorage.setItem("user-role", this.state.responseData["role"])
            this.props.history.push('/');
        }
    }
    render() {
        const {item} = this.state;
        return(
            <div className="edit-block">
                <h4>Login Page</h4>
                <form className="edit-form" onSubmit={this.handleSubmit}>
                    <div>
                        <label htmlFor="login">Login</label>
                        <input type="text" name="login" id="login" value={item.login || ''}
                               onChange={this.handleChange} required/>
                    </div>
                    <div>
                        <label htmlFor="password">Password</label>
                        <input type="password" name="password" id="password" value={item.password || ''}
                               onChange={this.handleChange} required/>
                    </div>
                    <button className="login-btn" onClick={this.handleSubmit} type="submit">Login</button>{' '}
                    <button className="register-btn"><a href="/auth/register">Register</a></button>
                </form>
            </div>
        )
    }
}export default LoginPage