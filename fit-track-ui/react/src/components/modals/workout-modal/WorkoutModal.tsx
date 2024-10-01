import React, {useState} from "react";
import "./workout-modal.scss";
import {addWorkout} from "../../../services/client.ts";
import {Customer} from "../../../typing";
import ReactDOM from "react-dom";


export const WorkoutModal = ({isOpen, onClose, customer}: {isOpen: boolean, onClose: any, customer: Customer | undefined}) => {
    const [formData, setFormData] = useState({
        // Initialize form data state
        // Example:
        customer: {},
        workoutType: "",
        calories: "",
        durationMinutes: "",
        exercises: "",
        volume: "",
        workoutDate: new Date().toISOString()
    });

    const handleChange = (e: { target: { name: any; value: any; }; }) => {
        // Update form data state as user types
        setFormData({ ...formData, [e.target.name]: e.target.value });
    };

    const handleSubmit = async (e: { preventDefault: () => void; }) => {
        e.preventDefault();
        if (customer?.id) {
            try {
                formData.customer = {id: customer?.id};
                await addWorkout(formData);
                onClose();

                setTimeout(() => {
                    window.location.reload();
                }, 500);
            } catch (error) {
                console.error("Could not add workout to customer", error);
            }
        }

    };


    if (!isOpen) return null;

    return ReactDOM.createPortal(
        <div className={`modal ${isOpen ? "open" : ""}`}>
            <div className="modal-overlay" onClick={onClose}></div>
            <div className="workout-modal-content">
                <span className="close" onClick={onClose}>&times;</span>
                <h2>Add Workout</h2>
                <form className="modal-inputs" onSubmit={handleSubmit}>
                    <div className="modal-column">
                        <label className="modal-label">Workout Type</label>
                        <select id="workoutType" name="workoutType" onChange={handleChange}>
                            <option value="">
                                Select Workout Type
                            </option>
                            <option value="Walking">Walking</option>
                            <option value="Running">Running</option>
                            <option value="Cycling">Cycling</option>
                            <option value="Strength Training">Strength Training</option>
                            <option value="Cross Fit">Cross Fit</option>

                        </select>
                    </div>
                    <div className="modal-column">
                        <label className="modal-label">Exercises</label>
                        <input type="number" name="exercises" value={formData.exercises} onChange={handleChange}/>
                    </div>
                    <div className="modal-column">
                        <label className="modal-label">Calories (kcal)</label>
                        <input type="number" name="calories" value={formData.calories} onChange={handleChange}/>
                    </div>
                    <div className="modal-column">
                        <label className="modal-label">Workout Duration (min)</label>
                        <input type="number" name="durationMinutes" value={formData.durationMinutes} 
                            onChange={handleChange}/>
                    </div>
                    <div className="modal-column">
                        <label className="modal-label">Volume (lbs)</label>
                        <input type="number" name="volume" value={formData.volume} onChange={handleChange}/>
                    </div>
                    <div className="modal-column">
                        <button type="submit">Submit</button>
                    </div>
                </form>
            </div>
        </div>,
        document.body
    );
};

export default WorkoutModal;