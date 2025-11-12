FROM openjdk:8-jdk-alpine
VOLUME /tmp
COPY ./target/oss-0.0.1-SNAPSHOT.jar oss-0.0.1-SNAPSHOT.jar
ENTRYPOINT ["java","-jar","oss-0.0.1-SNAPSHOT.jar","&"]