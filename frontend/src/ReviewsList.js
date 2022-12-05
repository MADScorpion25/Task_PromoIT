import {Component} from "react";

class ReviewsList extends Component{
    constructor(props) {
        super(props);
        this.state = {reviews: []};
        this.remove = this.remove.bind(this);
    }
    componentDidMount() {
        if(sessionStorage.getItem("user-role") !== 'DEALER'){
            fetch("/reviews",
                {headers: {Authorization: `Bearer ${sessionStorage.getItem("session-token")}`}})
                .then(review => review.json())
                .then(data => this.setState({reviews: data}))
        }
        else{
            fetch("/reviews/dealer/" + sessionStorage.getItem("user-login"),
                {headers: {Authorization: `Bearer ${sessionStorage.getItem("session-token")}`}})
                .then(review => review.json())
                .then(data => this.setState({reviews: data}))
        }
    }

    async remove(id) {
        await fetch(`/reviews/${id}`, {
            method: 'DELETE',
            headers: {
                Authorization: `Bearer ${sessionStorage.getItem("session-token")}`,
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            }
        }).then(() => {
            let updatedReviews = [...this.state.reviews].filter(i => i.id !== id);
            this.setState({reviews: updatedReviews});
        });
    }

    render() {
        const {reviews} = this.state;

        let role = sessionStorage.getItem("user-role")


        const reviewsList = reviews.length !== 0 ? (reviews.map(review => {
            let hr_read = `/reviews/${review.id}`
            let hr_edit = `/reviews-edit/${review.id}`
            let imgPath = `/img/${review.imgPath}`
            return (<div className="review-card" id={review.id}>
                <img src={imgPath} alt={imgPath}/>
                <div className="card-content">
                    <h4>{review.title}</h4>
                    <p>{review.text.substring(0, 170) + '...'}</p>
                    <button className="read-more-btn"><a href={hr_read}>READ MORE</a></button>
                    { role === 'DEALER' ? (
                        <div>
                            <button className="create-btn"><a href={hr_edit}>EDIT</a></button>
                            <button onClick={() => this.remove(review.id)} className="register-btn">DELETE</button>
                        </div>
                    ) : ''}
                </div>
            </div>)
        })): '';

        return (
            <div>
                { role === 'DEALER' ? (
                    <div>
                        <button className="create-btn"><a href="/reviews-edit/new">CREATE REVIEW</a></button>
                    </div>
                ) : ''}
                <div className="review-block">
                    {reviewsList}
                </div>
            </div>
        );
    }
}export default ReviewsList