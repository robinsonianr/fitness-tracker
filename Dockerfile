FROM openjdk:12-jdk-alpine
COPY build/libs/*.jar /
CMD ["java", "-jar", "/fitness-tracker.jar"]
