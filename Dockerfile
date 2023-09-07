# Use a base image that includes both Node.js and Java
FROM openjdk:17-jdk-alpine

# Set the working directory in the image
WORKDIR /app

# Copy the backend (Java application) JAR file into the image
COPY build/libs/fitness-tracker-0.0.1.jar fitness-tracker-0.0.1.jar

# Copy the frontend (UI build) files into a directory called "ui" in the image
COPY src/main/ui/build /app/ui

# Define the command to run the Java backend
CMD ["java", "-jar", "fitness-tracker-0.0.1.jar"]