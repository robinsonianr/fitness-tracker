import "./global.css";
import {createBrowserRouter, Navigate} from "react-router-dom";
import Dashboard from "./pages/dashboard/Dashboard";
import SignUp from "./components/Features/Signup/SignUp.tsx";
import Login from "./components/Features/Login/Login.tsx";
import ProtectedRoute from "./shared/ProtectedRoute.tsx";
import Profile from "./pages/Profile/Profile.tsx";
import Logs from "./components/Features/Logs/Logs.tsx";


export const router = createBrowserRouter([
    {
        path: "/login",
        element: <Login />
    },
    {
        path: "/signup",
        element: <SignUp />
    },
    {
        path: "/",
        element: <Navigate to="/dashboard" />
    },
    {
        path: "/dashboard",
        element: <ProtectedRoute><Dashboard /></ProtectedRoute>
    },
    {
        path: "/:id/profile",
        element: <ProtectedRoute><Profile /></ProtectedRoute>
    },
    {
        path: "/logs",
        element: <ProtectedRoute><Logs /></ProtectedRoute>
    }
]);

