import {Component} from "react";
import AppNavbar from "./AppNavbar";
import {Button, ButtonGroup, Container, Table} from "reactstrap";
import {Link} from "react-router-dom";

class ClientsList extends Component {

    constructor(props) {
        super(props);
        this.state = {clients: []};
        this.remove = this.remove.bind(this);
    }

    componentDidMount() {
        fetch('/users/clients',
            {headers: {Authorization: `Bearer ${sessionStorage.getItem("session-token")}`}})
            .then(response => response.json())
            .then(data => this.setState({clients: data}));
    }
    async remove(id) {
        await fetch(`/clients/${id}`, {
            method: 'DELETE',
            headers: {
                Authorization: `Bearer ${sessionStorage.getItem("session-token")}`,
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            }
        }).then(() => {
            let updatedclients = [...this.state.clients].filter(i => i.id !== id);
            this.setState({clients: updatedclients});
        });
    }

    render() {
        const {clients, isLoading} = this.state;

        if (isLoading) {
            return <p>Loading...</p>;
        }

        const clientList = clients.map(client => {
            return <tr key={client.id}>
                <td style={{whiteSpace: 'nowrap'}}>{client.login}</td>
                <td>{client.role}</td>
                <td>
                    <ButtonGroup>
                        <Button size="sm" color="primary" tag={Link} to={"/users/clients/" + client.id}>Edit</Button>
                    </ButtonGroup>
                </td>
            </tr>
        });

        return (
            <div>
                <AppNavbar/>
                <Container fluid>
                    <div className="float-right">
                        <Button className="create-btn" tag={Link} to="/auth/register">Add client</Button>
                    </div>
                    <h3>Clients</h3>
                    <Table className="table">
                        <thead>
                        <tr>
                            <th width="30%">Login</th>
                            <th width="30%">Role</th>
                            <th width="40%">Actions</th>
                        </tr>
                        </thead>
                        <tbody>
                        {clientList}
                        </tbody>
                    </Table>
                </Container>
            </div>
        );
    }
}export default ClientsList;