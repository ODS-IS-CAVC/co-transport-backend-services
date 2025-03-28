# Use the official OpenJDK image as a parent image
FROM openjdk:17-jdk-slim

# Set the working directory in the container
WORKDIR /app

# Copy the compiled Spring Boot jar file into the container
COPY target/nlj-carrier-link-service.jar /app/nlj-carrier-link-service.jar

# Run the Spring Boot application
ENTRYPOINT ["java", "-jar", "nlj-carrier-link-service.jar"]

# Expose the port the app runs on
EXPOSE 9500