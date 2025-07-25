import React from "react";
import ReactDOM from "react-dom/client";
import "./global.css";
import {RouterProvider} from "react-router-dom";

import {router} from "./App";
import AuthProvider from "./context/AuthContext";
import { ThemeProvider } from "./context/theme-context";

const root = ReactDOM.createRoot(
    document.getElementById("root") as HTMLElement
);
root.render(
    <React.StrictMode>
        <ThemeProvider>
            <AuthProvider>
                <RouterProvider router={router}/>
            </AuthProvider>
        </ThemeProvider>
    </React.StrictMode>
);


