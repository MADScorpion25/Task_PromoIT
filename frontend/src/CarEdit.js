import {Component, useState} from "react";
import AppNavbar from "./AppNavbar";
import {Button, ButtonGroup, Table} from "reactstrap";
import {Link} from "react-router-dom";
import axios from "axios";

class CarEdit extends Component{
    emptyItem = {
        brandName: '',
        modelName: '',
        productionYear: '',
        curConfigs: [],
        freeConfigs: []
    };

    constructor(props) {
        super(props);
        this.state = {
            item: this.emptyItem,
        };
        this.curConfigs = []
        this.freeConfigs = []
        this.handleChange = this.handleChange.bind(this);
        this.removeFree = this.removeFree.bind(this)
        this.removeCur = this.removeCur.bind(this)
        this.handleSubmit = this.handleSubmit.bind(this);
        this.handleChangeCheck = this.handleChangeCheck.bind(this);
    }

    async componentDidMount() {
        if (this.props.match.params.id !== 'new') {
            const client = await (await fetch(`/cars/info/${this.props.match.params.id}`,
                {
                    headers: {Authorization: `Bearer ${sessionStorage.getItem("session-token")}`}
                })).json();
            this.setState({item: client});
        }
        else{
            const free = await (await fetch(`/configurations/free`,
                {
                    headers: {Authorization: `Bearer ${sessionStorage.getItem("session-token")}`}
                })).json();
            let item = {...this.state.item};
            item.freeConfigs = free
            this.setState({item});
        }
        this.freeConfigs = this.state.item.freeConfigs
        this.curConfigs = this.state.item.curConfigs
    }

    handleChange(event) {
        const target = event.target;
        const value = target.value;
        const name = target.name;
        let item = {...this.state.item};
        item[name] = value;
        this.setState({item});
    }

    handleChangeCheck(event) {
        const target = event.target;

        let item = {...this.state.item};

        this.freeConfigs = item.freeConfigs
        this.curConfigs = item.curConfigs
        if(target["checked"]) {
            this.curConfigs.push(target["id"]);
            this.removeFree(target['id'])
        }
        else {
            this.removeCur(target['id'])
            this.freeConfigs.push(target['id'])
        }

        item.freeConfigs = this.freeConfigs
        item.curConfigs = this.curConfigs

        this.setState({item})
    }
    removeFree(e) {
        let item = {...this.state.item};
        this.freeConfigs = this.freeConfigs.filter(s => s !== e)
        item.freeConfigs = this.freeConfigs
        this.setState({item})
    }
    removeCur(e) {
        let item = {...this.state.item};
        this.curConfigs = this.curConfigs.filter(s => s !== e)
        item.curConfigs = this.curConfigs
        this.setState({item})
    }

    async handleSubmit(event) {
        event.preventDefault();
        const {item} = this.state;

        item.curConfigs = this.curConfigs
        if(item.id === undefined){
            await fetch('/cars' + (item.id ? '/' + item.id : ''), {
                method: 'POST',
                headers: {
                    Authorization: `Bearer ${sessionStorage.getItem("session-token")}`,
                    'Accept': 'application/json',
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify(item),
            });
        }
        else{
            await fetch('/cars/edit/' + (item.id ? '/' + item.id : ''), {
                method: 'PUT',
                headers: {
                    Authorization: `Bearer ${sessionStorage.getItem("session-token")}`,
                    'Accept': 'application/json',
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify(item),
            });
        }
        this.props.history.push('/');
    }

    render() {
        const {item} = this.state;

        const title = <h2>{item.id ? 'Edit Car' : 'Add Car'}</h2>;

        let selected = [...new Set(item.curConfigs)];
        let selTable;
        if(item.curConfigs !== undefined){
            selTable = selected.map(conf => {
                return (<tr>
                    <td style={{whiteSpace: 'nowrap'}}>{conf}</td>
                    <td>
                        <input type="checkbox" id={conf} checked={true} onChange={this.handleChangeCheck}/>
                    </td>
                </tr>)
            })
        }
        let unselected = [...new Set(item.freeConfigs)];
        let nonSelTable;
        if(item.freeConfigs !== undefined){
            nonSelTable = unselected.map(conf => {
                return (<tr>
                    <td style={{whiteSpace: 'nowrap'}}>{conf}</td>
                    <td>
                        <input type="checkbox" id={conf} onChange={this.handleChangeCheck}/>
                    </td>
                </tr>)
            })
        }

        return (<div>
            <AppNavbar/>
            <div className="edit-block">
                {title}
                <form className="edit-form" onSubmit={this.handleSubmit}>
                    <div>
                        <label htmlFor="brandName">Brand Name</label>
                        <input type="text" name="brandName" id="brandName" value={item.brandName || ''}
                               onChange={this.handleChange} autoComplete="brandName" required/>
                    </div>
                   <div>
                       <label htmlFor="modelName">Model Name</label>
                       <input type="text" name="modelName" id="modelName" value={item.modelName || ''}
                              onChange={this.handleChange} autoComplete="modelName" required/>
                   </div>
                   <div>
                       <label htmlFor="productionYear">Production Year</label>
                       <input type="number" name="productionYear" id="productionYear" max="2023" min="1900" value={item.productionYear || ''}
                              onChange={this.handleChange} autoComplete="productionYear" required/>
                   </div>
                    <div>
                        <h3>Доступные конфигурации</h3>
                        <Table className="table">
                            <thead>
                            <tr>
                                <th width="30%">Conf Name</th>
                                <th width="40%">Checker</th>
                            </tr>
                            </thead>
                            <tbody>
                            {selTable}
                            {nonSelTable}
                            </tbody>
                        </Table>
                    </div>
                    <button className="create-btn" type="submit">Save</button>{' '}
                    <button className="read-more-btn" to="/">Cancel</button>
                </form>
            </div>
        </div>)
    }
}export default CarEdit