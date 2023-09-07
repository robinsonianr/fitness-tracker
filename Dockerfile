# Use a lightweight Java image as the base
FROM openjdk:17-jdk-alpine

# Set the working directory for the application
WORKDIR /app

# Copy the Java application JAR file
COPY build/libs/fitness-tracker-0.0.1.jar fitness-tracker-0.0.1.jar

# Copy the built UI files from the src/main/ui/build directory
COPY src/main/ui/build ./ui

# Define the command to run the Java application
CMD ["java", "-jar", "fitness-tracker-0.0.1.jar"]