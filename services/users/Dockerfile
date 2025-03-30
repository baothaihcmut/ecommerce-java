FROM gradle:8.4-jdk21 AS builder
WORKDIR /app

COPY gradle/ ./gradle
COPY gradlew settings.gradle build.gradle ./

COPY libs/ ./libs
COPY services/users/ ./services/users
RUN ./gradlew :services:users:bootJar

FROM openjdk:21-jdk
WORKDIR /app

COPY --from=builder /app/services/users/build/libs/users-service-1.0.0.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]
