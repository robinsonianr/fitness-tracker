import React, {useEffect, useState} from "react";
import "./dashboard.scss";
import {Customer} from "../../typing";
import {getCustomer} from "../../services/client";
import Sidebar from "../sidebar/Sidebar.tsx";
import Navbar from "../navbar/Navbar.tsx";


export const Dashboard = () => {
    const [customer, setCustomer] = useState<Customer | undefined>(undefined);
    useEffect(() => {
        const fetchData = async () => {
            try {
                const id = localStorage.getItem("customerId")!;
                const response = await getCustomer(id);

                setCustomer(response.data);
            } catch (error) {
                console.error("Could not retrieve customer: ", error);
            }
        };

        fetchData();
    }, []);
    return (
        <div className="dashboard-container">
            <Sidebar customer={customer}/>
            <Navbar title={"Dashboard"} name={customer?.name}/>
        </div>
    );


};

export default Dashboard;