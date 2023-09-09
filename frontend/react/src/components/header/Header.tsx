import React from "react";
import {useAuth} from "../context/AuthContext";

// @ts-ignore
export const Header = ({title}) => {
    const {logOut} = useAuth();
    return (
        <header>
            <h1>{title}</h1>
            <nav>
                <ul>
                    <li><a href="/">Home</a></li>
                    <li><a href="/profile">Profile</a></li>
                    <li onClick={logOut}><a href="/login">Logout</a></li>
                </ul>
            </nav>
        </header>
    );
};

export default Header;