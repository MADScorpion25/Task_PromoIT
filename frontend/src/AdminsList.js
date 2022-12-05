import {Component} from "react";
import AppNavbar from "./AppNavbar";
import {Button, ButtonGroup, Container, Table} from "reactstrap";
import {Link} from "react-router-dom";

class AdminsList extends Component {

    constructor(props) {
        super(props);
        this.state = {admins: []};
    }

    componentDidMount() {
        fetch('/users/admins',
            {headers: {Authorization: `Bearer ${sessionStorage.getItem("session-token")}`}})
            .then(response => response.json())
            .then(data => this.setState({admins: data}));
    }

    render() {
        const {admins, isLoading} = this.state;

        if (isLoading) {
            return <p>Loading...</p>;
        }

        const adminList = admins.map(admin => {
            if(admin.login !== sessionStorage.getItem("user-login"))
                return (
                <tr key={admin.id}>
                    <td style={{whiteSpace: 'nowrap'}}>{admin.login}</td>
                    <td>{admin.role}</td>
                    <td>
                        <ButtonGroup>
                            <Button><a href={'/users/admins/' + admin.id}>Edit</a></Button>
                        </ButtonGroup>
                    </td>
                </tr>
            )
        });

        return (
            <div>
                <AppNavbar/>
                <Container fluid>
                    <div className="float-right">
                        <Button className="create-btn" color="success" tag={Link} to="/auth/register">Add admin</Button>
                    </div>
                    <h3>Admins</h3>
                    <Table className="table">
                        <thead>
                        <tr>
                            <th width="30%">Login</th>
                            <th width="30%">Role</th>
                            <th width="40%">Actions</th>
                        </tr>
                        </thead>
                        <tbody>
                        {adminList}
                        </tbody>
                    </Table>
                </Container>
            </div>
        );
    }
}export default AdminsList;