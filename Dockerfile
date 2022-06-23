FROM openjdk:17-alpine

WORKDIR /app
COPY target/ .

ADD /src/main/resources/fonts/arial.ttf /app

CMD ["java", "-jar", "/app/learnit-0.0.1-SNAPSHOT.jar"]
