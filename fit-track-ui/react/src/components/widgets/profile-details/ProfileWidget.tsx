import React, {useRef} from "react";
import "./profile-widget.scss";
import {ProfileDetailsWidget} from "../../../typing";
import {uploadCustomerProfileImage} from "../../../services/client.ts";

const ProfileWidget: React.FC<ProfileDetailsWidget> = ({profile, pfp}) => {
    const defaultImg = "/assets/user.png";
    const fileInputRef = useRef<HTMLInputElement>(null);
    const customerId = localStorage.getItem("customerId");
    const monthNames = [
        "January", "February", "March", "April", "May", "June", "July",
        "August", "September", "October", "November", "December"
    ];
    const profileMember = profile.memberSince?.toString();
    const month = new Date(profileMember).getMonth();
    const memberDate = monthNames[month] + " " + new Date(profileMember).getFullYear();

    const handleFileChange = async (event: React.ChangeEvent<HTMLInputElement>) => {
        const file = event.target.files?.[0];

        if (file) {
            await uploadPFP(file);
        }
    };

    const uploadPFP = async (file: File) => {
        const formData = new FormData();
        formData.append("file", file);

        try {
            await uploadCustomerProfileImage(customerId, formData);
        } catch (error) {
            console.error("File upload failed");
        }
    };

    const handleButtonClick = () => {
        if (fileInputRef.current) {
            fileInputRef.current.click();
        }
    };


    return (
        <div className="profile-widget">
            <div className="edit-pfp">
                <img className="profile-img" src={pfp != undefined ? pfp : defaultImg} alt="pfp"/>
                <input
                    type="file"
                    ref={fileInputRef}
                    style={{ display: "none" }}
                    accept="image/*"
                    onChange={handleFileChange}
                />
                <button className="edit-button" onClick={handleButtonClick}>✎</button>
            </div>
            {/* Profile Section */}
            <div className="profile-section">
                <h2>{profile.name}</h2>
                <div className="member-details">
                    <p><b>Email:</b> {profile.email}</p>
                    <p><b>Member Since:</b> {memberDate}</p>
                </div>
            </div>
        </div>
    );
};

export default ProfileWidget;