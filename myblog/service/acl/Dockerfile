FROM openjdk:8-jdk-alpine
VOLUME /tmp
COPY ./target/acl-0.0.1-SNAPSHOT.jar acl-0.0.1-SNAPSHOT.jar
ENTRYPOINT ["java","-jar","acl-0.0.1-SNAPSHOT.jar","&"]