# Use the Amazon Corretto 21 image as the base image
FROM amazoncorretto:21

# Set the working directory in the container
WORKDIR /app

# Copy the JAR file into the container
COPY . /app

# Expose the application port (adjust as needed)
EXPOSE 8080

# Run the application
ENTRYPOINT ["java", "-jar", "app.jar"]
