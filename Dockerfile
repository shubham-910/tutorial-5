# Use an official Java runtime as a parent image
FROM openjdk:17-slim

# Set the working directory in the container
WORKDIR /app

# Copy the current directory contents into the container at /app
COPY . /app

# Compile and package the application
RUN ./mvnw clean package

# Run the application
CMD ["java", "-jar", "target/littleLight-0.0.1-SNAPSHOT.jar"]

