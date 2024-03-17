import React from "react";
import "./navbar.scss";
import {useNavigate} from "react-router-dom";

interface NavProps {
    title: string;
    name: string | undefined;
}

const Navbar: React.FC<NavProps> = ({title, name}) => {
    const navigate = useNavigate();
    const id = localStorage.getItem("customerId");

    const handleClick = () => {
        // Redirect to the desired route
        navigate(`/${id}/profile`);
    };

    return (
        <nav className="navbar">
            <div className="dashTitle">
                {title}
            </div>
            <div className="profile"  onClick={handleClick}>
                <img src="/assets/user.png" alt="PFP" />
                {name}
            </div>
        </nav>
    );
};

export default Navbar;