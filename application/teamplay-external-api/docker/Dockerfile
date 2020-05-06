FROM openjdk:8-jdk-alpine

ARG env=dev

ADD teamplay-external-api.jar teamplay-external-api.jar

EXPOSE 8080

ENTRYPOINT exec java $JAVA_OPTS -Djava.security.egd=file:/dev/./urandom -jar -Dspring.profiles.active=${env} /cloud-config-server.jar
