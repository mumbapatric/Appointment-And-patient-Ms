# === Build stage ===
FROM maven:3.9.6-eclipse-temurin-17 as build
WORKDIR /build
COPY . .

WORKDIR /build/Appointment-And-Patient-MS
RUN mvn clean package -DskipTests

# === Runtime stage ===
FROM openjdk:17-jdk-slim
WORKDIR /app
COPY --from=build /build/Appointment-And-Patient-MS/target/Appointment-And-Patient-MS-0.0.1-SNAPSHOT.jar ./Appointment-And-Patient-MS.jar
EXPOSE 8080
CMD ["java", "-jar", "Appointment-And-Patient-MS.jar"]
