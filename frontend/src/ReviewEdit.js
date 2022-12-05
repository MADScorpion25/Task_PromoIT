import {Component} from "react";
import AppNavbar from "./AppNavbar";
import axios from "axios";

class ReviewEdit extends Component {
    emptyItem = {
        title: '',
        description: '',
        fileUp: null
    };

    constructor(props) {
        super(props);
        this.state = {
            item: this.emptyItem,
        };
        this.handleChange = this.handleChange.bind(this);
        this.fileChange = this.fileChange.bind(this);
        this.handleSubmit = this.handleSubmit.bind(this);
    }

    async componentDidMount() {
        if (this.props.match.params.id !== 'new') {
            const config = await (await fetch(`/reviews/${this.props.match.params.id}`,
                {
                    headers: {Authorization: `Bearer ${sessionStorage.getItem("session-token")}`}
                })).json();
            this.setState({item: config});
        }
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
        if (item.id !== undefined) formData.append("id", item["id"])
        formData.append("title", item["title"]);
        formData.append("dealerLogin", sessionStorage.getItem("user-login"));
        formData.append("text", item["text"]);

        console.log(item)
        if (item.id === undefined) {
            await axios.post('/reviews', formData,
                {
                    Authorization: `Bearer ${sessionStorage.getItem("session-token")}`,
                    accept: 'multipart/form-data',
                    headers: 'multipart/form-data'
                }).then();
        } else {
            await axios.put('/reviews/edit/' + item.id, formData,
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
        item['fileUp'] = file;
        this.setState({item})
    }

    render() {
        const {item} = this.state;
        const title = <h2>{item.id ? 'Edit review' : 'Add review'}</h2>;
        return <div>
            <AppNavbar/>
            <div className="edit-block">
                {title}
                <form className="edit-form" onSubmit={this.handleSubmit} method="POST" encType="multipart/form-data">
                    <div>
                        <label htmlFor="title">Title</label>
                        <input type="text" name="title" id="title"
                               value={item.title || ''}
                               onChange={this.handleChange} autoComplete="configurationName" required/>
                    </div>
                    <div>
                        <label htmlFor="text">Text</label>
                        <textarea name="text" id="text" value={item.text || ''}
                                  onChange={this.handleChange} autoComplete="text" required/>
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

export default ReviewEdit