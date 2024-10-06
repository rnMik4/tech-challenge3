FROM maven:latest AS build

WORKDIR /app

COPY pom.xml .
COPY src ./src

RUN mvn clean install package -DskipTests

FROM openjdk:21-jdk-slim

COPY --from=build /app/target/*.jar /app/app.jar

ENTRYPOINT [ "java", "-jar", "/app/app.jar"]
