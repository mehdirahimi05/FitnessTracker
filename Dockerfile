# Stage 1: Build
FROM maven:3.9-eclipse-temurin-21-alpine AS build
WORKDIR /app

# copy Parent POM and Module POMs first
# → Dependencies caching before Source Code get copied
COPY pom.xml .
COPY storage/pom.xml storage/pom.xml
COPY api/pom.xml api/pom.xml

# install all Dependencies
RUN mvn dependency:go-offline -B

# copy Source Code both Module
COPY storage/src storage/src
COPY api/src api/src

# build the Projekt without Tests
RUN mvn package -DskipTests

# Stage 2: Run
FROM eclipse-temurin:21-jre-alpine
WORKDIR /app

# copy only the API JAR from Stage 1
# api/target/ because this is the Endpoint
COPY --from=build /app/api/target/fitness-tracker-api.jar app.jar

# start Spring Boot
ENTRYPOINT ["java", "-jar", "app.jar"]