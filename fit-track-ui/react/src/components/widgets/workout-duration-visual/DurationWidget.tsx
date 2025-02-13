import {useEffect, useRef, useState} from "react";
import {Workout} from "../../../typing";
import * as echarts from "echarts";
import {getWeekOf, isDateInSelectedWeek, sortWorkoutsAsc} from "../../../utils/utilities.ts";
import {getAllWorkoutsByCustomerId} from "../../../services/client.ts";

const DurationWidget = ({weekDate}: { weekDate: string }) => {
    const chartRef = useRef<HTMLDivElement>(null);
    const [durationData, setDurationData] = useState<number[]>([]);
    const [weekOf, setWeekOf] = useState<string[]>([]);
    const [workoutData, setWorkoutData] = useState<Workout[]>([]);
    const hasAddedHeader = useRef(false);
    const chartInstance = useRef<any>(null);

    useEffect(() => {
        const fetchWorkoutData = async () => {
            try {
                const id = localStorage.getItem("customerId");
                const testRes = await getAllWorkoutsByCustomerId(id);

                setWorkoutData(testRes.data);
            } catch (error) {
                console.error("Could not retrieve workouts: ", error);
            }
        };

        fetchWorkoutData();
    }, []);

    useEffect(() => {
        setDurationData([]);
        const newDurationData: number[] = [];
        if (workoutData) {
            const workouts = sortWorkoutsAsc(workoutData);
            for (let i = 0; i < workouts.length; i++) {
                const date = new Date(workouts[i].workoutDate.toString());
                if (isDateInSelectedWeek(date, new Date(weekDate))) {
                    newDurationData[date.getDay()] = (workouts[i].durationMinutes!);
                }
            }

            // Gets the current week to display in chart
            const date = new Date(weekDate);
            const week: string[] = [];
            getWeekOf(date, week);
            setWeekOf(week);
        }
        setDurationData(newDurationData);
    }, [workoutData, weekDate]);

    useEffect(() => {
        if (durationData.length === 0 && !hasAddedHeader.current) {
            const noDataDom = document.getElementById("duration-graph");
            const noDataText = document.createElement("h2");
            noDataText.textContent = "No Duration Data Available";
            if (noDataDom) {
                noDataDom.appendChild(noDataText);
                hasAddedHeader.current = true;
            }
        }
    }, [weekOf]);

    useEffect(() => {
        if (durationData.length !== 0 && chartRef.current) {

            if (!chartInstance.current) {
                chartInstance.current = echarts.init(chartRef.current);
                hasAddedHeader.current = false;
            }

            chartInstance.current.setOption({
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
                if (chartInstance.current) {
                    chartInstance.current.dispose();
                    chartInstance.current = null;
                }

            };
        }
    }, [durationData]);

    return (
        <div>
            <div ref={chartRef} id="duration-graph" className="visual-widget" 
                style={{width: "340px", height: "300px"}}/>
        </div>
    );
};

export default DurationWidget;