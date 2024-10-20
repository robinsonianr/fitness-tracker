import React, {useEffect, useState} from "react";
import "./navbar.scss";
import {useNavigate} from "react-router-dom";
import {getCustomerProfileImage} from "../../services/client.ts";
import axios from "axios";

interface NavProps {
    title: string;
    name: string | undefined;
}

const Navbar: React.FC<NavProps> = ({title, name}) => {
    const navigate = useNavigate();
    const id = localStorage.getItem("customerId")!;
    const defaultImg = "/assets/user.png";

    const [pfp, setPfp] = useState<string | undefined>(undefined);

    const fetchPfp = async () => {
        try {
            const id = localStorage.getItem("customerId")!;
            const res = getCustomerProfileImage(id);
            const isImage = await checkImageUrl(res);

            if (isImage) {
                setPfp(res);
            }
        } catch (error) {
            console.error("Could not retrieve customer profile image: ", error);
        }
    };

    const checkImageUrl = async (url: string): Promise<boolean> => {
        try {
            const response = await axios.get(url);
            const contentType = response.headers["content-type"];
            return contentType && contentType.startsWith("image/");
        } catch (error) {
            console.error("Error checking image URL:", error);
            return false;
        }
    };
    useEffect(() => {
        fetchPfp()
            .then(r => r);
    }, []);


    const handleClick = () => {
        // Redirect to the desired route
        navigate(`/${id}/profile`);
    };

    return (
        <nav className="navbar">
            <div className="dashTitle">
                {title}
            </div>
            <div className="profile" onClick={handleClick}>
                <img src={pfp != undefined ? pfp : defaultImg} alt="pfp"/>
                {name}
            </div>
        </nav>
    );
};

export default Navbar;