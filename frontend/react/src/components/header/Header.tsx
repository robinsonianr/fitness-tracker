// @ts-ignore
import React from "react";
import {useAuth} from "../context/AuthContext";

export const Header = ({title}:{title: any}) => {
    const {logOut} = useAuth();
    const id = localStorage.getItem("customerId");
    return (
        <header>
            <h1>{title}</h1>
            <nav>
                <ul>
                    <li><a href="/">Home</a></li>
                    <li><a href={`/profile/${id}`}>Profile</a></li>
                    <li onClick={logOut}><a href="/login">Logout</a></li>
                </ul>
            </nav>
        </header>
    );
};

export default Header;