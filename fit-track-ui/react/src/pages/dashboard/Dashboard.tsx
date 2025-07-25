import { useEffect, useState } from "react";
import { useOutletContext } from "react-router-dom";
import { Customer } from "../../types/index.ts";
// import CalorieWidget from "../../components/common/widgets/workout-calorie-visual/CalorieWidget.tsx";
// import VolumeWidget from "../../components/common/widgets/workout-volume-visual/VolumeWidget.tsx";
// import DurationWidget from "../../components/common/widgets/workout-duration-visual/DurationWidget.tsx";
// import AverageInfoWidget from "../../components/common/widgets/number-of-workouts/AverageInfoWidget.tsx";
// import WorkoutToCalories from "../../components/common/widgets/workout-to-calories/WorkoutToCalories.tsx";
import { isDateInThisWeek, sortWorkoutsAsc } from "../../utils/utilities.ts";
// import WeightHistory from "../../components/common/widgets/weight-history-visual/WeightHistory.tsx";
import "./dashboard.css";

export const Dashboard = () => {
    const { customer } = useOutletContext<{ customer: Customer | undefined }>();
    
    const today = new Date();
    const thisWeek = new Date(today);
    thisWeek.setDate(today.getDate() - today.getDay());
    // const [selectedWeek, setSelectedWeek] = useState<Date>(thisWeek);
    const [weeks, setWeeks] = useState<Date[]>([]);

    // const handleOnchange = (event: React.ChangeEvent<HTMLSelectElement>) => {
    //     setSelectedWeek(new Date(event.target.value));
    // };

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

                        const firstDayOfWeek = new Date(date);
                        firstDayOfWeek.setDate(date.getDate() - (dayOfWeek === 0 ? 6 : dayOfWeek));
                        firstDayOfWeek.setHours(0, 0, 0, 0);

                        const lastDayOfWeek = new Date(prevDate);
                        lastDayOfWeek.setDate(prevDate.getDate() + 6);

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
                    // setSelectedWeek(thisWeek);
                }
            });

            setWeeks(newWeeks);
        }
    }, [customer]);

    return (
        <div className="dashboard-container">
            {/* Tabs */}
            <div className="tabs-container">
                <div className="tabs-wrapper">
                    <button className="tab-button-active">
                        Overview
                    </button>
                    <button className="tab-button-inactive">
                        Workouts
                    </button>
                    <button className="tab-button-inactive">
                        Nutrition
                    </button>
                    <button className="tab-button-inactive">
                        Body
                    </button>
                </div>
            </div>

            {/* Summary Widgets */}
            <div className="summary-widgets">
                <div className="widget-card">
                    <div className="widget-content">
                        <div>
                            <p className="widget-info">Workouts This Week</p>
                            <p className="widget-value">0</p>
                            <p className="widget-target">Target: 4 workouts</p>
                        </div>
                        <div className="widget-icon-container widget-icon-purple">
                            <svg className="widget-icon icon-purple" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                                <path strokeLinecap="round" strokeLinejoin="round" strokeWidth={2} d="M9 6v6m0 0v6m0-6h6m-6 0H9" />
                            </svg>
                        </div>
                    </div>
                </div>

                <div className="widget-card">
                    <div className="widget-content">
                        <div>
                            <p className="widget-info">Calories Burned</p>
                            <p className="widget-value">0</p>
                            <p className="widget-target">Target: 2,500 kcal</p>
                        </div>
                        <div className="widget-icon-container widget-icon-orange">
                            <svg className="widget-icon icon-orange" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                                <path strokeLinecap="round" strokeLinejoin="round" strokeWidth={2} d="M17.657 18.657A8 8 0 016.343 7.343S7 9 9 10c0-2 .5-5 2.986-7C14 5 16.09 5.777 17.656 7.343A7.975 7.975 0 0120 13a7.975 7.975 0 01-2.343 5.657z" />
                            </svg>
                        </div>
                    </div>
                </div>

                <div className="widget-card">
                    <div className="widget-content">
                        <div>
                            <p className="widget-info">Active Minutes</p>
                            <p className="widget-value">0</p>
                            <p className="widget-target">Target: 150 minutes</p>
                        </div>
                        <div className="widget-icon-container widget-icon-blue">
                            <svg className="widget-icon icon-blue" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                                <path strokeLinecap="round" strokeLinejoin="round" strokeWidth={2} d="M12 8v4l3 3m6-3a9 9 0 11-18 0 9 9 0 0118 0z" />
                            </svg>
                        </div>
                    </div>
                </div>

                <div className="widget-card">
                    <div className="widget-content">
                        <div>
                            <p className="widget-info">Volume Lifted</p>
                            <p className="widget-value">0</p>
                            <p className="text-sm text-green-600 dark:text-green-400">+12% from last week</p>
                        </div>
                        <div className="widget-icon-container widget-icon-green">
                            <svg className="widget-icon icon-green" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                                <path strokeLinecap="round" strokeLinejoin="round" strokeWidth={2} d="M3 6l3 1m0 0l-3 9a5.002 5.002 0 006.001 0M6 7l3 9M6 7l6-2m6 2l3-1m-3 1l-3 9a5.002 5.002 0 006.001 0M18 7l3 9m-3-9l-6-2m0-2v2m0 16V5m0 16H9m3 0h3" />
                            </svg>
                        </div>
                    </div>
                </div>
            </div>

            {/* Activity Sections */}
            <div className="activity-sections">
                <div className="activity-card">
                    <h3 className="activity-title">Workout Activity</h3>
                    <p className="activity-description">Your workout frequency and duration over time</p>
                    <div className="empty-state-container">
                        <div className="empty-state-icon">
                            <svg className="empty-state-icon-svg" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                                <path strokeLinecap="round" strokeLinejoin="round" strokeWidth={2} d="M9 19v-6a2 2 0 00-2-2H5a2 2 0 00-2 2v6a2 2 0 002 2h2a2 2 0 002-2zm0 0V9a2 2 0 012-2h2a2 2 0 012 2v10m-6 0a2 2 0 002 2h2a2 2 0 002-2m0 0V5a2 2 0 012-2h2a2 2 0 012 2v14a2 2 0 01-2 2h-2a2 2 0 01-2-2z" />
                            </svg>
                        </div>
                        <p className="empty-state-text">No workout data yet</p>
                        <p className="empty-state-subtext">Add your first workout to see your activity chart</p>
                        <button className="primary-button">
                            + Add Workout
                        </button>
                    </div>
                </div>

                <div className="activity-card">
                    <h3 className="activity-title">Weight Trend</h3>
                    <p className="activity-description">Track your weight progress over time</p>
                    <div className="empty-state-container">
                        <div className="empty-state-icon">
                            <svg className="empty-state-icon-svg" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                                <path strokeLinecap="round" strokeLinejoin="round" strokeWidth={2} d="M3 6l3 1m0 0l-3 9a5.002 5.002 0 006.001 0M6 7l3 9M6 7l6-2m6 2l3-1m-3 1l-3 9a5.002 5.002 0 006.001 0M18 7l3 9m-3-9l-6-2m0-2v2m0 16V5m0 16H9m3 0h3" />
                            </svg>
                        </div>
                        <p className="empty-state-text">No weight data yet</p>
                        <p className="empty-state-subtext">Log your weight to track your progress</p>
                        <button className="secondary-button">
                            + Log Weight
                        </button>
                    </div>
                </div>
            </div>

            {/* Goals and Recent Activity */}
            <div className="goals-section">
                <div className="goals-card">
                    <h3 className="activity-title">Weekly Goals</h3>
                    <p className="activity-description">Your progress toward weekly fitness goals</p>
                    <div className="goals-grid">
                        <div className="goal-item">
                            <div className="goal-circle goal-circle-pink">
                                <span className="goal-percentage goal-percentage-pink">25%</span>
                            </div>
                            <span className="goal-label">Cardio</span>
                        </div>
                        <div className="goal-item">
                            <div className="goal-circle goal-circle-purple">
                                <span className="goal-percentage goal-percentage-purple">50%</span>
                            </div>
                            <span className="goal-label">Strength</span>
                        </div>
                        <div className="goal-item">
                            <div className="goal-circle goal-circle-green">
                                <span className="goal-percentage goal-percentage-green">80%</span>
                            </div>
                            <span className="goal-label">Flexibility</span>
                        </div>
                    </div>
                </div>

                <div className="recent-workouts-card">
                    <div className="recent-workouts-header">
                        <h3 className="recent-workouts-title">Recent Workouts</h3>
                        <button className="view-all-button">
                            View All
                        </button>
                    </div>
                    <p className="recent-workouts-description">Your latest training sessions</p>
                    <div className="recent-workouts-empty">
                        <div className="recent-workouts-empty-icon">
                            <svg className="empty-state-icon-svg" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                                <path strokeLinecap="round" strokeLinejoin="round" strokeWidth={2} d="M9 6v6m0 0v6m0-6h6m-6 0H9" />
                            </svg>
                        </div>
                        <p className="recent-workouts-empty-text">No recent workouts</p>
                    </div>
                </div>
            </div>
        </div>
    );
};

export default Dashboard;