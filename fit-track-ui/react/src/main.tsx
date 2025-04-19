import React from "react";
import ReactDOM from "react-dom/client";
import "./styles/global.css";
import {RouterProvider} from "react-router-dom";

import {router} from "./App";
import AuthProvider from "./components/context/AuthContext";


const root = ReactDOM.createRoot(
    document.getElementById("root") as HTMLElement
);
root.render(
    <React.StrictMode>
        <AuthProvider>
            <RouterProvider router={router}/>
        </AuthProvider>
    </React.StrictMode>
);


