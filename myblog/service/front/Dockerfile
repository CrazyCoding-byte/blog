FROM openjdk:8-jdk-alpine
VOLUME /tmp
COPY ./target/front-0.0.1-SNAPSHOT.jar front-0.0.1-SNAPSHOT.jar
ENTRYPOINT ["java","-jar","front-0.0.1-SNAPSHOT.jar","&"]