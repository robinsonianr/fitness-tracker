import React, {useEffect} from "react";
import {Customer} from "../../../typing";
import * as echarts from "echarts";
import {isDateInThisWeek, sortWorkouts} from "../../../utils/utilities.ts";

const VolumeWidget = ({customer}: { customer: Customer }) => {
    const volumeData: number[] = [];
    const weekOf: string[] = [];

    if (customer?.workouts) {
        const workouts = sortWorkouts(customer.workouts);
        for (let i = 0; i < workouts.length; i++) {
            const date = new Date(workouts[i].workoutDate.toString());
            if (isDateInThisWeek(date)) {
                volumeData[date.getDay()] = (workouts[i].volume!);
            } else {
                break;
            }
        }
        // Gets the current week to display in chart
        const date =  new Date();
        isDateInThisWeek(date, weekOf);
    }

    useEffect(() => {
        if (volumeData.length !== 0) {
            const volumeChart = echarts.init(document.getElementById("volume-graph"));

            volumeChart.setOption({
                color: "whites",
                title: {
                    text: "Volume Lifted",
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
                    name: "Volume (lbs)",
                    type: "value",
                    nameTextStyle: {
                        color: "white"
                    },
                    axisLabel: {
                        formatter: function (value: number) {
                            if (value >= 1000) {
                                return (value / 1000) + "k"; // Divide by 1000 and append 'k'
                            } else {
                                return value;
                            }
                        },
                        color: "white"
                    }
                },
                series: [
                    {
                        type: "bar",
                        color: "#3f76c0",
                        data: volumeData,
                    }
                ]
            });

            return () => {
                volumeChart.dispose();
            };
        }
    }, [customer]);


    return (
        <div>
            {volumeData.length === 0 ? (
                <div className="visual-widget" style={{width: "475px", height: "300px"}}>
                    <h2>No Volume Data Available</h2>
                </div>
            ) : (
                <div id="volume-graph" className="visual-widget" style={{width: "475px", height: "300px"}}/>
            )}
        </div>
    );
};

export default VolumeWidget;