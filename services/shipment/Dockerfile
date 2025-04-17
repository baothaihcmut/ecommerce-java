FROM gradle:8.4-jdk21 AS builder
WORKDIR /app

COPY gradle/ ./gradle
COPY gradlew settings.gradle build.gradle ./

COPY libs/ ./libs
COPY services/shipment/ ./services/shipment
RUN ./gradlew :services:shipment:bootJar

FROM openjdk:21-jdk
WORKDIR /app

COPY services/users/build/libs/shipment-service-1.0.0.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]
