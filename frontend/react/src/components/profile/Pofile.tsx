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
            } catch (error) {
                console.error("Could not retrieve customer: ", error);
            }
        };

        fetchData();
    }, []);

    return (
        <div className="profile-container">
            <Sidebar customer={customer}/>
            <div className="main-content">
                <Navbar title={"Profile"} name={customer?.name}/>
                <div className="content">
                    {/* Your profile content */}
                    {/* Replace this with the actual content you want to display */}
                    <h1>Profile Details</h1>
                    <p>Name: {customer?.name}</p>
                    <p>Email: {customer?.email}</p>
                    {/* Add more profile details as needed */}
                </div>
            </div>
        </div>
    );
};

export default Profile;