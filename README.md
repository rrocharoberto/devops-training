# devops-training
Set of projects used in dev-ops training with Jenkings.

# Instructions for running the project.

To run the project in dev environment, execute the follow commands:

## Before running the server side:
Execute the database script contents of file `script.sql` in PostgreSQL.

Configure the file `application.properties` inside the project `student-backend`.

## In the server side: 
Go to student-backend directory and run:
`mvn clean install`
`mvn spring-boot:run`

## In the client side: 
Go to student-frontend directory and run:
`ng serve --proxy-config proxy.config.js`

## Other way of running the entire app, using docker.
Go to docker directory and run:
`docker-compose up`

