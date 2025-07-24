import "./global.css";
import { createBrowserRouter, RouterProvider } from "react-router-dom";
import { Navigate } from "react-router-dom";
import ProtectedRoute from "./shared/ProtectedRoute.tsx";
import Layout from "./pages/layout.tsx";
import Dashboard from "./pages/dashboard/Dashboard";
import Profile from "./pages/profile/Profile.tsx";
import Logs from "./components/features/logs/Logs.tsx";
import SignUp from "./components/features/signup/SignUp.tsx";
import Login from "./components/features/login/Login.tsx";

// Export the router so main.tsx can import it
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
        element: <ProtectedRoute><Layout /></ProtectedRoute>,
        children: [
            {
                index: true,
                element: <Navigate to="dashboard" replace />
            },
            {
                path: "dashboard",
                element: <Dashboard />
            },
            {
                path: "profile",
                element: <Profile />
            },
            {
                path: "logs",
                element: <Logs />
            }
        ]
    }
]);

function App() {
    return <RouterProvider router={router} />;
}

export default App;

