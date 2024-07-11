# Use an official Java runtime as a parent image
FROM openjdk:17-slim

# Set the working directory in the container
WORKDIR /app

# Copy the pre-built JAR file from the local machine to the container
COPY target/littleLight-0.0.1-SNAPSHOT.jar /app/app.jar

# Run the application
CMD ["java", "-jar", "/app/app.jar"]
