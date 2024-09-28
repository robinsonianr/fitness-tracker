import React, {useEffect, useState} from "react";
import "./profile.scss";
import {Customer} from "../../typing";
import {getCustomer, getCustomerProfileImage} from "../../services/client";
import Sidebar from "../sidebar/Sidebar.tsx";
import Navbar from "../navbar/Navbar.tsx";
import ProfileWidget from "../widgets/profileDetails/ProfileWidget.tsx";
import HealthWidget from "../widgets/healthInfo/HealthWidget.tsx";
import WorkoutHistoryWidget from "../widgets/workoutHistory/WorkoutHistoryWidget.tsx";
import HealthInfoModal from "../modals/edit-health-info/HealthInfoModal.tsx";
import axios from "axios";

export const Profile = () => {
    const [customer, setCustomer] = useState<Customer | undefined>(undefined);
    const [pfp, setPfp] = useState<string | undefined>(undefined);

    const fetchPfp = async (id: any) => {
        try {
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
        const id = localStorage.getItem("customerId")!;
        const fetchData = async () => {
            try {
                const customerId = parseInt(id, 10);
                const response = await getCustomer(customerId);
                setCustomer(response.data);
            } catch (error) {
                console.error("Could not retrieve customer: ", error);
            }
        };

        fetchPfp(id);
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
        email: customer?.email,
        memberSince: customer?.memberSince
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
            <HealthInfoModal isOpen={isModalOpen} onClose={closeModal} customer={customer} />
            <Sidebar customer={customer}/>
            <div className="main-content">
                <Navbar title={"Profile"} name={customer?.name}/>
                <div className="content">
                    <div>
                        <ProfileWidget profile={profile} pfp={pfp}/>
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