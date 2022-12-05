import {Component} from "react";

class RegisterPage extends Component{
    emptyItem = {
        login: '',
        password: '',
        role: ''
    };

    constructor(props) {
        super(props);
        this.state = {
            item: this.emptyItem,
            roles: []
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


    componentDidMount() {
        fetch("/users/roles",
            {
                headers: {
                    Authorization: `Bearer ${sessionStorage.getItem("session-token")}`
                }
            })
            .then(types => types.json())
            .then(data => this.setState({roles: data}))
    }

    async handleSubmit(event) {
        event.preventDefault();
        const {item} = this.state;

        console.log(item)
        let role = sessionStorage.getItem("user-role")
        await fetch(role === "ADMIN" ? '/users/new' : '/users/register', {
            method: 'POST',
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
        let role = sessionStorage.getItem("user-role")
        return(
            <div className="edit-block">
                <h4>Registration Page</h4>
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
                    {role === "ADMIN" ? (
                    <div>
                        <label htmlFor="role">Role</label>
                        <select id="role" name="role" value={item.role} onChange={this.handleChange} required>
                            <option selected disabled hidden></option>
                            {this.state.roles.map((option, key) =>
                                <option key={key} value={option.toString()}>{option}</option>
                            )}
                        </select>
                    </div>) : ('')}
                    <button className="register-btn" onClick={this.handleSubmit} type="submit">Register</button>{' '}
                </form>
            </div>
        )
    }
}export default RegisterPage