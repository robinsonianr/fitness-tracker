import React from "react";
import "./healthWidget.scss";
import {HealthInfoWidget} from "../../../typing";


const HealthWidget: React.FC<HealthInfoWidget> = ({healthInfo}) => {
    let gender = "";
    if (healthInfo.gender == "MALE") {
        gender = "Male";
    } else if (healthInfo.gender == "FEMALE") {
        gender = "Female";
    } else {
        gender = "Forbidden";
    }

    return (
        // Health Information Section
        <div className="health-info-widget">
            <div className="health-info-section">
                <h3>Health Information</h3>
                <p><b>Age:</b> {healthInfo.age} years old</p>
                <p><b>Gender:</b> {gender}</p>
                <p><b>Weight:</b> {healthInfo.weight} lbs</p>
                <p><b>Height:</b> {Math.floor(healthInfo.height / 12)}ft {healthInfo.height % 12} inches</p>
                <p><b>Weight Goal:</b> {healthInfo.weightGoal} lbs</p>
                <p><b>Activity Level:</b> {healthInfo.activity}</p>
                <p><b>Body Fat Percentage:</b> {healthInfo.bodyFat}%</p>
            </div>
        </div>
    );
};

export default HealthWidget;