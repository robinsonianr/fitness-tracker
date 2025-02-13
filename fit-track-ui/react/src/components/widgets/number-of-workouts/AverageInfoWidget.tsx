import {useEffect, useState} from "react";
import {Workout} from "../../../typing";
import {isDateInSelectedWeek} from "../../../utils/utilities.ts";
import {getAllWorkoutsByCustomerId} from "../../../services/client.ts";

const AverageInfoWidget = ({weekDate}: { weekDate: string }) => {
    const [workoutData, setWorkoutData] = useState<Workout[]>([]);
    const [avgVolume, setAvgVolume] = useState<number>(0);
    const [avgCalorie, setAvgCalorie] = useState<number>(0);
    const [avgDuration, setAvgDuration] = useState<number>(0);
    const [numOfWorkouts, setNumOfWorkouts] = useState<number>(0);

    useEffect(() => {
        const fetchWorkoutData = async () => {
            try {
                const id = localStorage.getItem("customerId")!;
                const testRes = await getAllWorkoutsByCustomerId(id);

                setWorkoutData(testRes.data);
            } catch (error) {
                console.error("Could not retrieve workouts: ", error);
            }
        };

        fetchWorkoutData();
    }, []);



    useEffect(() => {
        let newAvgVolume = 0;
        let newAvgCalorie = 0;
        let newAvgDuration = 0;
        let numWorkout = 0;

        if (workoutData) {
            const workouts = workoutData;
            for (let i = 0; i < workouts.length; i++) {
                const date = new Date(workouts[i].workoutDate.toString());
                if (isDateInSelectedWeek(date, new Date(weekDate))) {
                    newAvgCalorie += workouts[i].calories!;
                    newAvgVolume += workouts[i].volume!;
                    newAvgDuration += workouts[i].durationMinutes!;
                    numWorkout++;
                }
            }

            setNumOfWorkouts(numWorkout);
            setAvgCalorie(Math.floor(newAvgCalorie / numWorkout));
            setAvgVolume(Math.floor(newAvgVolume / numWorkout));
            setAvgDuration(Math.floor(newAvgDuration / numWorkout));

        }
    }, [workoutData, weekDate]);


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