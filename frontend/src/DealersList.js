import {Component} from "react";
import AppNavbar from "./AppNavbar";
import {Button, ButtonGroup, Container, Table} from "reactstrap";
import {Link} from "react-router-dom";

class DealersList extends Component {

    constructor(props) {
        super(props);
        this.state = {dealers: []};
        this.remove = this.remove.bind(this);
    }

    componentDidMount() {
        fetch('/users/dealers',
            {headers: {Authorization: `Bearer ${sessionStorage.getItem("session-token")}`}})
            .then(response => response.json())
            .then(data => this.setState({dealers: data}));
    }
    async remove(id) {
        await fetch(`/dealers/${id}`, {
            method: 'DELETE',
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            }
        }).then(() => {
            let updatedDealers = [...this.state.dealers].filter(i => i.id !== id);
            this.setState({dealers: updatedDealers});
        });
    }

    render() {
        const {dealers, isLoading} = this.state;

        if (isLoading) {
            return <p>Loading...</p>;
        }

        const dealerList = dealers.map(dealer => {
            return <tr key={dealer.id}>
                <td style={{whiteSpace: 'nowrap'}}>{dealer.login}</td>
                <td>{dealer.role}</td>
                <td>
                    <ButtonGroup>
                        <Button size="sm" color="primary" tag={Link} to={"/users/dealers/" + dealer.id}>Edit</Button>
                    </ButtonGroup>
                </td>
            </tr>
        });

        return (
            <div>
                <AppNavbar/>
                <Container fluid>
                    <div className="float-right">
                        <Button className="create-btn" color="success" tag={Link} to="/auth/register">Add Dealer</Button>
                    </div>
                    <h3>Dealers</h3>
                    <Table className="table">
                        <thead>
                        <tr>
                            <th width="30%">Login</th>
                            <th width="30%">Role</th>
                            <th width="40%">Actions</th>
                        </tr>
                        </thead>
                        <tbody>
                        {dealerList}
                        </tbody>
                    </Table>
                </Container>
            </div>
        );
    }
}export default DealersList;