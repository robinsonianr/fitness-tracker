import { defineConfig } from "vite";
import react from "@vitejs/plugin-react-swc";

export default defineConfig({
    plugins: [react()],
    server: {
        allowedHosts: ["fitness-tracker-env.eba-3f5efq3k.us-east-1.elasticbeanstalk.com"],
    },
    css: {
        preprocessorOptions: {
            scss: {
                api: "modern", // or "modern", "legacy"
            },
        },
    },
});