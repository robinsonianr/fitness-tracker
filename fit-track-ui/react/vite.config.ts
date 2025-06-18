import { defineConfig } from "vite";
import react from "@vitejs/plugin-react-swc";
import tailwindcss from "@tailwindcss/vite";

export default defineConfig({

    plugins: [react(), tailwindcss()],
    server: {
        allowedHosts: ["fitness-tracker-env.eba-3f5efq3k.us-east-1.elasticbeanstalk.com"],
    }
});