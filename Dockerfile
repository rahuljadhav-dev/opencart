# Base image
FROM maven:3.9.6-eclipse-temurin-17

# Set workdir
WORKDIR /project

# Copy all files
COPY . .

# Pre-download dependencies (no test)
RUN mvn clean compile

# Default command (tests run when you launch container)
CMD ["mvn", "test"]
