# Stage 1: Build the application
FROM gradle:8.4-jdk21 AS builder

# Set the working directory
WORKDIR /app

# Copy project files to the working directory
COPY . .

# Build the project using Gradle (adjust for Maven if needed)
RUN gradle clean build --no-daemon

# Stage 2: Create the runtime image
FROM amazoncorretto:21

# Set the working directory
WORKDIR /app

# Copy the JAR file from the builder stage to the runtime image
COPY --from=builder /app/build/libs/*.jar /app/app.jar

# Expose the application port (adjust as needed)
EXPOSE 8080

# Command to run the main() method
ENTRYPOINT ["java", "-jar", "/app/"]
