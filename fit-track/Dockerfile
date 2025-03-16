# Use a base image that includes both Node.js and Java
FROM openjdk:17-jdk-alpine

# Copy the backend (Java application) JAR file into the image
COPY /build/libs/fit-track-*.jar fitness-tracker-api.jar

# Define the command to run the Java backend
CMD ["java", "-jar", "fitness-tracker-api.jar"]
