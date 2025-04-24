import {useState} from "react";
import "./sidebar.scss";
import {useAuth} from "../../../context/AuthContext.tsx";
import {useNavigate} from "react-router-dom";
import WorkoutModal from "../../Common/Modals/workout-modal/WorkoutModal.tsx";
import {Customer} from "../../../types/index.ts";

export const Sidebar = ({customer}: {customer: Customer | undefined}) => {
    const {logOut} = useAuth();
    const navigate = useNavigate();

    const [isModalOpen, setIsModalOpen] = useState(false);

    const openModal = () => {
        setIsModalOpen(true);
    };

    const closeModal = () => {
        setIsModalOpen(false);
    };

    const id = localStorage.getItem("customerId");

    const goDash = () => {
        navigate("/dashboard");
    };

    const goProfile = () => {
        navigate(`/${id}/profile`);
    };

    const goLogs = () => {
        navigate("/logs");
    };

    return(
        <div className="sidebar">
            <WorkoutModal isOpen={isModalOpen} onClose={closeModal} customer={customer} />
            <div className="sidebar-logo">
                <div  className="logo" onClick={goDash}>
                    <img src="/assets/weight.png" alt="Gym Icon " className="gym-icon"/>
                     Fit Track
                </div>
            </div>
            <div className="sidebar-content">
                <ul className="side-menu">
                    <li className="">
                        <a onClick={goDash}>
                            <img src="/assets/dashboard.png" alt="dash"/>
                            <span>Dashboard</span>
                        </a>
                    </li>
                    <li className="">
                        <a onClick={goLogs}>
                            <img src="/assets/calendar.png" alt="logs"/>
                            <span>Logs</span>
                        </a>
                    </li>
                    <li className="">
                        <a onClick={goProfile}>
                            <img src="/assets/profile.png" alt="user"/>
                            <span>Profile</span>
                        </a>
                    </li>
                </ul>
            </div>
            <div className="add-workout" onClick={openModal}>
                <a className="font-w500">Add New Workout</a>
            </div>
            <div className="logout-button-container">
                <button className="logout-button" onClick={logOut}>Logout</button>
            </div>
        </div>
    );
};

export default Sidebar;

