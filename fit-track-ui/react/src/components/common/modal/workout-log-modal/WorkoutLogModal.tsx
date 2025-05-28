import "./workout-log-modal.scss";
import {Workout} from "../../../../types";
import ReactDOM from "react-dom";


export const WorkoutLogModal = ({isOpen, onClose, workout}: { isOpen: boolean, onClose: any, workout: Workout }) => {
    let date;
    if (workout) {
        date = new Date(workout.workoutDate);
    }

    if (!isOpen) return null;

    return ReactDOM.createPortal(
        <div className={`workout-log-modal ${isOpen ? "open" : ""}`}>
            <div className="modal-overlay" onClick={onClose}></div>
            <div className="workout-log-modal-content">
                <span className="close" onClick={onClose}>&times;</span>
                <h2>Workout: {date?.toDateString()}</h2>
                <div className="workout-log-data">
                    <p><b>Workout Type:</b> {workout.workoutType}</p>
                    <p><b>Exercises:</b> {workout.exercises}</p>
                    <p><b>Calories:</b> {workout.calories} kcal</p>
                    <p><b>Duration:</b> {Math.floor(workout.durationMinutes! / 60)}hr(s)
                        &nbsp;{workout.durationMinutes! % 60} minutes</p>
                    <p><b>Volume:</b> {workout.volume!.toLocaleString()} lbs</p>
                </div>
            </div>
        </div>,
        document.body
    );
};

export default WorkoutLogModal;