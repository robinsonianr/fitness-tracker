import React, {useEffect, useRef, useState} from "react";
import {Customer} from "../../types";
import {
    getCustomer,
    getCustomerProfileImage,
    updateCustomer,
    uploadCustomerProfileImage
} from "../../services/client.ts";
import axios from "axios";

export const Profile = () => {


    const [customer, setCustomer] = useState<Customer | undefined>(undefined);
    const [pfp, setPfp] = useState<string | undefined>(undefined);
    const defaultImg = "/assets/user.png";
    const fileInputRef = useRef<HTMLInputElement>(null);
    const id = localStorage.getItem("customerId")!;


    const profile = {
        name: customer?.name,
        email: customer?.email,
        memberSince: customer?.memberSince
    };

    const healthInfo = {
        age: customer?.age,
        gender: customer?.gender,
        weight: customer?.weight,
        height: customer?.height,
        weightGoal: customer?.weightGoal,
        activity: customer?.activity,
        bodyFat: customer?.bodyFat
    };


    const profileMember = profile.memberSince?.toString();
    const monthNames = [
        "January", "February", "March", "April", "May", "June", "July",
        "August", "September", "October", "November", "December"
    ];
    const month = new Date(profileMember!).getMonth();
    const memberDate = monthNames[month] + " " + new Date(profileMember!).getFullYear();

    const fetchPfp = async (id: any) => {
        try {
            const res = getCustomerProfileImage(id);
            const isImage = await checkImageUrl(res);
            if (isImage) {
                setPfp(res);
            }
        } catch (error) {
            console.error("Could not retrieve customer profile image: ", error);
        }
    };

    const checkImageUrl = async (url: string): Promise<boolean> => {
        try {
            const response = await axios.get(url);
            const contentType = response.headers["content-type"];
            return contentType && contentType.startsWith("image/");
        } catch (error) {
            console.error("Error checking image URL:", error);
            return false;
        }
    };

    useEffect(() => {
        const fetchData = async () => {
            try {
                const customerId = parseInt(id, 10);
                const response = await getCustomer(customerId);
                setCustomer(response.data);
            } catch (error) {
                console.error("Could not retrieve customer: ", error);
            }
        };

        fetchPfp(id);
        fetchData();
    }, []);


    const handleFileChange = async (event: React.ChangeEvent<HTMLInputElement>) => {
        const file = event.target.files?.[0];

        if (file) {
            await uploadPFP(file);
        }
    };

    const uploadPFP = async (file: File) => {
        const formData = new FormData();
        formData.append("file", file);

        try {
            await uploadCustomerProfileImage(id, formData);
        } catch (error) {
            console.error("File upload failed", error);
        }
    };

    const handleButtonClick = () => {
        if (fileInputRef.current) {
            fileInputRef.current.click();
        }
    };

    const handleSubmit = async (e: React.FormEvent<HTMLFormElement>) => {
        e.preventDefault();
        const formData = new FormData(e.currentTarget);
        formData.append("name", formData.get("firstName") + " " + formData.get("lastName"));
        const data = Object.fromEntries(formData.entries());

        if (customer?.id) {
            try {
                await updateCustomer(customer?.id, data);

                setTimeout(() => {
                    window.location.reload();
                }, 500);
            } catch (error) {
                console.error("Failed to update customer.", error);
            }
        }
    };


    return (
        <>
            <div className="grid grid-cols-1 lg:grid-cols-3 gap-6 p-6 min-h-[90%]">
                <div className=" bg-white dark:bg-[#333] rounded-lg border dark:border-gray-600 p-4 lg:col-span-1">
                    <div className="pb-6 mb-6">
                        <div className="flex items-center justify-between bold text-xl">
                            Profile Summary
                        </div>
                    </div>
                    <div className="flex flex-col items-center">
                        <div className="w-24 h-24 mb-4 relative">
                            <img className="rounded-[50%] object-cover w-20 h-20 z-1"
                                src={pfp != undefined ? pfp : defaultImg} alt="pfp"/>
                            <input
                                type="file"
                                ref={fileInputRef}
                                style={{display: "none"}}
                                accept="image/*"
                                onChange={handleFileChange}
                            />
                            <button
                                className="flex items-center justify-center absolute bottom-4 right-3 w-6 h-6 dark:bg-[#3f76c0] hover:bg-[#355a8f] border-none rounded-full cursor-pointer p-0"
                                onClick={handleButtonClick}>✎
                            </button>
                        </div>
                        <h2 className="text-2xl text-black dark:text-white mb-2 font-bold">{customer?.name}</h2>
                        <p className="text-xl text-black dark:text-gray-400 mb-2">Fitness
                            Experience: {healthInfo.activity}</p>
                        <div className="grid grid-cols-2 gap-4 w-full text-center p-6">
                            <div className="bg-[#222] rounded-md p-3 text-white">
                                <p className="text-lg font-bold text-black dark:text-white">Member Since</p>
                                <p className="text-black dark:text-gray-400">{memberDate}</p>
                            </div>
                            <div className="bg-[#222] rounded-md p-3 text-white">
                                <p className="text-2xl font-bold text-black dark:text-white">{customer?.workouts?.length}</p>
                                <p className="text-black dark:text-gray-400">Workouts</p>
                            </div>
                        </div>
                    </div>
                </div>
                <div className=" bg-white dark:bg-[#333] rounded-lg border dark:border-gray-600 p-4 lg:col-span-2">
                    <div className="pb-6 mb-6">
                        <div className="flex items-center justify-between bold text-xl">
                            Health Information
                        </div>
                    </div>
                    <div>
                        <form className="space-y-4" onSubmit={handleSubmit}>
                            <div className="grid grid-cols-1 md:grid-cols-2 gap-4">
                                <div className="space-y-2">
                                    <label htmlFor="firstName">First Name</label>
                                    <input name="firstName" type="text" defaultValue={customer?.name?.split(" ")[0]}
                                        className="border-2 border-gray-600 rounded-md p-2 w-full"/>
                                </div>
                                <div className="space-y-2">
                                    <label htmlFor="lastName">Last Name</label>
                                    <input name="lastName" type="text" defaultValue={customer?.name?.split(" ")[1]}
                                        className="border-2 border-gray-600 rounded-md p-2 w-full"/>
                                </div>
                                <div className="space-y-2">
                                    <label htmlFor="age">Age</label>
                                    <input name="age" type="text" defaultValue={healthInfo.age}
                                        className="border-2 border-gray-600 rounded-md p-2 w-full"/>
                                </div>
                                <div className="space-y-2">
                                    <label htmlFor="weight">Weight (lbs)</label>
                                    <input name="weight" type="text" defaultValue={healthInfo.weight}
                                        className="border-2 border-gray-600 rounded-md p-2 w-full"/>
                                </div>
                                <div className="space-y-2">
                                    <label htmlFor="height">Height (inches)</label>
                                    <input name="height" type="number" defaultValue={healthInfo.height}
                                        className="border-2 border-gray-600 rounded-md p-2 w-full"/>
                                </div>
                                <div className="space-y-2">
                                    <label htmlFor="bodyFat">Body Fat%</label>
                                    <input name="bodyFat" type="number" defaultValue={healthInfo.bodyFat}
                                        className="border-2 border-gray-600 rounded-md p-2 w-full"/>
                                </div>
                                <div className="space-y-2">
                                    <label htmlFor="gender">Gender</label>
                                    {customer && (
                                        <select name="gender" defaultValue={healthInfo.gender}
                                            className="border-2 border-gray-600 rounded-md p-2 w-full">
                                            <option value="">
                                                Select Gender
                                            </option>
                                            <option value="Male">Male</option>
                                            <option value="Female">Female</option>
                                            <option value="Forbidden">Forbidden</option>
                                        </select>
                                    )}
                                </div>
                                <div className="space-y-2">
                                    <label htmlFor="activity">Fitness Experience</label>
                                    {customer && (
                                        <select name="activity" defaultValue={healthInfo.activity}
                                            className="border-2 border-gray-600 rounded-md p-2 w-full">
                                            <option value="">
                                                Select Activity Experience
                                            </option>
                                            <option value="Advanced">Advanced</option>
                                            <option value="Intermediate">Intermediate</option>
                                            <option value="Beginner">Beginner</option>
                                            <option value="Sedentary">Sedentary</option>
                                        </select>
                                    )}
                                </div>
                            </div>
                            <div className="space-y-4 w-full">
                                <div className="space-y-2">
                                    <label htmlFor="email">Email</label>
                                    <input name="email" type="text" defaultValue={customer?.email}
                                        className="border-2 border-gray-600 rounded-md p-2 w-full"/>
                                </div>
                            </div>
                            <button
                                className="w-full h-12 bg-[#3f76c0] hover:bg-[#355a8f] duration-300 mt-2 rounded-md cursor-pointer">Save
                                Changes
                            </button>
                        </form>
                    </div>
                </div>
            </div>
        </>
    );
};

export default Profile;