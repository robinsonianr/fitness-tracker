import React, {useEffect, useState} from "react";
import "./dashboard.scss";
import Header from "../../components/header/Header";
import {Customer} from "../../typing";
import {getCustomer} from "../../services/client";


export const Dashboard = () => {
    const [customer, setCustomer] = useState<Customer | undefined>(undefined);
    useEffect(() => {
        const fetchData = async () => {
            try {
                const id = localStorage.getItem("customerId")!;
                const customerId = parseInt(id, 10);
                const response = await getCustomer(customerId);
                console.log(response.data);

                setCustomer(response.data);
            } catch (error) {
                console.error("Could not retrieve customer: ", error);
            }
        };

        fetchData();
    }, []);
    return (
        <div>
            <Header title="Fit Track"/>
            Welcome {customer?.name}!
        </div>
    );
};

export default Dashboard;