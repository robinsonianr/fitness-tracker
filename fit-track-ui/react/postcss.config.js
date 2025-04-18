/* eslint-disable no-undef */
// Determine environment without relying on process directly
const isDevelopment = process.env.NODE_ENV === "development";
const isProduction = process.env.NODE_ENV === "production";

export default {
    plugins: {
        // Import handling
        "postcss-import": {},

        // Features and organization
        "postcss-mixins": {},
        "postcss-nested": {},
        "postcss-custom-properties": {
            preserve: true,
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
        "postcss-browser-reporter": isDevelopment
            ? {
                selector: "body:before",       // Where to inject the warning in the browser
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
                // Filter out certain types of warnings
                filterRules: message => {
                    // Ignore specific warnings if needed
                    const ignoredWarnings = ["some-warning-to-ignore"];
                    return !ignoredWarnings.includes(message.plugin);
                }
            }
            : false,

        "postcss-reporter": {
            clearReportedMessages: true,     // Clear reported messages
            throwError: false,                // Won't throw an error on warnings
            filter: message => {
                // Only show warnings and errors
                return message.type === "warning" || message.type === "error";
            },
            formatter: input => {
                // Customize message format
                return `${input.type.toUpperCase()}: ${input.plugin} - ${input.text} (${input.file}:${input.line}:${input.column})`;
            },
            noPlugins: false,                // Show plugin names in messages
            sortByPosition: true,           // Sort messages by line / column positions
            positionless: "last",           // Where to log messages without positions
        },

        // Optimization (production only)
        "cssnano": isProduction ? {
            preset: ["default", {
                discardComments: {
                    removeAll: true,
                    removeAllButFirst: false,
                },
                normalizeWhitespace: true,
                minifyFontValues: true,
                minifyGradients: true,
                minifyParams: true,
                minifySelectors: true,
                mergeLonghand: true,
                mergeRules: true,
                reduceInitial: true,
                reduceTransforms: true,
                uniqueSelectors: true,
                zindex: false, // Disable z-index optimization to prevent potential issues
            }]
        } : false,
    }
};
