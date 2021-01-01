# PhoneBook
## Table of Contents
- [Introduction](#introduction)
- [Features](#features)
- [Technologies](#technologies)
- [Setup](#setup)
- [Documentation](#documentation)
- [Versions](#versions)

## Introduction
In this service two operations of save and search contact performs.

- save operation:
At first, A contact with PENDING status save on the database
and then it will perform an async call to "https://api.github.com/users/{username}/repos"
and receive all repositories of that contact and save those repositories of that contact and 
change status of that contact to SUCCESS.
when poor connection occurs it performs retries several times and when 
it receives user repositories, it updates contact repositories on the database. 
If after several times retry did not receive any response, a job with a fixed delay rate repeats 
the update operation.

Reason of select this approach:  
When the Internet is weak, it is better to try again ,
and when no results are obtained,with run a job ,try again so user data is not lost.

- search operation:
It performs dynamic search across contacts
 
## Features
These service can perform,
* Save Contact
* Search Contact
* Save GithubRepository of each contact
## Technologies
* [Spring Boot](https://spring.io/projects/spring-boot)
* [Mysql](https://www.mysql.com)
* [H2 Database](https://www.h2database.com/html/main.html)
* [Resilience4j](https://resilience4j.readme.io)
* [Swagger](https://swagger.io)
* [Lombok](https://projectlombok.org)
* [MapStruct](https://mapstruct.org)
## Setup
This project used Gradle as build tools
### Prerequisites
To run this application you should install [Docker](https://www.docker.com) and docker compose
- Ubuntu
    1) Docker:  
You can find help about installation of docker from this [link](https://docs.docker.com/engine/install/ubuntu/)
    2) Docker Compose:  
You can find help about installation of docker compose from this [link](https://docs.docker.com/compose/install/)
### Test
* Run all test cases of application
```
./gradlew test
```
### Build
* Build project without test execution
    - This section build project and copy jar file of the application to build/libs folder 
```
./gradlew build -x test
``` 
* Build docker image
    - This section build mysql and application image
```
docker-compose build
```
### Deployment
* Run docker images
    - This section first run mysql image and after that run application image since application image depends on mysql
    - Use ```-d``` because if you terminate your console don't stop docker images
```
docker-compose up -d
```
### Status
* To check status of docker containers
```
docker ps
```
This command are show the status of running containers
### Log
* To see logs about containers
    - Use ```-f``` to see logs continuously
```
docker-compose logs -f
```
### Stop
* To stop containers
```
docker-compose stop
```
## Documentation
- [Swagger](http://localhost:8080/swagger-ui.html)
- [Java Documentation](https://roghayehfarhadi.github.io/phonebook/)
## Versions
1) [0.0.1-SNAPSHOT](https://github.com/roghayehfarhadi/phonebook/releases/tag/v0.0.1-SNAPSHOT)  
    - contains create and search contact and fetch github repositories of each contact 