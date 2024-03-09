import React, {useEffect, useState} from "react";
import "./profile.scss";
import {Customer} from "../../typing";
import {getCustomer} from "../../services/client";

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
        <div>
            <h1>
                Hello {customer?.name}
            </h1>
        </div>
    );
};

export default Profile;