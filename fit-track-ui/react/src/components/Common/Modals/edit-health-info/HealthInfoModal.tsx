import {Customer} from "../../../../types/index.ts";
import {useEffect, useState} from "react";
import {updateCustomer} from "../../../../services/client.ts";
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
                        <label className="health-modal-label">Age</label>
                        <input type="number" name="age" value={formData.age} onChange={handleChange}/>
                    </div>
                    <div className="modal-column">
                        <label className="health-modal-label">Gender</label>
                        <select className="health-select" name="gender" value={formData.gender} onChange={handleChange}>
                            <option value="">
                                Select Gender
                            </option>
                            <option value="Male">Male</option>
                            <option value="Female">Female</option>
                            <option value="Forbidden">Forbidden</option>
                        </select>
                    </div>
                    <div className="modal-column">
                        <label className="health-modal-label">Weight (lbs)</label>
                        <input type="number" name="weight" value={formData.weight} onChange={handleChange}/>
                    </div>
                    <div className="modal-column">
                        <label className="health-modal-label">Height (inches)</label>
                        <input type="number" name="height" value={formData.height} onChange={handleChange}/>
                    </div>
                    <div className="modal-column">
                        <label className="health-modal-label">Weight Goal (lbs)</label>
                        <input type="number" name="weightGoal" value={formData.weightGoal} onChange={handleChange}/>
                    </div>
                    <div className="modal-column">
                        <label className="health-modal-label">Workout Activity</label>
                        <select className="health-select" name="activity" value={formData.activity} onChange={handleChange}>
                            <option value="">
                                Select Activity Level
                            </option>
                            <option value="Advanced">Advanced</option>
                            <option value="Intermediate">Intermediate</option>
                            <option value="Beginner">Beginner</option>
                            <option value="Sedentary">Sedentary</option>
                        </select>
                    </div>
                    <div className="modal-column">
                        <label className="health-modal-label">Body Fat Percentage</label>
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