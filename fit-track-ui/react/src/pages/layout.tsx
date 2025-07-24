import { Outlet } from "react-router-dom";
import { useMediaQuery } from "@uidotdev/usehooks";
import { useRef, useState, useEffect } from "react";
import { Customer } from "../types/index.ts";
import { getCustomer } from "../services/client";

import { Sidebar } from "../components/layout/sidebar/Sidebar";
import Header from "../components/layout/header/Header";

import { cn } from "../utils/cn";
import "./layout.css";

const Layout = () => {
    const isLargeScreen = useMediaQuery("(min-width: 1024px)");
    const [sidebarOpen, setSidebarOpen] = useState(false);
    const [customer, setCustomer] = useState<Customer | undefined>(undefined);

    const sidebarRef = useRef(null);

    // Fetch customer data for sidebar
    useEffect(() => {
        const fetchData = async () => {
            try {
                const id = localStorage.getItem("customerId");
                const response = await getCustomer(id);
                setCustomer(response.data);
            } catch (error) {
                console.error("Could not retrieve customer: ", error);
            }
        };

        fetchData();
    }, []);

    // Auto-close sidebar on mobile when clicking outside
    const handleOverlayClick = () => {
        if (!isLargeScreen) {
            setSidebarOpen(false);
        }
    };

    return (
        <div className="layout-container">
            {/* Mobile overlay */}
            {sidebarOpen && !isLargeScreen && (
                <div className="mobile-overlay" onClick={handleOverlayClick} />
            )}
            
            {/* Sidebar - hidden on mobile, visible on large screens */}
            <div className={cn(
                "sidebar-container",
                isLargeScreen ? "sidebar-visible" : sidebarOpen ? "sidebar-visible" : "sidebar-hidden"
            )}>
                <Sidebar 
                    ref={sidebarRef} 
                    collapsed={false} 
                    customer={customer}
                    onClose={() => setSidebarOpen(false)}
                />
            </div>
            
            {/* Main content area */}
            <div className={cn(
                "main-content",
                isLargeScreen ? "main-content-desktop" : "main-content-mobile"
            )}>
                <Header 
                    collapsed={false}
                    setCollapsed={() => setSidebarOpen(!sidebarOpen)}
                    customer={customer}
                    sidebarOpen={sidebarOpen}
                />
                <div className="content-area">
                    <Outlet context={{ customer }} />
                </div>
            </div>
        </div>
    );
};
 
export default Layout;