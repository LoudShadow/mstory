FROM maven AS build

WORKDIR /usr/src/app

COPY pom.xml pom.xml
RUN mvn dependency:resolve

COPY src src
RUN mvn package

FROM openjdk:17-jdk-alpine

# Path: /usr/src/app

COPY  --from=build /usr/src/app/target/mstory-0.0.1-SNAPSHOT.jar mstory-0.0.1-SNAPSHOT.jar

CMD ["java","-jar","mstory-0.0.1-SNAPSHOT.jar"]

EXPOSE 8080:8080

# Path: /usr/src/app/target
# CMD ["java", "-jar", "mstory-0.0.1-SNAPSHOT.jar"]