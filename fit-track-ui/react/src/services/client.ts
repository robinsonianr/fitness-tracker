import axios from "axios";


const axiosInstance = axios.create({
    baseURL: import.meta.env.VITE_API_BASE_URL,
});
axiosInstance.interceptors.request.use(
    (config) => {
        const accessToken = localStorage.getItem("access_token");

        if (config.data && config.data instanceof FormData) {
            // If the request contains FormData, don't set the 'Content-Type', 
            // the browser will handle it automatically
            delete config.headers["Content-Type"];
        } else {
            // Default to 'application/json' if not FormData
            config.headers["Content-Type"] = "application/json";
        }

        if (accessToken) {
            config.headers["Authorization"] = `Bearer ${accessToken}`;
        }
        return config;
    },
    (error) => {
        return Promise.reject(error);
    }
);

export const getCustomer = async (id: any) => {
    try {
        return await axiosInstance.get(`/api/v1/customers/${id}`);
    } catch (e) {
        throw e;
    }
};

export const getAllWorkoutsByCustomerId = async (id: any) => {
    try {
        return await axiosInstance.get(`/api/v1/workouts/log/${id}`);
    } catch (e) {
        throw e;
    }
};

export const login = async (formData: any) => {
    try {
        return await axiosInstance.post("/api/v1/auth/login", formData);
    } catch (e) {
        throw e;
    }
};

export const updateCustomer = async (id: any, update: any) => {
    try {
        return await axiosInstance.put(`/api/v1/customers/update/${id}`, update);
    } catch (e) {
        throw e;
    }
};
export const createCustomer = async (formData: any) => {
    try {
        return await axiosInstance.post("/api/v1/customers", formData);
    } catch (e) {
        throw e;
    }
};

export const addWorkout = async (formData: any) => {
    try {
        return await axiosInstance.post("/api/v1/workouts", formData);
    } catch (e) {
        throw e;
    }
};

export const getCustomerWeightHistory= async (entityId: any) => {
    try {
        return await axiosInstance.get(`/api/v1/audit/${entityId}`);
    } catch (e) {
        throw e;
    }
};

export const uploadCustomerProfileImage = async (id: any, formData: any) => {
    try {

        return axiosInstance.put(`/api/v1/customers/${id}/profile-image`, formData);
    } catch (e) {
        throw e;
    }

};

export const getCustomerProfileImage = (id: any) => `${import.meta.env.VITE_API_BASE_URL}/api/v1/customers/${id}/profile-image`;