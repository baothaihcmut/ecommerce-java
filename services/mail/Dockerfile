# FROM gradle:8.4-jdk21 AS builder
# WORKDIR /app

# COPY gradle/ ./gradle
# COPY gradlew settings.gradle build.gradle ./

# COPY libs/ ./libs
# COPY services/mail/ ./services/mail
# RUN ./gradlew :services:mail:bootJar

FROM eclipse-temurin:21-jdk
WORKDIR /app

# COPY --from=builder /app/services/mail/build/libs/mail-service-1.0.0.jar app.jar
COPY services/mail/build/libs/mail-service-1.0.0.jar app.jar
EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]
