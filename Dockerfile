# Use Maven for building the project
FROM maven:3.9.6-eclipse-temurin-17 AS build

# Set the working directory inside the container for Maven
WORKDIR /app

# Copy the pom.xml file and download dependencies separately for caching efficiency
COPY pom.xml /app/
RUN mvn dependency:go-offline -B

# Copy the source code to /app/src/
COPY src /app/src/

# Build the application and skip tests
RUN mvn clean package -DskipTests

# Prepare the runtime image
FROM eclipse-temurin:17-jdk

# Working directory for the runtime container
WORKDIR /app

# Copy the built JAR file from the build stage to the runtime stage
COPY --from=build /app/target/*.jar /app/app.jar

# Default command to run the application
CMD ["java", "-jar", "/app/app.jar"]