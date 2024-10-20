import React, {useEffect} from "react";
import {Customer} from "../../../typing";
import * as echarts from "echarts";
import {isDateInThisWeek, sortWorkouts} from "../../../utils/utilities.ts";

const DurationWidget = ({customer}: {customer: Customer}) => {
    const durationData: number[] = [];
    const weekOf: string[] = [];

    if (customer?.workouts) {
        const workouts = sortWorkouts(customer.workouts);
        for (let i = 0; i < workouts.length; i++) {
            const date = new Date(workouts[i].workoutDate.toString());
            if (isDateInThisWeek(date)) {
                durationData[date.getDay()] = (workouts[i].durationMinutes!);
            } else {
                break;
            }
        }
        // Gets the current week to display in chart
        const date =  new Date();
        isDateInThisWeek(date, weekOf);
    }

    useEffect(() => {
        if (durationData.length !== 0) {
            const durationChart = echarts.init(document.getElementById("duration-graph"));

            durationChart.setOption({
                color: "whites",
                title: {
                    text: "Workout Durations",
                    left: "center",
                    textStyle: {
                        color: "white",
                    }
                },
                tooltip: {},
                xAxis: {
                    name: "Week of (" + weekOf[0] + " - " + weekOf[1] + ")",
                    nameLocation: "middle",
                    nameTextStyle: {
                        color: "white"
                    },
                    nameGap: 40, // Distance between the title and the axis
                    axisLabel: {
                        color: "white"
                    },
                    data: ["Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat"],

                },
                yAxis: {
                    name: "Duration (min)",
                    nameGap: 15,
                    type: "value",
                    nameTextStyle: {
                        color: "white",
                        padding: [0, 0, 0, 20]
                    },
                    axisLabel: {
                        right: 10,
                        color: "white"
                    }
                },
                series: [
                    {
                        type: "bar",
                        color: "#3f76c0",
                        data: durationData,
                        connectNulls: true,
                        areaStyle: {}
                    }
                ]
            });

            return () => {
                durationChart.dispose();
            };
        }
    }, [customer]);

    return (
        <div>
            {durationData.length === 0 ? (
                <div className="visual-widget" style={{width: "340px", height: "300px"}}>
                    <h2>No Duration Data Available</h2>
                </div>
            ) : (
                <div id="duration-graph" className="visual-widget" style={{width: "340px", height: "300px"}}/>
            )}
        </div>
    );
};

export default DurationWidget;