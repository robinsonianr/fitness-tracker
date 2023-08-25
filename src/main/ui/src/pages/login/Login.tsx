import React, {useState} from "react";
import "./login.scss"
import {useNavigate} from "react-router-dom";


export const Login = () => {
    const navigate = useNavigate();

    const [formData, setFormData] = useState({
        "username": "",
        "password": "",
    });

    const handleChange = (event: { target: { name: any; value: any; }; }) => {
        const {name, value} = event.target;
        setFormData((prevFormData) => ({...prevFormData, [name]: value}))
    };

    const handleSubmit = async (e: { preventDefault: () => void; }) => {
        e.preventDefault();
        try {
            const response = await fetch("/api/v1/auth/login", {
                method: "POST",
                headers: {"Content-Type": "application/json"},
                body: JSON.stringify(formData),
            });

            const result = await response;
            console.log("Success", result);

            // After successful login redirect to dashboard
            navigate('/')
        } catch (error) {
            console.log("Error", error)
        }
    };

    return (

        <div className="login-form">
            <div className="login-form-container">
                <h2>Login</h2>
                <form onSubmit={handleSubmit}>
                    {/* Form fields go here */}
                    <div className="form-group">
                        <label htmlFor="username">Email/Username</label>
                        <input type="text" id="username" name="username" value={formData.username} onChange={handleChange} />
                        <label htmlFor="password">Password</label>
                        <input type="password" id="password" name="password" value={formData.password} onChange={handleChange}/>
                    </div>
                    {/* Other form fields */}
                    <button type="submit">Login</button>
                </form>
                <a href="/signup">Need an account? Register here</a>
            </div>
        </div>
    );
};