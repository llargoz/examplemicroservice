FROM openjdk:17-jdk-slim
WORKDIR /app
COPY ./target/NotificationService*.jar ./app.jar
CMD ["java", "-jar", "app.jar"]