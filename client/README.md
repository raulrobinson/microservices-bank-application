# Client Bank Application

This is a service for administrating bank accountEntities. It is a simple client-server application that allows users to create clients.

## Technologies

- Java 17+
- Spring Boot 3.3.8
- Maven 3.8.4
- H2 Database
- Lombok
- Swagger
- JUnit 5
- JPA / Hibernate

## How to run

To run the application, you need to have Java 17+ installed on your machine. You can download it [here](https://www.oracle.com/java/technologies/javase-jdk17-downloads.html).

After installing Java, you can run the application using the following command:

1. Run the command `mvn clean install` to build the project.
2. Run the command `$env:SPRING_PROFILES_ACTIVE="test"` to set the environment variable.
   (For Windows, use the command `set SPRING_PROFILES_ACTIVE=test`)
   (For Linux, use the command `export SPRING_PROFILES_ACTIVE=test`)
3. Run the command `mvn spring-boot:run` to start the application.
4. Access the URL `http://localhost:8001/swagger-ui/index.html` to see the API documentation.

The application will start on port 8001. You can access the Swagger documentation at [http://localhost:8001/swagger-ui/index.html](http://localhost:8001/swagger-ui/index.html).

## How to test

To run the tests, you can use the following command:

```shell
mvn test
```

## How to build

To build the application, you can use the following command:

```shell
mvn clean package
```

The command will generate a JAR file in the `target` directory. You can run the JAR file using the following command:

```shell
java -jar target/client-bank-0.0.1-SNAPSHOT.jar
```

### Author

- [Raul Bolivar Navas](https://www.linkedin.com/in/rasysbox/)

### License

This project is licensed under the MIT License - see the [LICENSE](https://www.apache.org/licenses/LICENSE-2.0) file for details.
