export default {
    plugins: {
        // Import handling
        "postcss-import": {},

        // Features and organization
        "postcss-mixins": {},
        "postcss-nested": {},
        "postcss-custom-properties": {
            preserve: true,
            importFrom: "src/styles/variables.css",
        },
        "postcss-custom-media": {},
        "postcss-utilities": {},

        // Modern CSS features
        "postcss-preset-env": {
            features: {
                "nesting-rules": false,
                "custom-properties": false,
                "custom-media-queries": false,
            },
            browsers: ["> 1%", "last 2 versions", "not dead"],
        },

        // Vendor prefixes
        "autoprefixer": {},

        // Debug and reporting (development only)
        "postcss-browser-reporter": import.meta.env.DEV
            ? {
                selector: "body:before",
                styles: {
                    position: "fixed",
                    top: "0",
                    left: "0",
                    right: "0",
                    "z-index": "1000",
                    padding: "10px",
                    background: "#fff3cd",
                    color: "#856404",
                    "border-bottom": "1px solid #ffeeba",
                    "white-space": "pre-wrap",
                },
                filterRules: message => {
                    const ignoredWarnings = ["some-warning-to-ignore"];
                    return !ignoredWarnings.includes(message.plugin);
                }
            }
            : false,

        "postcss-reporter": {
            clearReportedMessages: true,
            throwError: false,
            filter: message => {
                return message.type === "warning" || message.type === "error";
            },
            formatter: input => {
                return `${input.type.toUpperCase()}: ${input.plugin} - ${input.text} (${input.file}:${input.line}:${input.column})`;
            },
            noPlugins: false,
            sortByPosition: true,
            positionless: "last",
        },

        // Optimization (production only)
        "cssnano": import.meta.env.PROD
            ? {
                preset: ["default", {
                    discardComments: {
                        removeAll: true,
                    },
                }],
            }
            : false
    }
}; 