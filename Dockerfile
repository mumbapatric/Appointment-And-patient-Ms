# === Build Stage ===
FROM maven:3.9.6-eclipse-temurin-17 AS build
WORKDIR /build
COPY . .
RUN mvn clean package -DskipTests

# === Runtime Stage ===
FROM openjdk:17-jdk-slim
WORKDIR /app
COPY --from=build /build/target/Appointment-And-Patient-MS-0.0.1-SNAPSHOT.jar /app/Appointment-And-Patient-MS.jar
EXPOSE 8080
CMD ["java", "-jar", "Appointment-And-Patient-MS.jar"]
