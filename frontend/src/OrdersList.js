import {Component} from "react";
import AppNavbar from "./AppNavbar";

class OrdersList extends Component {
    constructor(props) {
        super(props);
        this.state = {orders: []}
        this.handleChange = this.handleChange.bind(this);
    }

    componentDidMount() {
        let role = sessionStorage.getItem("user-role");
        if (role === 'CLIENT') {
            fetch("/orders/client/" + sessionStorage.getItem("user-login"),
                {
                    headers: {Authorization: `Bearer ${sessionStorage.getItem("session-token")}`}
                })
                .then(resp => resp.json())
                .then(data => this.setState({orders: data}))
        } else if (role === 'DEALER') {
            fetch("/orders",
                {
                    headers: {Authorization: `Bearer ${sessionStorage.getItem("session-token")}`}
                })
                .then(resp => resp.json())
                .then(data => this.setState({orders: data}))
        }
    }

    handleChange(event) {
        const target = event.target;

        fetch('/orders/edit/' + target["id"], {
            method: 'PUT',
            headers: {
                Authorization: `Bearer ${sessionStorage.getItem("session-token")}`,
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            }
        }).then(r => r.json());
        window.location.reload()
    }

    render() {
        const {orders} = this.state;

        let role = sessionStorage.getItem("user-role")
        const modelsList = orders.map(order => {
            return (<div className="model-card" id={order.id}>
                <div className="card-content">
                    <h4>Order on configuration: {order.configuration.configurationName}</h4>
                    <h4>Create
                        Date: {new Date(order.sendDate).getDate() + "-" + new Date(order.sendDate).getMonth() + "-" + new Date(order.sendDate).getFullYear()}</h4>
                    {role === 'DEALER' ? (
                        <div>
                            <h4>{order.customerLogin}</h4>
                        </div>
                    ) : ''}
                    {order.deliveryDate !== null ? (
                        <h4>Verify Date - is
                            delivered: {new Date(order.deliveryDate).getDate() + "-" + new Date(order.deliveryDate).getMonth() + "-" + new Date(order.deliveryDate)}</h4>

                    ) : ''}
                    <img src={'/img/' + order.configuration.imgPath}/>
                    {role === 'DEALER' && order.deliveryDate === null ? (
                        <div>
                            <button id={order.id} className="create-btn" onClick={this.handleChange}>VERIFY DELIVER
                            </button>
                        </div>
                    ) : ''}
                </div>
            </div>)
        });

        return (
            <div>
                <AppNavbar/>
                {role === 'CLIENT' ? (
                    <button className="create-btn"><a href="/orders/new">CREATE ORDER</a></button>
                ) : ''}
                <div className="model-block">
                    {modelsList}
                </div>
            </div>
        );
    }
}

export default OrdersList