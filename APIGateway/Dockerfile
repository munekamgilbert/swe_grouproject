# Use the official maven/Java 17 image to create a build artifact.
FROM maven:3.8.1-openjdk-17 as builder
WORKDIR /usr/src/app
COPY . .
RUN mvn clean install

# Use OpenJDK 17 to run the JAR
FROM openjdk:17
COPY --from=builder /usr/src/app/target/*.jar /app/service.jar
ENTRYPOINT ["java","-jar","/app/service.jar"]
