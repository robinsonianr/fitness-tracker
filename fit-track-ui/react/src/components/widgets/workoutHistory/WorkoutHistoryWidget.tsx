import React, {useState} from "react";
import "./workoutHistoryWidget.scss";
import {Customer, Workout} from "../../../typing";

const WorkoutHistoryWidget = ({customer}: { customer: Customer | undefined }) => {

    const [selectedOption, setSelectedOption] = useState<string | undefined>(undefined);
    const workouts: Workout[] = customer?.workouts || [];

    const handleSelectedChange = (event: React.ChangeEvent<HTMLSelectElement>) => {
        setSelectedOption(event.target.value);
    };
    const convertDate = (timestamp: string | undefined) => {
        return new Date(timestamp!).toDateString();
    };

    return (
        <div className="workout-history-widget">
            {/* Profile Section */}
            <div className="workout-section">
                <h2>Workout History</h2>
                <select className="workout-select" value={selectedOption} onChange={handleSelectedChange}>
                    <option value="">
                        Select Workout
                    </option>
                    {workouts.map((option, index) => (
                        <option key={option.id} value={index}>
                            Workout: {convertDate(option.workoutDate)}
                        </option>
                    ))}
                </select>
                {selectedOption ? (
                    <div className="workout-data">
                        <p><b>Workout Type:</b> {workouts[parseInt(selectedOption)].workoutType}</p>
                        <p><b>Exercises:</b> {workouts[parseInt(selectedOption)]?.exercises}</p>
                        <p><b>Calories:</b> {workouts[parseInt(selectedOption)]?.calories}</p>
                        <p><b>Duration:</b> {Math.floor(workouts[parseInt(selectedOption)].durationMinutes! / 60)}hr(s)
                            &nbsp;{workouts[parseInt(selectedOption)].durationMinutes! % 60} minutes</p>
                        <p><b>Date:</b> {convertDate(workouts[parseInt(selectedOption)]?.workoutDate)}</p>
                        <p><b>Volume:</b> {workouts[parseInt(selectedOption)]?.volume.toLocaleString()} lbs</p>
                    </div>
                ) : null}
            </div>
        </div>
    );
};

export default WorkoutHistoryWidget;