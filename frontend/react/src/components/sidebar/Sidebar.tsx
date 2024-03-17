import React from "react";
import "./sidebar.scss";
import {useAuth} from "../context/AuthContext.tsx";
import {useNavigate} from "react-router-dom";

export const Sidebar = () => {
    const {logOut} = useAuth();
    const navigate = useNavigate();

    const id = localStorage.getItem("customerId");
    const goDash = () => {
        navigate("/dashboard");
    };
    const goProfile = () => {
        navigate(`/${id}/profile`);
    };
    return(
        <div className="sidebar">
            <div className="sidebar-logo">
                <div  className="logo" onClick={goDash}>
                    <img src="/assets/weight.png" alt="Gym Icon " className="gym-icon"/>
                     Fit Track
                </div>
            </div>
            <div className="sidebar-content">
                <ul className="side-menu">
                    <li className="">
                        <a onClick={goDash}>
                            <img src="/assets/dashboard.png" alt="dash"/>
                            <span >Dashboard</span>
                        </a>
                    </li>
                    <li className="">
                        <a onClick={goProfile}>
                            <img src="/assets/profile.png" alt="user"/>
                            <span >Profile</span>
                        </a>
                    </li>
                </ul>
                {/* Other sidebar content */}
            </div>
            <div className="add-workout">
                <a className="font-w500" href="/">Add New Workout</a>
            </div>
            <div className="logout-button-container">
                <button className="logout-button" onClick={logOut}>Logout</button>
            </div>
        </div>
    );
};

export default Sidebar;

