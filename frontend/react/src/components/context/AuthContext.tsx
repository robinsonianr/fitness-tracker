import React, {createContext, useContext, useEffect, useState} from "react";
import {login as performLogin} from "../../services/client";
import jwtDecode from "jwt-decode";


type Customer = {
    username: string;
    roles: String[];
};

type AuthContextType = {
    customer: Customer | null;
    login: (formData: any) => Promise<void>;
    logOut: () => void;
    isCustomerAuthenticated: () => boolean;
    setCustomerFromToken: () => void;
};


const AuthContext = createContext<AuthContextType | null>(null);

const AuthProvider = ({children}: { children: any }) => {
    const [customer, setCustomer] = useState<Customer | null>(null);

    const setCustomerFromToken = () => {
        let token: any = localStorage.getItem("access_token");
        if (token) {
            token = jwtDecode(token);
            const customer: Customer = {
                username: token.sub,
                roles: token.scopes
            };
            setCustomer(customer)
        }
    }
    useEffect(() => {
        setCustomerFromToken()
    }, [])

    const login = async (formData: any): Promise<void> => {
        return new Promise<void>((resolve: any, reject: any): void => {
            performLogin(formData).then(res => {
                const jwtToken = res.headers["authorization"];
                localStorage.setItem("access_token", jwtToken);

                const decodedToken: any = jwtDecode(jwtToken);

                const customer: Customer = {
                    username: decodedToken.sub,
                    roles: decodedToken.scopes
                };
                setCustomer(customer)
                resolve();
            }).catch(err => {
                reject(err);
            })
        })
    }

    const logOut = () => {
        localStorage.removeItem("access_token")
        setCustomer(null)
    }

    const isCustomerAuthenticated = () => {
        const token = localStorage.getItem("access_token");
        if (!token) {
            return false;
        }
        const decodeToken: any = jwtDecode(token);
        const expiration = decodeToken.exp
        if (Date.now() > expiration * 1000) {
            logOut()
            return false;
        }
        return true;
    }

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
    )
}
// class AuthContext {
//     async login(formData: any) {
//         try {
//             const response = await fetch("/api/v1/auth/login", {
//                 method: "POST",
//                 headers: {"content-type": "application/json"},
//                 body: JSON.stringify(formData),
//             });
//
//             if (response.ok) {
//                 const token = await response.headers.get("authorization");
//                 console.log("Success", token);
//
//                 if (token !== null) {
//                     localStorage.setItem("jwtToken", token);
//
//                     return response;
//
//                 } else {
//                     console.log("Token is null")
//                 }
//             }
//
//         } catch (error) {
//             console.log("Error", error)
//         }
//     }
//
//
//     logout () {
//         localStorage.removeItem("jwtToken")
//     }
//
//     async register (formData: any) {
//         try {
//             const response = await fetch("/api/v1/customers", {
//                 method: "POST",
//                 headers: {"Content-Type": "application/json"},
//                 body: JSON.stringify(formData),
//             });
//
//             if (response.ok) {
//                 const token = await response.headers.get("authorization");
//                 console.log("Success", token);
//
//                 if (token !== null) {
//                     localStorage.setItem("jwtToken", token);
//
//                     return response;
//                 } else {
//                     console.log("Token is null")
//                 }
//             }
//
//         } catch (error) {
//             console.log("Error", error)
//         }
//     }
//
//
//     getCurrentToken() {
//         return localStorage.getItem("jwtToken");
//     }
//
//
// }


export const useAuth = (): AuthContextType => {
    const context = useContext(AuthContext);
    if (!context) {
        throw new Error('useAuth must be used within an AuthProvider');
    }
    return context;
};

export default AuthProvider;