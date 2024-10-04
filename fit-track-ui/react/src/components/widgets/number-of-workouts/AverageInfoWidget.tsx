import React from "react";
import {Customer} from "../../../typing";
import {isDateInThisWeek} from "../../../utils/utilities.ts";

const AverageInfoWidget = ({customer}: { customer: Customer }) => {

    let numOfWorkouts = 0;
    let avgVolume = 0;
    let avgCalorie = 0;
    let avgDuration = 0;
    if (customer?.workouts) {
        const workouts = customer?.workouts;
        for (let i = 0; i < workouts?.length; i++) {
            const date = workouts[i].workoutDate;
            if (isDateInThisWeek(date)) {
                numOfWorkouts++;
                avgCalorie += workouts[i].calories!;
                avgVolume += workouts[i].volume!;
                avgDuration += workouts[i].durationMinutes!;
            }
        }

        avgCalorie = Math.floor(avgCalorie / numOfWorkouts);
        avgVolume = Math.floor(avgVolume / numOfWorkouts);
        avgDuration = Math.floor(avgDuration / numOfWorkouts);

    }


    return (
        <div className="visual-widget" style={{width: "340px", height: "300px"}}>
            <div>
                <div style={{fontSize: 16}}>
                    <h1>{numOfWorkouts}</h1>
                </div>
                <div>
                    Number of Workouts This Week
                </div>
            </div>
            <div>
                <div style={{fontSize: 16}}>
                    <h1>{avgCalorie} kcal</h1>
                </div>
                <div>
                    Avg Calories Burned This Week
                </div>
            </div>
            <div>
                <div style={{fontSize: 16}}>
                    <h1>{avgVolume} lbs</h1>
                </div>
                <div>
                    Avg Volume Lifted This Week
                </div>
            </div>
            <div>
                <div style={{fontSize: 16}}>
                    <h1>{Math.floor(avgDuration)} min</h1>
                </div>
                <div>
                    Avg Minutes Per Workout This Week
                </div>
            </div>
        </div>
    );
};

export default AverageInfoWidget;