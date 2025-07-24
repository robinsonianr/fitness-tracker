import React, { useEffect, useState } from "react";
import { useOutletContext } from "react-router-dom";
import { Customer } from "../../types/index.ts";
// import CalorieWidget from "../../components/common/widgets/workout-calorie-visual/CalorieWidget.tsx";
// import VolumeWidget from "../../components/common/widgets/workout-volume-visual/VolumeWidget.tsx";
// import DurationWidget from "../../components/common/widgets/workout-duration-visual/DurationWidget.tsx";
// import AverageInfoWidget from "../../components/common/widgets/number-of-workouts/AverageInfoWidget.tsx";
// import WorkoutToCalories from "../../components/common/widgets/workout-to-calories/WorkoutToCalories.tsx";
import { isDateInThisWeek, sortWorkoutsAsc } from "../../utils/utilities.ts";
// import WeightHistory from "../../components/common/widgets/weight-history-visual/WeightHistory.tsx";

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
                    // setSelectedWeek(thisWeek); // This line was commented out in the original file
                }
            });

            setWeeks(newWeeks);
        }
    }, [customer]);

    return (
        <div className="p-6 bg-gray-50 dark:bg-gray-900 min-h-full">
            {/* Tabs */}
            <div className="mb-6">
                <div className="flex space-x-1 bg-white dark:bg-gray-800 rounded-lg p-1 border border-gray-200 dark:border-gray-700">
                    <button className="flex-1 px-4 py-2 text-sm font-medium text-purple-600 dark:text-purple-400 bg-purple-100 dark:bg-purple-900/30 rounded-md">
                        Overview
                    </button>
                    <button className="flex-1 px-4 py-2 text-sm font-medium text-gray-500 dark:text-gray-400 hover:text-gray-700 dark:hover:text-gray-300">
                        Workouts
                    </button>
                    <button className="flex-1 px-4 py-2 text-sm font-medium text-gray-500 dark:text-gray-400 hover:text-gray-700 dark:hover:text-gray-300">
                        Nutrition
                    </button>
                    <button className="flex-1 px-4 py-2 text-sm font-medium text-gray-500 dark:text-gray-400 hover:text-gray-700 dark:hover:text-gray-300">
                        Body
                    </button>
                </div>
            </div>

            {/* Summary Widgets */}
            <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-4 gap-6 mb-6">
                <div className="bg-white dark:bg-gray-800 rounded-lg p-6 border border-gray-200 dark:border-gray-700">
                    <div className="flex items-center justify-between">
                        <div>
                            <p className="text-sm font-medium text-gray-600 dark:text-gray-400">Workouts This Week</p>
                            <p className="text-2xl font-bold text-gray-900 dark:text-white">0</p>
                            <p className="text-sm text-gray-500 dark:text-gray-400">Target: 4 workouts</p>
                        </div>
                        <div className="w-10 h-10 bg-purple-100 dark:bg-purple-900/30 rounded-lg flex items-center justify-center">
                            <svg className="w-6 h-6 text-purple-600 dark:text-purple-400" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                                <path strokeLinecap="round" strokeLinejoin="round" strokeWidth={2} d="M9 6v6m0 0v6m0-6h6m-6 0H9" />
                            </svg>
                        </div>
                    </div>
                </div>

                <div className="bg-white dark:bg-gray-800 rounded-lg p-6 border border-gray-200 dark:border-gray-700">
                    <div className="flex items-center justify-between">
                        <div>
                            <p className="text-sm font-medium text-gray-600 dark:text-gray-400">Calories Burned</p>
                            <p className="text-2xl font-bold text-gray-900 dark:text-white">0</p>
                            <p className="text-sm text-gray-500 dark:text-gray-400">Target: 2,500 kcal</p>
                        </div>
                        <div className="w-10 h-10 bg-orange-100 dark:bg-orange-900/30 rounded-lg flex items-center justify-center">
                            <svg className="w-6 h-6 text-orange-600 dark:text-orange-400" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                                <path strokeLinecap="round" strokeLinejoin="round" strokeWidth={2} d="M17.657 18.657A8 8 0 016.343 7.343S7 9 9 10c0-2 .5-5 2.986-7C14 5 16.09 5.777 17.656 7.343A7.975 7.975 0 0120 13a7.975 7.975 0 01-2.343 5.657z" />
                            </svg>
                        </div>
                    </div>
                </div>

                <div className="bg-white dark:bg-gray-800 rounded-lg p-6 border border-gray-200 dark:border-gray-700">
                    <div className="flex items-center justify-between">
                        <div>
                            <p className="text-sm font-medium text-gray-600 dark:text-gray-400">Active Minutes</p>
                            <p className="text-2xl font-bold text-gray-900 dark:text-white">0</p>
                            <p className="text-sm text-gray-500 dark:text-gray-400">Target: 150 minutes</p>
                        </div>
                        <div className="w-10 h-10 bg-blue-100 dark:bg-blue-900/30 rounded-lg flex items-center justify-center">
                            <svg className="w-6 h-6 text-blue-600 dark:text-blue-400" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                                <path strokeLinecap="round" strokeLinejoin="round" strokeWidth={2} d="M12 8v4l3 3m6-3a9 9 0 11-18 0 9 9 0 0118 0z" />
                            </svg>
                        </div>
                    </div>
                </div>

                <div className="bg-white dark:bg-gray-800 rounded-lg p-6 border border-gray-200 dark:border-gray-700">
                    <div className="flex items-center justify-between">
                        <div>
                            <p className="text-sm font-medium text-gray-600 dark:text-gray-400">Volume Lifted</p>
                            <p className="text-2xl font-bold text-gray-900 dark:text-white">0</p>
                            <p className="text-sm text-green-600 dark:text-green-400">+12% from last week</p>
                        </div>
                        <div className="w-10 h-10 bg-green-100 dark:bg-green-900/30 rounded-lg flex items-center justify-center">
                            <svg className="w-6 h-6 text-green-600 dark:text-green-400" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                                <path strokeLinecap="round" strokeLinejoin="round" strokeWidth={2} d="M3 6l3 1m0 0l-3 9a5.002 5.002 0 006.001 0M6 7l3 9M6 7l6-2m6 2l3-1m-3 1l-3 9a5.002 5.002 0 006.001 0M18 7l3 9m-3-9l-6-2m0-2v2m0 16V5m0 16H9m3 0h3" />
                            </svg>
                        </div>
                    </div>
                </div>
            </div>

            {/* Activity Sections */}
            <div className="grid grid-cols-1 lg:grid-cols-2 gap-6 mb-6">
                <div className="bg-white dark:bg-gray-800 rounded-lg p-6 border border-gray-200 dark:border-gray-700">
                    <h3 className="text-lg font-semibold text-gray-900 dark:text-white mb-2">Workout Activity</h3>
                    <p className="text-sm text-gray-600 dark:text-gray-400 mb-4">Your workout frequency and duration over time</p>
                    <div className="flex flex-col items-center justify-center py-8">
                        <div className="w-16 h-16 bg-gray-200 dark:bg-gray-700 rounded-full flex items-center justify-center mb-4">
                            <svg className="w-8 h-8 text-gray-400 dark:text-gray-500" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                                <path strokeLinecap="round" strokeLinejoin="round" strokeWidth={2} d="M9 19v-6a2 2 0 00-2-2H5a2 2 0 00-2 2v6a2 2 0 002 2h2a2 2 0 002-2zm0 0V9a2 2 0 012-2h2a2 2 0 012 2v10m-6 0a2 2 0 002 2h2a2 2 0 002-2m0 0V5a2 2 0 012-2h2a2 2 0 012 2v14a2 2 0 01-2 2h-2a2 2 0 01-2-2z" />
                            </svg>
                        </div>
                        <p className="text-gray-500 dark:text-gray-400 text-center mb-4">No workout data yet</p>
                        <p className="text-gray-400 dark:text-gray-500 text-center text-sm mb-4">Add your first workout to see your activity chart</p>
                        <button className="px-4 py-2 bg-purple-600 text-white rounded-lg hover:bg-purple-700 transition-colors">
                            + Add Workout
                        </button>
                    </div>
                </div>

                <div className="bg-white dark:bg-gray-800 rounded-lg p-6 border border-gray-200 dark:border-gray-700">
                    <h3 className="text-lg font-semibold text-gray-900 dark:text-white mb-2">Weight Trend</h3>
                    <p className="text-sm text-gray-600 dark:text-gray-400 mb-4">Track your weight progress over time</p>
                    <div className="flex flex-col items-center justify-center py-8">
                        <div className="w-16 h-16 bg-gray-200 dark:bg-gray-700 rounded-full flex items-center justify-center mb-4">
                            <svg className="w-8 h-8 text-gray-400 dark:text-gray-500" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                                <path strokeLinecap="round" strokeLinejoin="round" strokeWidth={2} d="M3 6l3 1m0 0l-3 9a5.002 5.002 0 006.001 0M6 7l3 9M6 7l6-2m6 2l3-1m-3 1l-3 9a5.002 5.002 0 006.001 0M18 7l3 9m-3-9l-6-2m0-2v2m0 16V5m0 16H9m3 0h3" />
                            </svg>
                        </div>
                        <p className="text-gray-500 dark:text-gray-400 text-center mb-4">No weight data yet</p>
                        <p className="text-gray-400 dark:text-gray-500 text-center text-sm mb-4">Log your weight to track your progress</p>
                        <button className="px-4 py-2 bg-white dark:bg-gray-700 text-gray-700 dark:text-gray-300 border border-gray-300 dark:border-gray-600 rounded-lg hover:bg-gray-50 dark:hover:bg-gray-600 transition-colors">
                            + Log Weight
                        </button>
                    </div>
                </div>
            </div>

            {/* Goals and Recent Activity */}
            <div className="grid grid-cols-1 lg:grid-cols-2 gap-6">
                <div className="bg-white dark:bg-gray-800 rounded-lg p-6 border border-gray-200 dark:border-gray-700">
                    <h3 className="text-lg font-semibold text-gray-900 dark:text-white mb-2">Weekly Goals</h3>
                    <p className="text-sm text-gray-600 dark:text-gray-400 mb-4">Your progress toward weekly fitness goals</p>
                    <div className="flex space-x-4">
                        <div className="flex flex-col items-center">
                            <div className="w-16 h-16 rounded-full border-4 border-pink-200 dark:border-pink-800 flex items-center justify-center mb-2">
                                <span className="text-sm font-semibold text-pink-600 dark:text-pink-400">25%</span>
                            </div>
                            <span className="text-xs text-gray-500 dark:text-gray-400">Cardio</span>
                        </div>
                        <div className="flex flex-col items-center">
                            <div className="w-16 h-16 rounded-full border-4 border-purple-200 dark:border-purple-800 flex items-center justify-center mb-2">
                                <span className="text-sm font-semibold text-purple-600 dark:text-purple-400">50%</span>
                            </div>
                            <span className="text-xs text-gray-500 dark:text-gray-400">Strength</span>
                        </div>
                        <div className="flex flex-col items-center">
                            <div className="w-16 h-16 rounded-full border-4 border-green-200 dark:border-green-800 flex items-center justify-center mb-2">
                                <span className="text-sm font-semibold text-green-600 dark:text-green-400">80%</span>
                            </div>
                            <span className="text-xs text-gray-500 dark:text-gray-400">Flexibility</span>
                        </div>
                    </div>
                </div>

                <div className="bg-white dark:bg-gray-800 rounded-lg p-6 border border-gray-200 dark:border-gray-700">
                    <div className="flex items-center justify-between mb-4">
                        <h3 className="text-lg font-semibold text-gray-900 dark:text-white">Recent Workouts</h3>
                        <button className="text-sm text-purple-600 dark:text-purple-400 hover:text-purple-700 dark:hover:text-purple-300">
                            View All
                        </button>
                    </div>
                    <p className="text-sm text-gray-600 dark:text-gray-400 mb-4">Your latest training sessions</p>
                    <div className="flex flex-col items-center justify-center py-8">
                        <div className="w-16 h-16 bg-gray-200 dark:bg-gray-700 rounded-full flex items-center justify-center mb-4">
                            <svg className="w-8 h-8 text-gray-400 dark:text-gray-500" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                                <path strokeLinecap="round" strokeLinejoin="round" strokeWidth={2} d="M9 6v6m0 0v6m0-6h6m-6 0H9" />
                            </svg>
                        </div>
                        <p className="text-gray-500 dark:text-gray-400 text-center">No recent workouts</p>
                    </div>
                </div>
            </div>
        </div>
    );
};

export default Dashboard;