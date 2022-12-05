import {Component} from "react";
import AppNavbar from "./AppNavbar";

class ConfigurationsList extends Component{
    constructor(props) {
        super(props);
        this.state = {configs: []}
        this.remove = this.remove.bind(this);
    }
    componentDidMount() {
        fetch("/configurations",
            {
                headers: {Authorization: `Bearer ${sessionStorage.getItem("session-token")}`}
            })
            .then(resp => resp.json())
            .then(data => this.setState({configs: data}))

    }

    async remove(id) {
        await fetch(`/configurations/${id}`, {
            method: 'DELETE',
            headers: {
                Authorization: `Bearer ${sessionStorage.getItem("session-token")}`,
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            }
        }).then(() => {
            let updatedConfigs = [...this.state.configs].filter(i => i.id !== id);
            this.setState({configs: updatedConfigs});
        });
    }

    render() {
        const {configs} = this.state
        let role = sessionStorage.getItem("user-role")
        const configsList = configs.map(config => {
            let hr = "/configurations/" + config.id
            return (
                <div className="model-card" id={config.id}>
                    <div className="card-content">
                        <img src={'/img/' + config.imgPath} alt={config.configurationName}/>
                        <h4>Название: {config.configurationName}</h4>
                        <h4>Кол-во л/c: {config.power}</h4>
                        <h4>Цена: {config.price}</h4>
                        <h4>Тип кузова: {config.bodyType}</h4>
                        <h4>Класс: {config.carClass}</h4>
                        <h4>Трансмиссия: {config.transmissionType}</h4>
                        <h4>Привод: {config.driveType}</h4>

                        <p>{config.description}</p>
                        { role === 'DEALER' ? (
                            <div>
                                <button className="create-btn"><a href={hr}>EDIT</a></button>
                                <button onClick={() => {
                                    this.remove(config.id);
                                    this.remove(config.id);
                                }} className="register-btn">DELETE</button>
                            </div>
                        ) : ''}
                    </div>
                </div>)
        });

        return (
            <div>
                <AppNavbar/>
                { role === 'DEALER' ? (
                    <button className="create-btn"><a href="/configurations/new">CREATE CONFIGURATION</a></button>
                ) : ''}
                <div className="model-block">
                    {configsList}
                </div>
            </div>
        );
    }
}export default ConfigurationsList