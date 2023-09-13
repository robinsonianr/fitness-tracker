import './App.css';
import {createBrowserRouter} from "react-router-dom";
import Dashboard from "./components/dashboard/Dashboard";
import SignUp from "./components/signup/SignUp";
import Login from "./components/login/Login";
import ProtectedRoute from "./components/shared/ProtectedRoute";


export const router = createBrowserRouter([
    {
        path: "/login",
        element: <Login/>
    },
    {
        path: "/signup",
        element: <SignUp/>
    },
    {
        path: "/",
        element: <ProtectedRoute><Dashboard/></ProtectedRoute>
    },
])

