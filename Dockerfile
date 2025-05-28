# Use OpenJDK base image
FROM openjdk:17-jdk-slim

# Set working directory inside the container
WORKDIR /app

# Copy Gradle wrapper and project files
COPY . .

# Build the application
RUN ./gradlew build --no-daemon

# Expose the port your app runs on (default 8082)
EXPOSE 8082

# Run the JAR file
CMD ["java", "-jar", "build/libs/agribid-server-0.0.1-SNAPSHOT.jar"]
