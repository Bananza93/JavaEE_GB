FROM openjdk:17
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} app.jar
COPY media media
ENTRYPOINT ["java", "-jar", "/app.jar"]