# Stage 1: Use a base image that includes both Node.js and Java
FROM openjdk:17-jdk-alpine

# Copy the backend (Java application) JAR file into the image
COPY build/libs/fitness-tracker-0.0.1.jar fitness-tracker-0.0.1.jar

# Stage 2: Build the UI
FROM node:14-alpine AS build-ui

WORKDIR /ui

COPY src/main/ui/package.json src/main/ui/package-lock.json /ui/
RUN npm install
COPY src/main/ui /ui
RUN npm run build

 # Stage 2: Create the final image
FROM openjdk:17-jdk-alpine

COPY --from=build-ui /ui/build /ui

CMD ["java", "-jar", "fitness-tracker-0.0.1.jar"]