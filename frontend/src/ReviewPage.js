import {Component} from "react";
import AppNavbar from "./AppNavbar";

class ReviewPage extends Component{
    emptyItem = {
        title: '',
        text: '',
        imgPath: ''
    };

    constructor(props) {
        super(props);
        this.state = {
            item: this.emptyItem
        };
    }
    async componentDidMount() {
        if (this.props.match.params.id !== 'new') {
            const review = await (await fetch(`/reviews/${this.props.match.params.id}`,
                {headers: {Authorization: `Bearer ${sessionStorage.getItem("session-token")}`}}))
                .json();
            this.setState({item: review});
        }
    }
    render() {
        const {item} = this.state;
        let imgPath = `/img/${item.imgPath}`
        return <div>
            <AppNavbar/>
            <div className="info-block">
                <div>
                    <img src={imgPath} alt={item.imgPath}/>
                    <div className="card-content">
                        <h3>{item.title}</h3>
                        <p>{item.text}</p>
                    </div>
                </div>
            </div>
        </div>
    }
}export default ReviewPage