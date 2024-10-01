import React, {useEffect, useState} from "react";
import "./dashboard.scss";
import {Customer} from "../../typing";
import {getCustomer} from "../../services/client";
import Sidebar from "../sidebar/Sidebar.tsx";
import Navbar from "../navbar/Navbar.tsx";
import CalorieWidget from "../widgets/workout-calorie-visual/CalorieWidget.tsx";
import VolumeWidget from "../widgets/workout-volume-visual/VolumeWidget.tsx";
import DurationWidget from "../widgets/workout-duration-visual/DurationWidget.tsx";
import AverageInfoWidget from "../widgets/number-of-workouts/AverageInfoWidget.tsx";
import WorkoutToCalories from "../widgets/workout-to-calories/WorkoutToCalories.tsx";


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
            <div className="dashboard-content">
                <div className="visual-content">
                    <CalorieWidget customer={customer!}/>
                    <VolumeWidget customer={customer!}/>
                </div>
                <div>
                    <div className="minor-visual-content">
                        <DurationWidget customer={customer!}/>
                        <AverageInfoWidget customer={customer!}/>
                    </div>
                    <WorkoutToCalories customer={customer!}/>
                </div>
            </div>
        </div>
    );


};

export default Dashboard;