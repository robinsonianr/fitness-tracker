import React, {useEffect, useState} from "react";
import {useNavigate} from "react-router-dom";
import {useAuth} from "../../../context/AuthContext";

export const Login = () => {
    const navigate = useNavigate();
    const {login} = useAuth();
    const {customer} = useAuth();

    const [loginError, setLoginError] = useState<string | null>(null);


    const handleSubmit = async (e:React.FormEvent<HTMLFormElement>)=> {
        e.preventDefault();
        setLoginError(null);
        const data = new FormData(e.currentTarget);
        const formData = Object.fromEntries(data.entries());

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
        <>
            <div className="bg-[#222] justify-center items-center flex flex-col min-h-screen">
                <div className="mb-10 flex items-center">
                    <div className="w-25 h-25 mr-3">
                        <img src="/assets/weight.png" alt="Gym Icon "/>
                    </div>
                    <div className="text-6xl font-bold text-white dark:text-gray-100">
                        Fit Track
                    </div>
                </div>
                <div className=" bg-white dark:bg-[#333] border border-gray-400 dark:border-gray-600 flex flex-col rounded-lg justify-center items-center w-125 h-125 ">
                    <h2 className="mb-5 text-3xl font-bold">Login</h2>
                    <form className="space-y-4 mb-5" onSubmit={handleSubmit}>
                        <div className="space-y-2 col-span-2">
                            <label className="mb-4" htmlFor="email">Email</label>
                            <input name="email" type="email" className="border-2 border-gray-600 rounded-md p-2 w-full"/>
                        </div>
                        <div className="space-y-2">
                            <label htmlFor="password">Password</label>
                            <input name="password" type="password" className="border-2 border-gray-600 rounded-md p-2 w-full"/>
                        </div>
                        <button type="submit" className="w-full h-12 bg-[#3f76c0] hover:bg-[#355a8f] duration-300 mt-2 rounded-md cursor-pointer">Login</button>
                    </form>
                    {loginError && (
                        <div className="text-red-700 text-sm">{loginError}</div>
                    )}
                    <a href="/signup">Need an account? Register here</a>
                </div>
            </div>
        </>
    );
};

export default Login;