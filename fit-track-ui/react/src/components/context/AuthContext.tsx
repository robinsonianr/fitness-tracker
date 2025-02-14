import {createContext, useContext, useEffect, useState} from "react";
import {login as performLogin} from "../../services/client";
import jwtDecode from "jwt-decode";
import {Customer} from "../../typing";




type AuthContextType = {
    customer: Customer | undefined;
    login: (formData: any) => Promise<void>;
    logOut: () => void;
    isCustomerAuthenticated: () => boolean;
    setCustomerFromToken: () => void;
};


const AuthContext = createContext<AuthContextType | null>(null);

const AuthProvider = ({children}: { children: any }) => {
    const [customer, setCustomer] = useState<Customer | undefined>(undefined);

    const setCustomerFromToken = () => {
        let token: any = localStorage.getItem("access_token");
        if (token) {
            token = jwtDecode(token);
            const customer: Customer = {
                email: token.sub,
                roles: token.scopes
            };
            setCustomer(customer);
        }
    };
    useEffect(() => {
        if (localStorage.getItem("access_token") !== undefined) {
            setCustomerFromToken();
        }
    }, []);

    const login = async (formData: any): Promise<void> => {
        try {
            const response = await performLogin(formData);
            const jwtToken = response.headers["authorization"];
            if (jwtToken !== undefined) {
                localStorage.setItem("access_token", jwtToken);
            }

            const customerId = response.data.customerId;
            if (customerId !== undefined) {
                localStorage.setItem("customerId", customerId);
            }

            const decodedToken: any = jwtDecode(jwtToken);

            const customer: Customer = {
                id: customerId,
                username: decodedToken.sub,
                roles: decodedToken.scopes
            };
            setCustomer(customer);
        } catch (error) {
            throw new Error("Failed to login, either email or password is incorrect.");
        }
    };

    const logOut = () => {
        localStorage.removeItem("access_token");
        localStorage.removeItem("customerId");
        setCustomer(undefined);
    };

    const isCustomerAuthenticated = () => {
        const token = localStorage.getItem("access_token");
        if (!token) {
            return false;
        }
        const decodeToken: any = jwtDecode(token);
        const expiration = decodeToken.exp;
        if (Date.now() > expiration * 1000) {
            logOut();
            return false;
        }
        return true;
    };

    return (
        <AuthContext.Provider value={{
            customer,
            login,
            logOut,
            isCustomerAuthenticated,
            setCustomerFromToken

        }}>
            {children}
        </AuthContext.Provider>
    );
};


export const useAuth = (): AuthContextType => {
    const context = useContext(AuthContext);
    if (!context) {
        throw new Error("useAuth must be used within an AuthProvider");
    }
    return context;
};

export default AuthProvider;