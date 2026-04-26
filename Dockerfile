# Stage 1: Build the application
FROM maven:3.9-eclipse-temurin-21 AS builder
WORKDIR /app
# Copy the pom.xml and source code
COPY pom.xml .
COPY src ./src
# Package the Quarkus application (skip tests to speed up the build)
RUN mvn clean package -DskipTests

# Stage 2: Create the runtime image
FROM eclipse-temurin:21-jre-jammy
WORKDIR /app

# Copy the Quarkus fast-jar directory structure from the builder stage
COPY --from=builder /app/target/quarkus-app/lib/ /app/lib/
COPY --from=builder /app/target/quarkus-app/*.jar /app/
COPY --from=builder /app/target/quarkus-app/app/ /app/app/
COPY --from=builder /app/target/quarkus-app/quarkus/ /app/quarkus/

# Expose the standard Quarkus port
EXPOSE 8080

# Run the application
ENTRYPOINT ["java", "-Djava.net.preferIPv4Stack=true", "-jar", "/app/quarkus-run.jar"]