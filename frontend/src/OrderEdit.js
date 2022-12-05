import {Component, useState} from "react";
import AppNavbar from "./AppNavbar";

class OrderEdit extends Component{
    emptyItem = {
        configurationName: '',
        customerLogin: ''
    };

    constructor(props) {
        super(props);
        this.state = {
            item: this.emptyItem,
            cars: []
        };
        this.handleChange = this.handleChange.bind(this);
        this.handleSubmit = this.handleSubmit.bind(this);
    }

    async componentDidMount() {
        const crs = await (await fetch(`/cars`,
            {
                headers: {Authorization: `Bearer ${sessionStorage.getItem("session-token")}`}
            })).json();
        this.setState({cars: crs});
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
        item.customerLogin = sessionStorage.getItem("user-login")
        console.log(item)
        await fetch('/orders', {
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

        const title = <h2>Add Order</h2>;
        console.log(this.state.cars)
        return (<div>
            <AppNavbar/>
            <div className="edit-block">
                {title}
                <form className="edit-form" onSubmit={this.handleSubmit}>
                    <div>
                        <h3>Доступные конфигурации</h3>
                        <div className="order-block">
                            <label htmlFor="configurationName">Configs</label>
                            <select id="configurationName" name="configurationName" value={item.configurationName} onChange={this.handleChange} required={true}>
                                <option selected disabled hidden></option>
                                {this.state.cars.map(option => {
                                    return option.configurations.map((conf, i) => {
                                        return (<option key={i} value={conf.configurationName}>{option.brandName + " " + option.modelName + " " + conf.configurationName}</option>)
                                    })
                                })}
                            </select>
                        </div>
                    </div>
                    <button className="create-btn" type="submit">Save</button>{' '}
                    <button className="read-more-btn" to="/">Cancel</button>
                </form>
            </div>
        </div>)
    }
}export default OrderEdit