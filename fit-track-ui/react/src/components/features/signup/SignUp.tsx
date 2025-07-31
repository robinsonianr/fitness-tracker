import React from "react";
import {useNavigate} from "react-router-dom";
import {createCustomer} from "../../../services/client";

export const SignUp = () => {
    const navigate = useNavigate();

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

    const handleSubmit = async (e: React.FormEvent<HTMLFormElement>) => {
        e.preventDefault();

        const data = new FormData(e.currentTarget);
        const formData = Object.fromEntries(data.entries());

        await createCustomer(formData).then(() => {
            navigate("/login");
        });
    };


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
                <div className=" bg-white dark:bg-[#333] border border-gray-400 dark:border-gray-600 flex flex-col rounded-lg justify-center items-center w-150 h-150">
                    <h2 className="mb-5 text-3xl font-bold">Create Your Account</h2>
                    <form className="space-y-4 mb-2" onSubmit={handleSubmit}>
                        <div className="grid grid-cols-1 gap-3">
                            <div className="space-y-2">
                                <label htmlFor="name">Full Name</label>
                                <input name="name" type="text" className="border-2 border-gray-600 rounded-md p-2 w-full"/>
                            </div>
                        </div>
                        <div className="grid grid-cols-1 md:grid-cols-2 gap-3">
                            <div className="space-y-2">
                                <label htmlFor="email">Email</label>
                                <input name="email" type="email" className="border-2 border-gray-600 rounded-md p-2 w-full"/>
                            </div>
                            <div className="space-y-2">
                                <label htmlFor="password">Password</label>
                                <input name="password" type="password" minLength={6} className="border-2 border-gray-600 rounded-md p-2 w-full"/>
                            </div>
                        </div>
                        <div className="grid grid-cols-1 md:grid-cols-2 gap-4">
                            <div className="space-y-2">
                                <label htmlFor="age">Age</label>
                                <select className="border-2 border-gray-600 rounded-md p-2 w-full" name="age"
                                    aria-label="Select age"
                                    title="Select age"
                                    required>
                                    <option value="">Select age</option>
                                    {generateAgeOptions()}
                                </select>
                            </div>
                            <div className="space-y-2">
                                <label htmlFor="gender">Gender</label>
                                <select className="border-2 border-gray-600 rounded-md p-2 w-full" name="gender"
                                    title="Select gender"
                                    required>
                                    <option value="">Select gender</option>
                                    <option value="Male">Male</option>
                                    <option value="Female">Female</option>
                                    <option value="Forbidden">Forbidden</option>
                                </select>
                            </div>
                        </div>
                        <button type="submit" className="w-full h-12 bg-[#3f76c0] hover:bg-[#355a8f] duration-300 mt-2 rounded-md cursor-pointer">Sign Up</button>
                    </form>
                    <a href="/login">Already have an account? Login</a>
                </div>
            </div>
        </>
    );
};

export default SignUp;