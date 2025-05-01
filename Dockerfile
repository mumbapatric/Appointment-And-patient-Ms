# Use Maven to build the project
FROM maven:3.9.6-eclipse-temurin-17 AS build

# Create working directory
RUN mkdir -p /app
WORKDIR /app

# Copy the pom.xml file to /app
COPY pom.xml /app/

# Copy the source code to /app (also include src folder if it exists)
COPY src /app/src/

# Verify that pom.xml exists
RUN ls -l /app

# Package the app
RUN mvn clean package -DskipTests