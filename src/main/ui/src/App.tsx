import React from 'react';
import './App.css';
import {Route, Routes, Navigate} from "react-router-dom";
import Dashboard from "./pages/dashboard/Dashboard";
import SignUp from "./pages/signup/SignUp";
import {Login} from "./pages/login/Login";
import AuthService from "./provider/AuthService";

function App() {
    const token = AuthService.getCurrentToken();
    let isAuthenticated = false;

    if (token) {
        isAuthenticated = true;
    }

    return (
        <div className="App">
            <Routes>
                <Route path="/" element={isAuthenticated ? <Dashboard /> : <Navigate to="/login" />} />
                <Route path="/signup" element={<SignUp />} />
                <Route path="/login" element={<Login />} />
            </Routes>
        </div>
    );
}

export default App;
