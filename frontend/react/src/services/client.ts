import axios from "axios";


const axiosInstance = axios.create({
    baseURL: import.meta.env.VITE_API_BASE_URL,
    headers: {
        "Content-Type": "application/json",
    },
});
axiosInstance.interceptors.request.use(
    (config) => {
        const accessToken = localStorage.getItem("access_token");
        if (accessToken) {
            config.headers["Authorization"] = `Bearer ${accessToken}`;
        }
        return config;
    },
    (error) => {
        return Promise.reject(error);
    }
);

export const getCustomers = async () => {
    try {
        return await axiosInstance.get("/api/v1/customers");
    } catch (e) {
        throw e;
    }
};

export const getCustomer = async (id: number) => {
    try {
        return await axiosInstance.get(`/api/v1/customers/${id}`);
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
        return await axiosInstance.put(`/api/v1/customers/${id}`, update);
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
export const deleteCustomer = async (id: any) => {
    try {
        return await axiosInstance.delete(`/api/v1/customers/${id}`);
    } catch (e) {
        throw e;
    }
};

export const uploadCustomerProfileImage = async (id: any, formData: any) => {
    try {

        return axiosInstance.post(`/api/v1/customers/${id}/profile-image`, formData);
    } catch (e) {
        throw e;
    }

};

export const getCustomerProfileImage = (id: any) => `${import.meta.env.VITE_API_BASE_URL}/api/v1/customers/${id}/profile-image`;
