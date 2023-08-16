import React from "react";
import "./login.scss"


export const Login = () => {
    return (

        <div className="login-form">
            <h2>Login</h2>
            <form>
                {/* Form fields go here */}
                <div className="form-group">
                    <label htmlFor="email">Email</label>
                    <input type="text" id="email" name="email" />
                    <label htmlFor="password">Password</label>
                    <input type="password" id="password" name="password" />
                </div>
                {/* Other form fields */}
                <button type="submit">Login</button>
            </form>
            <a href="/signup">Need an account? Register here</a>
        </div>
    );
};