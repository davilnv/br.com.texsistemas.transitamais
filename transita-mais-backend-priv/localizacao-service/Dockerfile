FROM eclipse-temurin:17-jdk
VOLUME /tmp
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} /app/app.jar
ENTRYPOINT ["java", "-jar", "/app/app.jar", "--spring.profiles.active=prod"]
