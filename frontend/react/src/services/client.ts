import axios from "axios";

const getAuthConfig = () => ({
    headers: {
        Authorization: `Bearer ${localStorage.getItem("access_token")}`
    }
});

export const getCustomers = async () => {
    try {
        return await axios.get(
            `${import.meta.env.VITE_API_BASE_URL}/api/v1/customers`,
            getAuthConfig(),
        )
    } catch (e) {
        throw e
    }
};

export const getCustomer = async (id: number) => {
    try {
        return await axios.get(
            `${import.meta.env.VITE_API_BASE_URL}/api/v1/customers/${id}`,
            getAuthConfig(),
        )
    } catch (e) {
        throw e
    }
};

export const login = async (formData: any) => {
    // ${import.meta.env.VITE_API_BASE_URL}

    try {
        return await axios.post(
            `${import.meta.env.VITE_API_BASE_URL}/api/v1/auth/login`,
            formData
        )
    } catch (e) {
        throw e
    }
}

export const updateCustomer = async (id: any, update: any) => {
    try {
        return await axios.put(
            `${import.meta.env.VITE_API_BASE_URL}/api/v1/customers/${id}`,
            update,
            getAuthConfig()
        )
    } catch (e) {
        throw e;
    }
}
export const createCustomer = async (formData: any) => {
    try {
        return await axios.post(
            `${import.meta.env.VITE_API_BASE_URL}/api/v1/customers`,
            formData
        )
    } catch (e) {
        throw e;
    }
}
export const deleteCustomer = async (id: any) => {
    try {
        return await axios.delete(
            `${import.meta.env.VITE_API_BASE_URL}/api/v1/customers/${id}`,
            getAuthConfig()
        )
    } catch (e) {
        throw e;
    }
}

export const uploadCustomerProfileImage = async (id: any, formData: any) => {
    try {

        return axios.post(
            `${import.meta.env.VITE_API_BASE_URL}/api/v1/customers/${id}/profile-image`,
            formData,
            {
                ...getAuthConfig(),
                // @ts-ignore
                'Content-Type' : 'multipart/form-data'
            }
        );
    } catch (e) {
        throw e
    }

}

export const getCustomerProfileImage = (id: any) => `${import.meta.env.VITE_API_BASE_URL}/api/v1/customers/${id}/profile-image`;
