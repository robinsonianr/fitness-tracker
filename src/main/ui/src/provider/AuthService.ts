class AuthService {
    async login(formData: any) {
        try {
            const response = await fetch("/api/v1/auth/login", {
                method: "POST",
                headers: {"content-type": "application/json"},
                body: JSON.stringify(formData),
            });

            if (response.ok) {
                const token = await response.headers.get("authorization");
                console.log("Success", token);

                if (token !== null) {
                    localStorage.setItem("jwtToken", token);

                    return response;

                } else {
                    console.log("Token is null")
                }
            }

        } catch (error) {
            console.log("Error", error)
        }
    }


    logout () {
        localStorage.removeItem("jwtToken")
    }

    async register (formData: any) {
        try {
            const response = await fetch("/api/v1/customers", {
                method: "POST",
                headers: {"Content-Type": "application/json"},
                body: JSON.stringify(formData),
            });

            if (response.ok) {
                const token = await response.headers.get("authorization");
                console.log("Success", token);

                if (token !== null) {
                    localStorage.setItem("jwtToken", token);

                    return response;
                } else {
                    console.log("Token is null")
                }
            }

        } catch (error) {
            console.log("Error", error)
        }
    }


    getCurrentToken() {
        return localStorage.getItem("jwtToken");
    }


}

const authServiceInstance = new AuthService();
export default authServiceInstance;