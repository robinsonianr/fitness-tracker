import {Customer} from "../../../typing";
import React, {useEffect, useState} from "react";
import {updateCustomer} from "../../../services/client.ts";
import "./health-info-modal.scss";
import ReactDOM from "react-dom";

export const HealthInfoModal = ({isOpen, onClose, customer}: {isOpen: boolean, onClose: any, customer: Customer | undefined}) => {
    const [formData, setFormData] = useState({
        name: "",
        email: "",
        age: "",
        gender: "",
        weight: "",
        height: "",
        weightGoal: "",
        activity: "",
        bodyFat: ""
    });

    // Use useEffect to update formData when customer data is available
    useEffect(() => {
        if (customer) {
            setFormData({
                name: customer.name || "",
                email: customer.email || "",
                age: customer.age?.toString() || "", // Converting numbers to strings for input fields
                gender: customer.gender || "",
                weight: customer.weight?.toString() || "",
                height: customer.height?.toString() || "",
                weightGoal: customer.weightGoal?.toString() || "",
                activity: customer.activity || "",
                bodyFat: customer.bodyFat?.toString() || ""
            });
        }
    }, [customer]);

    const handleChange = (e: { target: { name: any; value: any; }; }) => {
        // Update form data state as user types
        setFormData({ ...formData, [e.target.name]: e.target.value });
    };

    const handleSubmit = async (e: { preventDefault: () => void; }) => {
        e.preventDefault();

        if (customer?.id) {
            try {
                await updateCustomer(customer?.id, formData);
                onClose();

                setTimeout(() => {
                    window.location.reload();
                }, 500);
            } catch (error) {
                console.error("Failed to update customer.", error);
            }
        }
    };

    if (!isOpen) return null;

    return ReactDOM.createPortal(
        <div className={`health-modal ${isOpen ? "open" : ""}`}>
            <div className="health-modal-overlay" onClick={onClose}></div>
            <div className="health-modal-content">
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
        </div>,
        document.body
    );
};

export default HealthInfoModal;