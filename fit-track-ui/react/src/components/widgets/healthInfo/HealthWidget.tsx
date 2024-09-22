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
                <p>Age: {healthInfo.age} years old</p>
                <p>Gender: {gender}</p>
                <p>Weight: {healthInfo.weight} lbs</p>
                <p>Height: {Math.floor(healthInfo.height / 12)}ft {healthInfo.height % 12} inches</p>
                <p>Weight Goal: {healthInfo.weightGoal} lbs</p>
                <p>Activity Level: {healthInfo.activity}</p>
                <p>Body Fat Percentage: {healthInfo.bodyFat}%</p>
            </div>
        </div>
    );
};

export default HealthWidget;