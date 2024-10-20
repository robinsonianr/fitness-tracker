import React, {useEffect, useRef, useState} from "react";
import {Customer} from "../../../typing";
import * as echarts from "echarts";
import {getWeekOf, isDateInSelectedWeek, sortWorkoutsAsc} from "../../../utils/utilities.ts";

const VolumeWidget = ({customer, weekDate}: { customer: Customer, weekDate: string }) => {
    const chartInstance = useRef<any>(null);
    const chartRef = useRef<HTMLDivElement>(null);
    const [volumeData, setVolumeData] = useState<number[]>([]);
    const [weekOf, setWeekOf] = useState<string[]>([]);
    const hasAddedHeader = useRef(false);

    useEffect(() => {
        setVolumeData([]);
        const newVolumeData: number[] = [];
        if (customer?.workouts) {
            const workouts = sortWorkoutsAsc(customer.workouts);
            for (let i = 0; i < workouts.length; i++) {
                const date = new Date(workouts[i].workoutDate.toString());
                if (isDateInSelectedWeek(date, new Date(weekDate))) {
                    newVolumeData[date.getDay()] = (workouts[i].volume!);
                }
            }

            const date = new Date(weekDate);
            const week: string[] = [];
            getWeekOf(date, week);
            setWeekOf(week);
        }
        setVolumeData(newVolumeData);
    }, [customer, weekDate]);

    useEffect(() => {
        if (volumeData.length === 0 && !hasAddedHeader.current) {
            const noDataDom = document.getElementById("volume-graph");
            const noDataText = document.createElement("h2");
            noDataText.textContent = "No Volume Data Available";
            if (noDataDom) {
                noDataDom.appendChild(noDataText);
                hasAddedHeader.current = true;
            }
        }
    }, [weekOf]);

    useEffect(() => {
        if (volumeData.length !== 0 && chartRef.current) {
            if (!chartInstance.current) {
                chartInstance.current = echarts.init(chartRef.current);
                hasAddedHeader.current = false;
            }

            chartInstance.current.setOption({
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
        }

        return () => {
            if (chartInstance.current) {
                chartInstance.current.dispose();
                chartInstance.current = null;
            }

        };
    }, [volumeData]);


    return (
        <div>
            <div ref={chartRef} id="volume-graph" className="visual-widget" style={{width: "475px", height: "300px"}}/>
        </div>
    );
};

export default VolumeWidget;