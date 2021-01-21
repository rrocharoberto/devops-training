# devops-training - Development Instructions

This document show how to configure and execute the dev-ops project in development environments.

Please refer to [the README.md](README.md) for the main information about the project.



# Running in the **development** environment

To run the project in developent environment, execute the following commands:

## 1. Configure the database

Execute the database script contents of file `docker/postgres/script.sql` in PostgreSQL.

## 2. Configure the backend database properties

Configure the database properties in the file `src/resources/application.properties` inside the project `student-backend`.

## 3. Build and run the backend project: 

### For development environment (will generate Spring Boot jar file):
Go to `student-backend` directory and run:

`mvn clean install`

`mvn spring-boot:run`


## 4. Run the frontend project: 
Go to `student-frontend` directory and run:

`ng build`

`ng serve --proxy-config proxy.config.js`

## 5. Run the tests

Execute the test classes in the projects  `student-api-test` and `student-functional-test`.

## 6. Test environment

Use the following URLs in your browser:

Front: `http://localhost:4200/students`

Back: `http://localhost:8080/student-backend`


