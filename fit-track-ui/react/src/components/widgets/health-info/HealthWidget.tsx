import React from "react";
import "./health-widget.scss";
import {HealthInfoWidget} from "../../../typing";


const HealthWidget: React.FC<HealthInfoWidget> = ({healthInfo}) => {
    let height: number;

    if (healthInfo.height) {
        height = healthInfo.height;
    } else {
        height = 0;
    }

    return (
        // Health Information Section
        <div className="health-info-widget">
            <div className="health-info-section">
                <h3>Health Information</h3>
                <p><b>Age:</b> {healthInfo.age} years old</p>
                <p><b>Gender:</b> {healthInfo.gender}</p>
                <p><b>Weight:</b> {healthInfo.weight === null ? 0 : healthInfo.weight} lbs</p>
                <p><b>Height:</b> {Math.floor(height / 12)}ft {height % 12} inches</p>
                <p><b>Weight Goal:</b> {healthInfo.weightGoal === null ? 0 : healthInfo.weightGoal} lbs</p>
                <p><b>Activity Level:</b> {healthInfo.activity === null ? "N/A" : healthInfo.activity}</p>
                <p><b>Body Fat Percentage:</b> {healthInfo.bodyFat === null ? 0 : healthInfo.bodyFat}%</p>
            </div>
        </div>
    );
};

export default HealthWidget;