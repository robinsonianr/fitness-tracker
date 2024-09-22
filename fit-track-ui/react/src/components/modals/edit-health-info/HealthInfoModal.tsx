import {Customer} from "../../../typing";
import React, {useEffect, useState} from "react";
import {updateCustomer} from "../../../services/client.ts";
import "./health-info-modal.scss";

export const HealthInfoModal = ({isOpen, onClose, customer}: {isOpen: boolean, onClose: any, customer: Customer | undefined}) => {
    const [formData, setFormData] = useState({
        name: undefined,
        email: undefined,
        age: undefined,
        gender: undefined,
        weight: undefined,
        height: undefined,
        weightGoal: undefined,
        activity: undefined,
        bodyFat: undefined
    });

    // Use useEffect to update formData when customer data is available
    useEffect(() => {
        if (customer) {
            setFormData({
                name: customer.name || undefined,
                email: customer.email || undefined,
                age: customer.age || undefined, // Converting numbers to strings for input fields
                gender: customer.gender || undefined,
                weight: customer.weight || undefined,
                height: customer.height || undefined,
                weightGoal: customer.weightGoal || undefined,
                activity: customer.activity || undefined,
                bodyFat: customer.bodyFat || undefined
            });
        }
    }, [customer]);

    const handleChange = (e: { target: { name: any; value: any; }; }) => {
        // Update form data state as user types
        setFormData({ ...formData, [e.target.name]: e.target.value });
    };

    const handleSubmit = (e: { preventDefault: () => void; }) => {
        e.preventDefault();

        updateCustomer(customer?.id, formData)
            .then(onClose());

        setTimeout(() => {
            window.location.reload();
        }, 500);
    };

    return (
        <div className={`modal ${isOpen ? "open" : ""}`}>
            <div className="modal-overlay" onClick={onClose}></div>
            <div className="modal-content">
                <span className="close" onClick={onClose}>&times;</span>
                <h2>Edit Health Information</h2>
                <form className="modal-inputs" onSubmit={handleSubmit}>
                    <div className="modal-column">
                        <label className="modal-label">Age: </label>
                        <input type="number" name="age" value={formData.age} onChange={handleChange}/>
                    </div>
                    <div className="modal-column">
                        <label className="modal-label">Gender:</label>
                        <input type="text" name="gender" value={formData.gender} onChange={handleChange}/>
                    </div>
                    <div className="modal-column">
                        <label className="modal-label">Weight:</label>
                        <input type="number" name="weight" value={formData.weight} onChange={handleChange}/>
                    </div>
                    <div className="modal-column">
                        <label className="modal-label">Height: </label>
                        <input type="number" name="height" value={formData.height} onChange={handleChange}/>
                    </div>
                    <div className="modal-column">
                        <label className="modal-label">Weight Goal:</label>
                        <input type="number" name="weightGoal" value={formData.weightGoal} onChange={handleChange}/>
                    </div>
                    <div className="modal-column">
                        <label className="modal-label">Activity:</label>
                        <input type="text" name="activity" value={formData.activity} onChange={handleChange}/>
                    </div>
                    <div className="modal-column">
                        <label className="modal-label">Body Fat Percentage:</label>
                        <input type="number" name="bodyFat" value={formData.bodyFat} onChange={handleChange}/>
                    </div>
                    <div className="modal-column">
                        <button type="submit">Submit</button>
                    </div>
                </form>
            </div>
        </div>
    );
};

export default HealthInfoModal;