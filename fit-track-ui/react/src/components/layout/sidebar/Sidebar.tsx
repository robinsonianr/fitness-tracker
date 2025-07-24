import { useState, forwardRef } from "react";
import { useAuth } from "../../../context/AuthContext.tsx";
import { useNavigate, useLocation } from "react-router-dom";
import WorkoutModal from "../../common/modal/workout-modal/WorkoutModal.tsx";
import { Customer } from "../../../types/index.ts";
import { cn } from "../../../utils/cn";
import "./sidebar.css";

interface SidebarProps {
    customer: Customer | undefined;
    collapsed?: boolean;
    onClose?: () => void;
}

export const Sidebar = forwardRef<HTMLDivElement, SidebarProps>(({ customer, collapsed = false, onClose }, ref) => {
    const { logOut } = useAuth();
    const navigate = useNavigate();
    const location = useLocation();

    const [isModalOpen, setIsModalOpen] = useState(false);

    const openModal = () => {
        setIsModalOpen(true);
    };

    const closeModal = () => {
        setIsModalOpen(false);
    };

    const goDash = () => {
        navigate("/dashboard");
        onClose?.();
    };

    const goProfile = () => {
        navigate("/profile");
        onClose?.();
    };

    const goLogs = () => {
        navigate("/logs");
        onClose?.();
    };

    return (
        <div ref={ref} className="sidebar">
            <WorkoutModal isOpen={isModalOpen} onClose={closeModal} customer={customer} />
            
            {/* Header with close button for mobile */}
            <div className="sidebar-header">
                <div className="brand-group" onClick={goDash}>
                    <img src="/assets/weight.png" alt="Gym Icon" className="brand-icon"/>
                    <span className="brand-title">FitTrack</span>
                </div>
                
                {/* Close button for mobile */}
                <button 
                    onClick={onClose}
                    className="close-button"
                    aria-label="Close sidebar"
                >
                    <svg width="20" height="20" viewBox="0 0 24 24" fill="none" stroke="currentColor" strokeWidth="2">
                        <line x1="18" y1="6" x2="6" y2="18"></line>
                        <line x1="6" y1="6" x2="18" y2="18"></line>
                    </svg>
                </button>
            </div>
            
            {/* Navigation */}
            <div className="sidebar-nav">
                <ul className="nav-list">
                    <li>
                        <button 
                            onClick={goDash}
                            className={cn(
                                "nav-item",
                                location.pathname === "/dashboard" ? "nav-item-active" : "nav-item-inactive"
                            )}
                        >
                            <img src="/assets/dashboard.png" alt="dash" className="nav-icon"/>
                            <span>Dashboard</span>
                        </button>
                    </li>
                    <li>
                        <button 
                            onClick={goLogs}
                            className={cn(
                                "nav-item",
                                location.pathname === "/logs" ? "nav-item-active" : "nav-item-inactive"
                            )}
                        >
                            <img src="/assets/calendar.png" alt="logs" className="nav-icon"/>
                            <span>Workout Log</span>
                        </button>
                    </li>
                    <li>
                        <button 
                            onClick={goProfile}
                            className={cn(
                                "nav-item",
                                location.pathname === "/profile" ? "nav-item-active" : "nav-item-inactive"
                            )}
                        >
                            <img src="/assets/profile.png" alt="user" className="nav-icon"/>
                            <span>Profile</span>
                        </button>
                    </li>
                </ul>
            </div>
            
            {/* Add workout button */}
            <div className="sidebar-footer">
                <button onClick={openModal} className="add-workout-button">
                    + Add Workout
                </button>
            </div>
            
            {/* Logout */}
            <div className="sidebar-footer">
                <button onClick={logOut} className="logout-button">
                    Logout
                </button>
            </div>
        </div>
    );
});

Sidebar.displayName = "Sidebar";

export default Sidebar;

