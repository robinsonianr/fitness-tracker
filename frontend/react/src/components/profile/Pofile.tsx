import React, {useEffect, useState} from "react";
import "./profile.scss";
import {Customer} from "../../typing";
import {getCustomer} from "../../services/client";
import Sidebar from "../sidebar/Sidebar.tsx";
import Navbar from "../navbar/Navbar.tsx";

export const Profile = () => {
    const [customer, setCustomer] = useState<Customer | undefined>(undefined);
    useEffect(() => {
        const fetchData = async () => {
            try {
                const id = localStorage.getItem("customerId")!;
                const customerId = parseInt(id, 10);
                const response = await getCustomer(customerId);
                setCustomer(response.data);
                console.log(response.data);
            } catch (error) {
                console.error("Could not retrieve customer: ", error);
            }
        };

        fetchData();
    }, []);

    return (
        <div className="profileContainer">
            <Sidebar/>
            <Navbar title={"Profile"} name={customer?.name}/>
        </div>
    );
};

export default Profile;