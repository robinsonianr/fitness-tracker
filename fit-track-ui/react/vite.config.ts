import { defineConfig } from "vite";
import react from "@vitejs/plugin-react";
import tailwindcss from "@tailwindcss/vite";

export default defineConfig({

    plugins: [react(), tailwindcss()],
    server: {
        host: "0.0.0.0",
        port: 5173,
        allowedHosts: [
            "fitness-tracker-env.eba-3f5efq3k.us-east-1.elasticbeanstalk.com",
            "fit-track-dev.eba-jpnjhwum.us-east-1.elasticbeanstalk.com"
        ],
    },
    esbuild: {
        target: "es2020",
        logLevel: "error"
    }
});