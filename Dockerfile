FROM openjdk:8-jdk-alpine

ADD ./target/testtask-0.0.1-SNAPSHOT.jar /app/
CMD ["java", "-Xmx100m", "-jar", "/app/testtask-0.0.1-SNAPSHOT.jar"]

EXPOSE 8080
