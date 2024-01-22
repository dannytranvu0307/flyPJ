
FROM maven:3.8.3-openjdk-11-slim AS build

WORKDIR /app

COPY pom.xml .

RUN mvn dependency:go-offline

COPY src ./src

RUN mvn package -DskipTests

FROM openjdk:11-jre-slim

WORKDIR /app

ARG JAR_FILE=/app/target/flyPJ-0.0.1-SNAPSHOT.jar

COPY --from=build ${JAR_FILE} ./app.jar

CMD ["java", "-jar", "app.jar"]
