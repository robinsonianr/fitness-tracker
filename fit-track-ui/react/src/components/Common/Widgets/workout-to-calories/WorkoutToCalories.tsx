import {useEffect, useRef, useState} from "react";
import {Workout} from "../../../../types/index.ts";
import * as echarts from "echarts";
import {getWeekOf, isDateInSelectedWeek, sortWorkoutsAsc} from "../../../../utils/utilities.ts";
import {getAllWorkoutsByCustomerId} from "../../../../services/client.ts";

const WorkoutToCalories = ({weekDate}: { weekDate: string }) => {
    const chartInstance = useRef<any>(null);
    const [weekOf, setWeekOf] = useState<string[]>([]);
    const [workoutData, setWorkoutData] = useState<Workout[]>([]);
    const chartRef = useRef<HTMLDivElement>(null);
    const hasAddedHeader = useRef(false);
    const [data, setData] = useState<number[][]>([]);

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
        const newData: number[][] = [];
        if (workoutData) {
            const workouts = sortWorkoutsAsc(workoutData);
            for (let i = 0; i < workouts.length; i++) {
                const date = new Date(workouts[i].workoutDate.toString());
                if (isDateInSelectedWeek(date, new Date(weekDate))) {
                    newData.push([workouts[i].volume!, workouts[i].calories!]);
                }
            }

            const date = new Date(weekDate);
            const week: string[] = [];
            getWeekOf(date, week);
            setWeekOf(week);
        }

        setData(newData);
    }, [workoutData, weekDate]);


    useEffect(() => {
        if (data.length === 0 && !hasAddedHeader.current) {
            const noDataDom = document.getElementById("volume-to-cal-graph");
            const noDataText = document.createElement("h2");
            noDataText.textContent = "No Data Available";
            if (noDataDom) {
                noDataDom.appendChild(noDataText);
                hasAddedHeader.current = true;
            }
        }
    }, [weekOf]);

    useEffect(() => {

        if (data.length !== 0 && chartRef.current) {
            if (!chartInstance.current) {
                chartInstance.current = echarts.init(chartRef.current);
                hasAddedHeader.current = false;
            }

            chartInstance.current.setOption({
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
        }


        return () => {
            if (chartInstance.current) {
                chartInstance.current.dispose();
                chartInstance.current = null;
            }

        };
    }, [data]);

    return (
        <div ref={chartRef} id="volume-to-cal-graph" className="visual-widget"
            style={{width: "700px", height: "300px"}}/>
    );
};

export default WorkoutToCalories;