import {useEffect} from "react";
import {useNavigate} from "react-router-dom";
import {useAuth} from "../context/AuthContext";

const ProtectedRoute = ({children}: { children: any }) => {

    const {isCustomerAuthenticated} = useAuth();
    const navigate = useNavigate();

    useEffect(() => {
        if (!isCustomerAuthenticated()) {
            navigate("/login");
        }
    });

    return isCustomerAuthenticated() ? children : "";
};

export default ProtectedRoute;