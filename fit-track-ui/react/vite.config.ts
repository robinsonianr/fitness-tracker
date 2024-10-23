import { defineConfig } from "vite";
import react from "@vitejs/plugin-react";

export default defineConfig({
    plugins: [react()],
    server: {
        hmr: {
            timeout: 30000 // Increase the timeout value
        }
    },
});