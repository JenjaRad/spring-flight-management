
FROM maven:3.8.5 AS maven

LABEL MAINTAINER="Eugene Radchenko"

WORKDIR /usr/src/app

COPY . /usr/src/app

RUN mvn package -Dmaven.test.skip=true

FROM openjdk:17-alpine

ARG JAR_FILE=flight-management.jar

WORKDIR /opt/app

COPY --from=maven /usr/src/app/target/${JAR_FILE} /opt/app

ENTRYPOINT ["java", "-jar", "flight-management.jar"]
