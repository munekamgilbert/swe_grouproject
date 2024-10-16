
# Use the official Maven image to build the project
FROM maven:3.8.3-openjdk-17 AS build

# Set working directory
WORKDIR /app

# Copy the entire project source code into the image
COPY . .

# Build the project
RUN mvn clean package -DskipTests

# Use the OpenJDK image for the runtime
FROM openjdk:17

# Set working directory in the container
WORKDIR /app

# Copy the jar file from the build stage into this image
COPY --from=build /app/target/*.jar /app/customer-management-service.jar

# Expose port
EXPOSE 9090

# Run the application
CMD ["java", "-jar", "customer-management-service.jar"]
