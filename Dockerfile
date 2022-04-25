FROM openjdk:17-alpine

WORKDIR /app
COPY target/ .

CMD ["java", "-jar", "/app/learnit-0.0.1-SNAPSHOT.jar"]
