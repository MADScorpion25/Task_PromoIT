import {Component} from "react";
import AppNavbar from "./AppNavbar";

class ModelsList extends Component{
    constructor(props) {
        super(props);
        this.state = {models: []}
        this.remove = this.remove.bind(this)
    }
    componentDidMount() {
        fetch("/cars",
            {
                headers: {Authorization: `Bearer ${sessionStorage.getItem("session-token")}`}
            })
            .then(resp => resp.json())
            .then(data => this.setState({models: data}))
    }

    async remove(id) {
        await fetch(`/cars/${id}`, {
            method: 'DELETE',
            headers: {
                Authorization: `Bearer ${sessionStorage.getItem("session-token")}`,
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            }
        }).then(() => {
            let updatedCars = [...this.state.models].filter(i => i.id !== id);
            this.setState({models: updatedCars});
        });
    }

    render() {
        const {models} = this.state;


        console.log(models)
        let role = sessionStorage.getItem("user-role")
        const modelsList = models.map(model => {
            let hr = '/models/' + model.id
            return (<div className="model-card" id={model.id}>
                {model.configurations.length !== 0 ? (
                    <img src={'/img/' + model.configurations[0].imgPath} alt="car"/>
                ): ''}
                <div className="card-content">
                    <h4>{model.brandName} {model.modelName} {model.productionYear}</h4>
                    <h4>Доступные конфигурации:</h4>
                    {model.configurations.length !== 0 ? (
                        <ul>
                            {
                                model.configurations.map(conf => {
                                    return(
                                        <li>{conf.configurationName}</li>
                                    )
                                })
                            }
                        </ul>
                    ): ''}
                    <button className="read-more-btn"><a href={"/models-info/" + model.id}>READ MORE</a></button>
                    { role === 'DEALER' ? (
                        <div>
                            <button className="create-btn"><a href={hr}>EDIT</a></button>
                            <button onClick={() => this.remove(model.id)} className="register-btn">DELETE</button>
                        </div>
                    ) : ''}
                </div>
            </div>)
        });

        return (
            <div>
                <AppNavbar/>
                { role === 'DEALER' ? (
                    <button className="create-btn"><a href="/models/new">CREATE CAR</a></button>
                ) : ''}
                <div className="model-block">
                    {modelsList}
                </div>
            </div>
        );
    }
}export default ModelsList