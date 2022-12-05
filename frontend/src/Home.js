import {Component} from "react";
import AppNavbar from "./AppNavbar";
import ReviewsList from "./ReviewsList";
import Slider from "./Slider"

class Home extends Component{
    render() {
        return (
            <div>
                <AppNavbar />
                <Slider />
                <h4 className="header">REVIEWS</h4>
                <ReviewsList />
            </div>
        );
    }
}export default Home