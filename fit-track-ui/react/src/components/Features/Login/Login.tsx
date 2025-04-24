import  {useEffect, useState} from "react";
import "./login.scss";
import {useNavigate} from "react-router-dom";
import {useAuth} from "../../../context/AuthContext";

export const Login = () => {
    const navigate = useNavigate();
    const {login} = useAuth();
    const {customer} = useAuth();

    const [formData, setFormData] = useState({
        email: "",
        password: "",
    });

    const [loginError, setLoginError] = useState<string | null>(null);
    const handleChange = (event: { target: { name: any; value: any; }; }) => {
        const {name, value} = event.target;
        setFormData((prevFormData) => ({...prevFormData, [name]: value}));
    };

    const handleSubmit = async (e: { preventDefault: () => void; }) => {
        e.preventDefault();
        setLoginError(null);

        try {
            await login(formData);
            navigate("/");
        } catch (error) {
            console.error(error);
            setLoginError("Incorrect email or password.");
        }

    };

    useEffect(() => {
        if (customer) {
            navigate("/");
        }
    }, [customer, navigate]);

    return (

        <div className="login-form">
            <div className="login-logo">
                <img src="/assets/weight.png" alt="Gym Icon " className="gym-icon"/>
                Fit Track
            </div>
            <div className="login-form-container">
                <h2>Login</h2>
                <form onSubmit={handleSubmit}>
                    {/* Form fields go here */}
                    <div className="form-group">
                        <label htmlFor="email">Email/Username</label>
                        <input type="email" id="email" name="email" value={formData.email}
                            onChange={handleChange}/>
                        <label htmlFor="password">Password</label>
                        <input type="password" id="password" name="password" value={formData.password}
                            onChange={handleChange}/>
                    </div>
                    {/* Other form fields */}
                    <button type="submit">Login</button>
                </form>
                {loginError && (
                    <div className="error-message">{loginError}</div>
                )}
                <a href="/signup">Need an account? Register here</a>
            </div>
        </div>
    );
};

export default Login;