FROM openjdk:17-jdk-slim
WORKDIR /app
COPY ./target/AccountService*.jar ./app.jar
CMD ["java", "-jar", "app.jar"]