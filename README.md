# Microservices Bank Application

## Description

This is a simple example of how to implement a microservices architecture with Spring Boot and H2 Database embedded.

### Technologies

- Java 11+
- Spring Boot 2.4.2
- H2 Database
- Log4j2
- Lombok
- JPA Repository


### How to run

1. Run the command `mvn clean install` to build the project.
2. Run the command `$env:SPRING_PROFILES_ACTIVE="test"` to set the environment variable.
   (For Windows, use the command `set SPRING_PROFILES_ACTIVE=test`)
   (For Linux, use the command `export SPRING_PROFILES_ACTIVE=test`)
3. Run the command `mvn spring-boot:run` to start the application.

### License

This project is licensed under the MIT License - see the [LICENSE](https://www.apache.org/licenses/LICENSE-2.0) file for details.