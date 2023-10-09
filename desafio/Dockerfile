#Maven Build
FROM maven:3.8.3-openjdk-17 AS builder
COPY pom.xml /app/
COPY src /app/src

RUN --mount=type=cache,target=/root/.m2 mvn -f /app/pom.xml clean package -DskipTests


#Run Api
FROM openjdk:17-alpine

WORKDIR /jar

COPY --from=builder /app/target/desafio-*.jar /jar/desafio-api.jar
COPY data /jar/data
ENTRYPOINT java -jar desafio-api.jar