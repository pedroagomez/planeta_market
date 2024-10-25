FROM openjdk:21-jdk-slim

ARG JAR_FILE=target/Planeta-0.0.1-SNAPSHOT.jar
COPY ${JAR_FILE} app_planeta.jar

ENTRYPOINT ["java","-jar","/app_planeta.jar"]
