import React, {useEffect} from "react";
import {Customer} from "../../../typing";
import * as echarts from "echarts";
import {isDateInThisWeek, sortWorkouts} from "../../../utils/utilities.ts";

const WorkoutToCalories = ({customer}: {customer: Customer}) => {

    useEffect(() => {
        const data: number[][] = [];
        const volToDurChart = echarts.init(document.getElementById("volume-to-cal-graph"));
        const weekOf: string[] = [];

        if (customer?.workouts) {
            const workouts = sortWorkouts(customer.workouts);
            for (let i = 0; i < workouts.length; i++) {
                const date = new Date(workouts[i].workoutDate.toString());
                if (isDateInThisWeek(date)) {
                    data.push([workouts[i].volume!, workouts[i].calories!]);
                } else {
                    break;
                }
            }
            // Gets the current week to display in chart
            const date =  new Date();
            isDateInThisWeek(date, weekOf);
        }

        volToDurChart.setOption({
            color: "white",
            title: {
                text: "Calories Burned vs. Volume Lifted",
                left: "center",
                textStyle: {
                    color: "white",
                }
            },
            tooltip: {
                trigger: "axis",
                axisPointer: {
                    type: "cross"
                }
            },
            xAxis: {
                name: "Volume (lbs)",
                nameLocation: "middle",
                nameTextStyle: {
                    color: "white"
                },
                nameGap: 40, // Distance between the title and the axis
                axisLabel: {
                    color: "white"
                },
            },
            yAxis: {
                name: "Calories (kcal)",
                type: "value",
                nameTextStyle: {
                    color: "white"
                },
                axisLabel: {
                    color: "white"
                }
            },
            series: [
                {
                    type: "scatter",
                    name: "kcal",
                    symbolSize: 10,
                    color: "#3f76c0",
                    data: data,
                }
            ]
        });

        return () => {
            volToDurChart.dispose();
        };
    }, [customer]);
    return (
        <div id="volume-to-cal-graph" className="visual-widget" style={{width: "700px", height: "300px"}}>
            Hello {customer?.name}
        </div>
    );
};

export default WorkoutToCalories;