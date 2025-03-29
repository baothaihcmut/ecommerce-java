FROM gradle:8.4-jdk21 AS builder
WORKDIR /app

COPY gradle/ ./gradle
COPY gradlew settings.gradle build.gradle ./

COPY libs/ ./libs 
COPY gateway ./gateway
RUN ./gradlew :gateway:bootJar

FROM openjdk:21-jdk-slim
WORKDIR /app

COPY --from=builder /app/gateway/build/libs/api-gateway-1.0.0.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]