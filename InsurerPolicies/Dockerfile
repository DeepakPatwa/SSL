FROM openjdk:11

ADD ./target/InsurerPolicies-0.0.1-SNAPSHOT.jar /event/src/InsurerPolicies-0.0.1-SNAPSHOT.jar
EXPOSE 8079
WORKDIR event/src
ENTRYPOINT ["java","-jar","InsurerPolicies-0.0.1-SNAPSHOT.jar"]
