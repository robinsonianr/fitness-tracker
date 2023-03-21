FROM openjdk:17-jdk-alpine
COPY build/libs/fitness-tracker-0.0.1.jar fitness-tracker-0.0.1.jar
CMD ["java","-jar","/fitness-tracker-0.0.1.jar"]
