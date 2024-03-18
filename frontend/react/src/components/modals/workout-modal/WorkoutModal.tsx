import React, {useState} from "react";
import "./workout-modal.scss";
import {addWorkout} from "../../../services/client.ts";
import {Customer} from "../../../typing";


export const WorkoutModal = ({isOpen, onClose, customer}: {isOpen: boolean, onClose: any, customer: Customer | undefined}) => {
    const [formData, setFormData] = useState({
        // Initialize form data state
        // Example:
        customer,
        workoutType: "",
        calories: "",
        durationMinutes: "",
        workoutDate: new Date().toISOString()
    });

    const handleChange = (e: { target: { name: any; value: any; }; }) => {
        // Update form data state as user types
        setFormData({ ...formData, [e.target.name]: e.target.value });
    };

    const handleSubmit = (e: { preventDefault: () => void; }) => {
        e.preventDefault();

        formData.customer = customer;
        addWorkout(formData)
            .then(onClose());
    };

    return (
        <div className={`modal ${isOpen ? "open" : ""}`}>
            <div className="modal-overlay" onClick={onClose}></div>
            <div className="modal-content">
                <span className="close" onClick={onClose}>&times;</span>
                <h2>Add Workout</h2>
                <form className="modal-inputs" onSubmit={handleSubmit}>
                    <div className="modal-column">
                        <label className="modal-label">Workout Type:</label>
                        <select id="workoutType" name="workoutType" onChange={handleChange}>
                            <optgroup>
                                <option value="N/A"></option>
                                <option value="Walking">Walking</option>
                                <option value="Running">Running</option>
                                <option value="Cycling">Cycling</option>
                                <option value="Strength Training">Strength Training</option>
                                <option value="Cross Fit">Cross Fit</option>
                            </optgroup>

                        </select>
                    </div>
                    <div className="modal-column">
                        <label className="modal-label">Calories:</label>
                        <input type="number" name="calories" value={formData.calories} onChange={handleChange}/>
                    </div>
                    <div className="modal-column">
                        <label className="modal-label">Workout Duration:</label>
                        <input type="number" name="durationMinutes" value={formData.durationMinutes} onChange={handleChange}/>
                    </div>
                    <div className="modal-column">
                        <button type="submit">Submit</button>
                    </div>
                </form>
            </div>
        </div>
    );
};

export default WorkoutModal;