import React, {useEffect, useState} from "react";
import "./dashboard.scss";
import {Customer} from "../../typing";
import {getCustomer} from "../../services/client";
import Sidebar from "../sidebar/Sidebar.tsx";
import Navbar from "../navbar/Navbar.tsx";
import CalorieWidget from "../widgets/workout-calorie-visual/CalorieWidget.tsx";
import VolumeWidget from "../widgets/workout-volume-visual/VolumeWidget.tsx";
import DurationWidget from "../widgets/workout-duration-visual/DurationWidget.tsx";
import AverageInfoWidget from "../widgets/number-of-workouts/AverageInfoWidget.tsx";
import WorkoutToCalories from "../widgets/workout-to-calories/WorkoutToCalories.tsx";
import {isDateInThisWeek, sortWorkoutsAsc} from "../../utils/utilities.ts";


export const Dashboard = () => {
    const [customer, setCustomer] = useState<Customer | undefined>(undefined);
    // const weeks: Date[] = [];
    const today = new Date();
    const thisWeek = new Date(today);
    thisWeek.setDate(today.getDate() - today.getDay());
    const [selectedWeek, setSelectedWeek] = useState<Date>(thisWeek);
    const [weeks, setWeeks] = useState<Date[]>([]);

    const handleOnchange = (event: React.ChangeEvent<HTMLSelectElement>) => {
        setSelectedWeek(new Date(event.target.value));
    };

    useEffect(() => {
        if (customer?.workouts) {
            const workouts = sortWorkoutsAsc(customer.workouts);
            let date: Date;
            const newWeeks: Date[] = [...weeks];

            workouts.forEach((workout) => {
                date = new Date(workout.workoutDate.toString());

                if (!isDateInThisWeek(date)) {
                    if (newWeeks.length > 0) {
                        const prevDate = newWeeks[newWeeks.length - 1];
                        const dayOfWeek = date.getDay();

                        // Get the first day of the week based on the workout date
                        const firstDayOfWeek = new Date(date);
                        firstDayOfWeek.setDate(date.getDate() - (dayOfWeek === 0 ? 6 : dayOfWeek));
                        firstDayOfWeek.setHours(0, 0, 0, 0);

                        // Get last day of the week of previous added week
                        const lastDayOfWeek = new Date(prevDate);
                        lastDayOfWeek.setDate(prevDate.getDate() + 6);

                        // Check to see if workout date is after the previous workout week
                        if (date > lastDayOfWeek) {
                            if (!newWeeks.some(week => week.getTime() === firstDayOfWeek.getTime())) {
                                newWeeks.unshift(firstDayOfWeek);
                            }
                        }
                    } else {
                        const dayOfWeek = date.getDay();
                        const firstDayOfWeek = new Date(date);
                        firstDayOfWeek.setDate(date.getDate() - dayOfWeek);
                        firstDayOfWeek.setHours(0, 0, 0, 0);

                        newWeeks.push(firstDayOfWeek);
                    }
                } else {
                    setSelectedWeek(thisWeek);
                }
            });

            setWeeks(newWeeks);
        }
    }, [customer]);

    useEffect(() => {
        const fetchData = async () => {
            try {
                const id = localStorage.getItem("customerId");
                const response = await getCustomer(id);

                setCustomer(response.data);
            } catch (error) {
                console.error("Could not retrieve customer: ", error);
            }
        };

        fetchData();
    }, []);

    return (
        <div className="dashboard-container">
            <Sidebar customer={customer}/>
            <Navbar title={"Dashboard"} name={customer?.name}/>

            <div className="dashboard-content">
                <div className="week-widget">
                    <select value={selectedWeek.toString()} onChange={handleOnchange} style={{width: 280}}>
                        <option value={thisWeek.toString()}>
                            Current: {thisWeek.toDateString()}
                        </option>
                        {weeks.map((option, index) => (
                            <option key={index} value={option.toString()}>
                                {option.toDateString()}
                            </option>
                        ))}
                    </select>
                </div>
                <div className="visual-container">
                    <div className="visual-content">
                        <CalorieWidget weekDate={selectedWeek.toString()}/>
                        <VolumeWidget weekDate={selectedWeek.toString()}/>
                    </div>
                    <div>
                        <div className="minor-visual-content">
                            <DurationWidget weekDate={selectedWeek.toString()}/>
                            <AverageInfoWidget weekDate={selectedWeek.toString()}/>
                        </div>
                        <WorkoutToCalories weekDate={selectedWeek.toString()}/>
                    </div>
                </div>
            </div>
        </div>
    );


};

export default Dashboard;