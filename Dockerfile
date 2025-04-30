# Use an official openjdk as a parent image
FROM openjdk:17-jdk-slim

# Set the working directory in the container
WORKDIR /app

# Copy the Spring Boot jar file into the container
COPY target/Appointment-And-Patient-MS-0.0.1-SNAPSHOT.jar /app/Appointment-And-Patient-MS.jar

# Expose the port that the app will run on
EXPOSE 8080

# Run the Spring Boot application
CMD ["java", "-jar", "Appointment-And-Patient-MS.jar"]
