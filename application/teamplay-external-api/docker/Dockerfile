FROM openjdk:8-jdk-alpine

ARG env

ADD teamplay-external-api.jar teamplay-external-api.jar

EXPOSE 8080

ENV SPRING_PROFILES_ACTIVE=$env
ENTRYPOINT exec java $JAVA_OPTS -Djava.security.egd=file:/dev/./urandom -jar /teamplay-external-api.jar
