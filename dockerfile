# Creation of the image from the official maven and java images. Version compatible with those of the project
FROM maven:3.8.8-openjdk-22 AS build

# Creation of the directory "app" inside the container, we will use
WORKDIR /app

# Copy the Maven project file (with dependencies) to the working directory
COPY pom.xml /app/

# Download dependencies defined in pom.xml
RUN mvn dependency:go-offline -B

# Copy the source code into the container
COPY src /app/src/

# Build the application using Maven (-DskipTests for skipping tests and speed up the process)
RUN mvn clean package -DskipTests

# Use a smaller JRE image for running the application
FROM openjdk:22-jdk-slim

# Copy the JAR file from the build stage to the new image
# I got the path after running mvn clean package -DskipTests in cmd from the project directory where pom.xml is
COPY --from=build /app/target/Template-0.0.1-SNAPSHOT.jar /app/app.jar

# Expose port 8080 (port of my choice)
EXPOSE 8080

# Command to run the application
CMD ["java", "-jar", "/app/app.jar"]

