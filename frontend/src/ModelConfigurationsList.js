import {Component} from "react";
import AppNavbar from "./AppNavbar";

class ModelConfigurationsList extends Component{
    emptyItem = {
        brandName: '',
        modelName: '',
        productionYear: '',
        configurations: []
    };

    constructor(props) {
        super(props);
        this.state = {
            item: this.emptyItem
        };
    }

    componentDidMount() {
        console.log(this.props.match.params.id)
        fetch("/cars/info/" + this.props.match.params.id,
            {
                headers: {Authorization: `Bearer ${sessionStorage.getItem("session-token")}`}
            })
            .then(resp => resp.json())
            .then(data => this.setState({item: data}))
    }
    render() {
        const {item} = this.state;

        console.log(item)
        console.log(this.props.match.params.id)
        const modelsList = item.configurations.map(config => {
            return (
                <div className="model-card" id={item.id}>
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
                    </div>
                </div>)
        });

        return (
            <div>
                <AppNavbar/>
                <h4>{item.brandName} {item.modelName} {item.productionYear}</h4>
                <div className="model-block">
                    {modelsList}
                </div>
            </div>
        );
    }
}export default ModelConfigurationsList