import React, {useEffect, useState} from "react";
import "./profile.scss";
import {Customer} from "../../typing";
import {getCustomer} from "../../services/client";
import Sidebar from "../sidebar/Sidebar.tsx";
import Navbar from "../navbar/Navbar.tsx";
import ProfileWidget from "../widgets/profileDetails/ProfileWidget.tsx";
import HealthWidget from "../widgets/healthInfo/HealthWidget.tsx";
import WorkoutHistoryWidget from "../widgets/workoutHistory/WorkoutHistoryWidget.tsx";
import HealthInfoModal from "../modals/edit-health-info/HealthInfoModal.tsx";

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

    const [isModalOpen, setIsModalOpen] = useState(false);

    const openModal = () => {
        setIsModalOpen(true);
    };

    const closeModal = () => {
        setIsModalOpen(false);
    };

    const profile = {
        name: customer?.name,
        email: customer?.email
    };

    const healthInfo = {
        age: customer?.age,
        gender: customer?.gender,
        weight: customer?.weight,
        height: customer?.height,
        weightGoal: customer?.weightGoal,
        activity: customer?.activity,
        bodyFat: customer?.bodyFat
    };

    return (
        <div className="profile-container">
            <Sidebar customer={customer}/>
            <div className="main-content">
                <HealthInfoModal isOpen={isModalOpen} onClose={closeModal} customer={customer} />
                <Navbar title={"Profile"} name={customer?.name}/>
                <div className="content">
                    <div>
                        <ProfileWidget profile={profile}/>
                    </div>
                    <div className="health-info">
                        <HealthWidget healthInfo={healthInfo}/>
                        <WorkoutHistoryWidget customer={customer}/>
                    </div>
                </div>
            </div>
            <div className="edit-health-button" onClick={openModal}>
                <a className="font-w500">Edit Health Info</a>
            </div>
        </div>
    );
};

export default Profile;