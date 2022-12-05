import {Component} from "react";
import AppNavbar from "./AppNavbar";
import axios from "axios";

class ConfigurationEdit extends Component {
    emptyItem = {
        configurationName: '',
        power: '',
        price: '',
        bodyType: '',
        carClass: '',
        driveType: '',
        transmissionType: '',
        description: '',
        fileUp: null
    };

    constructor(props) {
        super(props);
        this.state = {
            item: this.emptyItem,
            bodyTypes: [],
            carClasses: [],
            driveTypes: [],
            transmissionTypes: []
        };
        this.handleChange = this.handleChange.bind(this);
        this.fileChange = this.fileChange.bind(this);
        this.handleSubmit = this.handleSubmit.bind(this);
    }

    async componentDidMount() {
        if (this.props.match.params.id !== 'new') {
            const config = await (await fetch(`/configurations/${this.props.match.params.id}`,
                {
                    headers: {Authorization: `Bearer ${sessionStorage.getItem("session-token")}`}
                })).json();
            this.setState({item: config});
        }
        fetch("/configurations/body-types",
            {
                headers: {Authorization: `Bearer ${sessionStorage.getItem("session-token")}`}
            })
            .then(types => types.json())
            .then(data => this.setState({bodyTypes: data}))
        fetch("/configurations/car-classes",
            {
                headers: {Authorization: `Bearer ${sessionStorage.getItem("session-token")}`}
            })
            .then(types => types.json())
            .then(data => this.setState({carClasses: data}))
        fetch("/configurations/drive-types",
            {
                headers: {Authorization: `Bearer ${sessionStorage.getItem("session-token")}`}
            })
            .then(types => types.json())
            .then(data => this.setState({driveTypes: data}))
        fetch("/configurations/transmission-types",
            {
                headers: {Authorization: `Bearer ${sessionStorage.getItem("session-token")}`}
            })
            .then(types => types.json())
            .then(data => this.setState({transmissionTypes: data}))
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

        let formData = new FormData();
        if (item.fileUp !== null) formData.append("fileUp", item["fileUp"]);
        formData.append("configurationName", item["configurationName"]);
        formData.append("power", item["power"]);
        formData.append("price", item["price"]);
        formData.append("bodyType", item["bodyType"]);
        formData.append("carClass", item["carClass"]);
        formData.append("driveType", item["driveType"]);
        formData.append("transmissionType", item["transmissionType"]);
        formData.append("description", item["description"]);

        console.log(item)
        if (item.id === undefined) {
            await axios.post('/configurations', formData,
                {
                    Authorization: `Bearer ${sessionStorage.getItem("session-token")}`,
                    accept: 'multipart/form-data',
                    headers: 'multipart/form-data'
                }).then();
        } else {
            await axios.put('/configurations/edit/' + item.id, formData,
                {
                    Authorization: `Bearer ${sessionStorage.getItem("session-token")}`,
                    accept: 'multipart/form-data',
                    headers: 'multipart/form-data'
                }).then();
        }

        this.props.history.push('/');
    }

    fileChange(event) {
        event.preventDefault();
        const target = event.target;
        const file = target.files[0];
        let item = {...this.state.item};
        console.log(file)
        item['fileUp'] = file;
        this.setState({item})
    }

    render() {
        const {item} = this.state;
        const title = <h2>{item.id ? 'Edit configuration' : 'Add configuration'}</h2>;
        return <div>
            <AppNavbar/>
            <div className="edit-block">
                {title}
                <form className="edit-form" onSubmit={this.handleSubmit} method="POST" encType="multipart/form-data">
                    <div>
                        <label htmlFor="configurationName">Configuration Name</label>
                        <input type="text" name="configurationName" id="configurationName"
                               value={item.configurationName || ''}
                               onChange={this.handleChange} autoComplete="configurationName" required/>
                    </div>
                    <div>
                        <label htmlFor="power">Rower</label>
                        <input type="number" name="power" id="power" min="10" max="1000" value={item.power || ''}
                               onChange={this.handleChange} autoComplete="power" required/>
                    </div>
                    <div>
                        <label htmlFor="price">Price</label>
                        <input type="number" name="price" id="price" min="100000" max="10000000"
                               value={item.price || ''}
                               onChange={this.handleChange} autoComplete="price" required/>
                    </div>
                    <div>
                        <label htmlFor="bodyType">Body Type</label>
                        <select id="bodyType" name="bodyType" value={item.bodyType} onChange={this.handleChange} required>
                            <option selected disabled hidden></option>
                            {this.state.bodyTypes.map((option, key) =>
                                <option key={key} value={option.toString()}>{option}</option>
                            )}
                        </select>
                    </div>
                    <div>
                        <label htmlFor="carClass">Car Class</label>
                        <select id="carClass" name="carClass" value={item.carClass} onChange={this.handleChange} required>
                            <option selected disabled hidden></option>
                            {this.state.carClasses.map((option, key) =>
                                <option key={key} value={option.toString()}>{option}</option>
                            )}
                        </select>
                    </div>
                    <div>
                        <label htmlFor="driveType">Drive Type</label>
                        <select id="driveType" name="driveType" value={item.driveType} onChange={this.handleChange} required>
                            <option selected disabled hidden></option>
                            {this.state.driveTypes.map((option, key) =>
                                <option key={key} value={option.toString()}>{option}</option>
                            )}
                        </select>
                    </div>
                    <div>
                        <label htmlFor="transmissionType">Transmission Type</label>
                        <select id="transmissionType" name="transmissionType" value={item.transmissionType}
                                onChange={this.handleChange} required>
                            <option selected disabled hidden></option>
                            {this.state.transmissionTypes.map((option, key) =>
                                <option key={key} value={option.toString()}>{option}</option>
                            )}
                        </select>
                    </div>
                    <div>
                        <label htmlFor="description">Description</label>
                        <textarea name="description" id="description" value={item.description || ''}
                                  onChange={this.handleChange} autoComplete="description" required/>
                    </div>
                    {
                        item.id !== undefined ? (
                            <div>
                                <label>Preview</label>
                                <img className="preview" src={'/img/' + item.imgPath}/>
                                <label htmlFor="fileUp">Load</label>
                                <input type="file" name="fileUp" id="fileUp" required={false}
                                       onChange={this.fileChange} autoComplete="fileUp"/>
                            </div>
                        ) : (
                            <div>
                                <label htmlFor="fileUp">Img</label>
                                <input type="file" name="fileUp" id="fileUp" required
                                       onChange={this.fileChange} autoComplete="fileUp"/>
                            </div>
                        )
                    }
                    <button className="create-btn" type="submit">Save</button>
                    {' '}
                    <button className="read-more-btn" to="/">Cancel</button>
                </form>
            </div>
        </div>
    }
}

export default ConfigurationEdit