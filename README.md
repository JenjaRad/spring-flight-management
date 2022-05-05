# Air Companies Management System

## Description

- Crud operations for Air company.
- Endpoint to move airplanes between companies
- Endpoint to find all Air Company Flight by status
- Endpoint to find all Flights in ACTIVE status and started more than 24 hours ago.
- Endpoint to add new flight
- Endpoint to change Flight status
- Endpoint to find all Flights in COMPLETED status and diff between started and ended time.
- Endpoint to get all flights by status and Air Company name.
- Possibility to update Air Company by name.
- Opportunity to audit a data.
- Global Exception handling.
- Simple implementation of Spring AOP for logging and tracking.
- Air Company Web integration test.

## Technologies

Java 17, Spring Boot, Spring Data, Spring AOP, Spring HATEOAS, PostgreSQL, H2, Flyway, Rest API, Docker, Git, Lombok,
Log4J2, Jasypt, JCache.

## Requirements
Use features of Java 8 + such Streams, Optional. Dockerize an application by creating a multi-staged build based on Alpine.

## How To Start

<h4> Clone the application </h4>

<h4> Create a PostgreSQL database </h4>

```bash
$ create database flight_db
```

<h4> Change PostgreSQL username and password as per your PostgreSQL installation locally. </h4>

- Edit `spring.datasource.username` and `spring.datasource.password` properties as per your PostgreSQL installation
  in `src/main/resources/application.properties` 
  
- You can avoid encrypting of personal data in `src/main/application.properties`.

<h4> Run the app </h4>

```shell
./mvnw spring-boot:run (Maven Wrapper) # For UNIX/Linux based operating systems
mvnw.cmd spring-boot:run (Maven Wrapper) # For Windows based operating systems
```

- The server will start on `server.port:8080` and will create the tables for you using migrations.

<h4> Run an app in a container </h4>

- Firstly you need to build a simple base image
```bash
$ docker build -t <YourName> .
```
- Finally you can start a local container using your personal environment.
```bash
$ docker run --net=host <IMAGE ID>
```
