FROM openjdk:11.0-oracle
ADD ./target/demo-0.0.1-SNAPSHOT.jar /usr/src/demo-0.0.1-SNAPSHOT.jar
EXPOSE 8094
WORKDIR usr/src
ENTRYPOINT ["java","-jar","demo-0.0.1-SNAPSHOT.jar"]
