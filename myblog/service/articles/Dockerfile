FROM openjdk:8-jdk-alpine
VOLUME /tmp
COPY ./target/articles-0.0.1-SNAPSHOT.jar articles-0.0.1-SNAPSHOT.jar
ENTRYPOINT ["java","-jar","articles-0.0.1-SNAPSHOT.jar","&"]