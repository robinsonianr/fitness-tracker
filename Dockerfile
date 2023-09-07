# Use a lightweight Java image as the base
FROM openjdk:17-jdk-alpine

# Set the working directory in the image
WORKDIR /app

# Copy the Java application JAR file into the image
COPY build/libs/fitness-tracker-0.0.1.jar fitness-tracker-0.0.1.jar

# Copy the built UI files from the src/main/ui/build directory into a directory called "ui" in the image
COPY src/main/ui/build /app/ui

# Define the command to run the Java application
CMD ["java", "-jar", "fitness-tracker-0.0.1.jar"]