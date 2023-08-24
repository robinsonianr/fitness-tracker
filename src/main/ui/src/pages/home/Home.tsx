import React from "react";
import "./home.scss"
import Header from "../../components/header/Header";
export const Home = () => {
    return (
        <div>
            <Header title="Fitness Tracker"/>
            <h1 className="home-title">Welcome to the Home Page!</h1>
        </div>
    );
};

export default Home;