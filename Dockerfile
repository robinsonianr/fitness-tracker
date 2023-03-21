FROM openjdk:17-jdk-alpine
ARG JAR_FILE=fitness-tracker-0.0.1.jar
COPY ${JAR_FILE} /
CMD ["java","-jar","/fitness-tracker-0.0.1.jar"]
