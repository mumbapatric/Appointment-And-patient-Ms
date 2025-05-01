# === Build stage ===
FROM maven:3.9.6-eclipse-temurin-17 as build
WORKDIR /app
COPY . .
RUN mvn clean package -DskipTests

# === Runtime stage ===
FROM openjdk:17-jdk-slim
WORKDIR /app
COPY --from=build /app/target/Appointment-And-Patient-MS-0.0.1-SNAPSHOT.jar ./Appointment-And-Patient-MS.jar
EXPOSE 8080
CMD ["java", "-jar", "Appointment-And-Patient-MS.jar"]
