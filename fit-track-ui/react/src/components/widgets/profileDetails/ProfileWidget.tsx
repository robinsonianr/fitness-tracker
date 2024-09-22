import React from "react";
import "./profileWidget.scss";
import {ProfileDetailsWidget} from "../../../typing";

const ProfileWidget: React.FC<ProfileDetailsWidget> = ({profile}) => {
    return (
        <div className="profile-widget">
            {/* Profile Section */}
            <div className="profile-section">
                <h2>{profile.name}</h2>
                <p>Email: {profile.email}</p>
            </div>
        </div>
    );
};

export default ProfileWidget;