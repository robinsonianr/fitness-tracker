import React, {useEffect, useState} from "react";
import Sidebar from "../sidebar/Sidebar.tsx";
import Navbar from "../navbar/Navbar.tsx";
import {getAllWorkoutsByCustomerId, getCustomer} from "../../services/client.ts";
import "./logs.scss";
import {Customer, Workout} from "../../typing";
import WorkoutLogModal from "../modals/workout-log-modal/WorkoutLogModal.tsx";

const Logs = () => {
    const [customer, setCustomer] = useState<Customer>({});
    const [workoutData, setWorkoutData] = useState<Workout[]>([]);
    const [currentMonth, setCurrentMonth] = useState(new Date());
    const [selectedWorkout, setSelectedWorkout] = useState<Workout>();
    const [hoverDay, setHoverDay] = useState<number | null>(null);

    // Fetch customer data when component mounts
    useEffect(() => {
        const fetchData = async () => {
            try {
                const id = localStorage.getItem("customerId");
                const response = await getCustomer(id);
                const testRes = await getAllWorkoutsByCustomerId(id);
                setCustomer(response.data);
                setWorkoutData(testRes.data);
            } catch (error) {
                console.error("Could not retrieve customer: ", error);
            }
        };

        fetchData();
    }, []); // Run only once when the component mounts

    const daysOfWeek = ["Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat"];

    const [isModalOpen, setIsModalOpen] = useState(false);

    const openModal = () => {
        setIsModalOpen(true);
    };

    const closeModal = () => {
        setIsModalOpen(false);
    };

    const openWorkout = (workout: any) => {
        setSelectedWorkout(workout);
        openModal();
    };

    const getDaysInMonth = (date: Date) => {
        const year = date.getFullYear();
        const month = date.getMonth();
        return new Date(year, month + 1, 0).getDate();
    };

    const startDayOfMonth = (date: Date) => {
        const year = date.getFullYear();
        const month = date.getMonth();
        return new Date(year, month, 1).getDay();
    };

    const renderCalendarDays = () => {
        const daysInMonth = getDaysInMonth(currentMonth);
        const firstDay = startDayOfMonth(currentMonth);
        const daysArray: any[] = [];
        const today = new Date();
        const workouts: any[] = [];

        // Get workouts for the current month
        if (workoutData.length > 0) {
            for (let i = 0; i < workoutData.length; i++) {
                const date = new Date(workoutData[i].workoutDate);
                if (date.getMonth() === currentMonth.getMonth() && date.getFullYear() == currentMonth.getFullYear()) {
                    workouts.push(workoutData[i]);
                }
            }
        }

        // Fill in previous month's blank days
        for (let i = 0; i < firstDay; i++) {
            daysArray.push(<div key={`empty-${i}`} className="empty-day"></div>);
        }

        // Fill in the days of the current month
        for (let day = 1; day <= daysInMonth; day++) {
            const isToday =
                today.getDate() === day &&
                today.getMonth() === currentMonth.getMonth() &&
                today.getFullYear() === currentMonth.getFullYear();

            let minutes: any = null;

            const workoutForDay = workouts.find((workout) => {
                const workoutDate = new Date(workout.workoutDate);
                if (workoutDate.getDate() === day) {
                    minutes = workout.durationMinutes;
                }
                return workoutDate.getDate() === day;
            });

            const dayStyle: React.CSSProperties = {
                color: isToday
                    ? "white"
                    : workoutForDay
                        ? "lightgreen"
                        : "#888",
                cursor: workoutForDay ? "pointer" : "default",
                backgroundColor: isToday
                    ? hoverDay === day
                        ? "inherit"
                        : "#3f76c0"
                    : hoverDay === day
                        ? "#333"
                        : "inherit",
                transition: hoverDay === day ? `background-color ${0.3}s` : "inherit"
            };

            daysArray.push(
                <div
                    key={day}
                    className={"day"}
                    onClick={() => workoutForDay && openWorkout(workoutForDay)}
                    onMouseEnter={() => workoutForDay && setHoverDay(day)}
                    onMouseLeave={() => workoutForDay && setHoverDay(null)}
                    style={dayStyle}
                >
                    {day}
                    {minutes ? <><br/>{minutes} min</> : ""}
                </div>
            );
        }

        return daysArray;
    };

    const handlePreviousMonth = () => {
        setCurrentMonth(new Date(currentMonth.getFullYear(), currentMonth.getMonth() - 1, 1));
    };

    const handleNextMonth = () => {
        setCurrentMonth(new Date(currentMonth.getFullYear(), currentMonth.getMonth() + 1, 1));
    };

    return (
        <div className="logs-container">
            <Sidebar customer={customer}/>
            <Navbar title={"Logs"} name={customer.name}/>
            <WorkoutLogModal isOpen={isModalOpen} onClose={closeModal} workout={selectedWorkout!}/>
            <div className="logs-content">
                <div className="calendar-header">
                    <button onClick={handlePreviousMonth}>{"<"}</button>
                    <h2>
                        {currentMonth.toLocaleString("default", {month: "long"})}{" "}
                        {currentMonth.getFullYear()}
                    </h2>
                    <button onClick={handleNextMonth}>{">"}</button>
                </div>

                <div className="calendar">
                    {/* Days of the week */}
                    <div className="days-of-week">
                        {daysOfWeek.map((day) => (
                            <div key={day} className="day-of-week">
                                {day}
                            </div>
                        ))}
                    </div>

                    {/* Calendar days */}
                    <div className="days-grid">{renderCalendarDays()}</div>
                </div>
            </div>
        </div>
    );
};

export default Logs;
