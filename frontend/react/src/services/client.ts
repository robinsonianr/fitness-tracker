import axios from "axios";

const getAuthConfig = () => ({
    headers: {
        Authorization: `Bearer ${localStorage.getItem("access_token")}`
    }
});

export const getCustomers = async () => {
    try {
        return await axios.get(
            `http://localhost:8080/api/v1/customers`,
            getAuthConfig(),
        )
    } catch (e) {
        throw e
    }
};

export const login = async (formData: any) => {
    try {
        return await axios.post(
            `http://localhost:8080/api/v1/auth/login`,
            formData
        )
    } catch (e) {
        throw e
    }
}

export const updateCustomer = async (id: any, update: any) => {
    try {
        return await axios.put(
            `http://localhost:8080/api/v1/customers/${id}`,
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
            `http://localhost:8080/api/v1/customers`,
            formData
        ).then(res => {
            const token = res.headers["authorization"];
            localStorage.setItem("access_token", token);
        })
    } catch (e) {
        throw e;
    }
}
export const deleteCustomer = async (id: any) => {
    try {
        return await axios.delete(
            `http://localhost:8080/api/v1/customers/${id}`,
            getAuthConfig()
        )
    } catch (e) {
        throw e;
    }
}
