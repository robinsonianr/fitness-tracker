import "./global.css";
import {createBrowserRouter, Navigate} from "react-router-dom";
import Dashboard from "./pages/dashboard/Dashboard";
import SignUp from "./components/features/signup/SignUp.tsx";
import Login from "./components/features/login/Login.tsx";
import ProtectedRoute from "./shared/ProtectedRoute.tsx";
import Profile from "./pages/profile/Profile.tsx";
import Logs from "./components/features/logs/Logs.tsx";


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

