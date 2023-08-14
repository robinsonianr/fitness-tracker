import React from "react";
import "./signup.scss"


export const SignUp = () => {
    const generateAgeOptions = () => {
        const options = [];
        for (let age = 18; age <= 80; age++) {
            options.push(
                <option key={age} value={age}>
                    {age}
                </option>
            );
        }
        return options;
    };

    return (
        <div className="signup-form">
            <h2>Sign Up</h2>
            <form>
                {/* Form fields go here */}
                <div className="form-group">
                    <label htmlFor="name">Full Name</label>
                    <input type="text" id="name" name="name" />
                    <label htmlFor="email">Email</label>
                    <input type="text" id="email" name="email" />
                    <label htmlFor="username">Username</label>
                    <input type="text" id="username" name="username" />
                    <label htmlFor="password">Password</label>
                    <input type="password" id="password" name="password" />
                </div>
                <div className="form-group inline">
                    <div className="form-subgroup">
                        <label htmlFor="age">Age</label>
                        <select id="age" name="age">
                            <option value="">Select age</option>
                            {generateAgeOptions()}
                        </select>
                    </div>
                    <div className="form-subgroup">
                        <label htmlFor="gender">Gender</label>
                        <select id="gender" name="gender">
                            <option value="">Select gender</option>
                            <option value="male">Male</option>
                            <option value="female">Female</option>
                            <option value="other">Other</option>
                        </select>
                    </div>
                </div>
                {/* Other form fields */}
                <button type="submit">Sign Up</button>
            </form>
            <a href="/login">Already have an account? Login</a>
        </div>
    );
};

export default SignUp;