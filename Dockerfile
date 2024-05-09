# Stage 1: Build the application
FROM gradle:jdk11 AS build
COPY . /app
WORKDIR /app
RUN gradle build --no-daemon

# Stage 2: Create the final image
FROM openjdk:11-jre-slim
COPY --from=build /app/build/libs/demo-1.jar /demo.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "/demo.jar"]
