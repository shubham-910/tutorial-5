# Use an official Java runtime as a parent image
FROM openjdk:17-slim

# Set the working directory in the container
WORKDIR /app

# Copy the current directory contents into the container at /app
COPY . /app

# Grant execution permissions to the mvnw script
RUN chmod +x ./mvnw

# Compile and package the application
RUN ./mvnw clean package

# Run the application
CMD ["java", "-jar", "target/your-app-name.jar"]
