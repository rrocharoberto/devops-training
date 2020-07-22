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

## Creating the images:

# Build the BackEnd project:

`cd student-backend`
`mvn clean install`

# Build the API-Test project:
`cd student-api-test`
`mvn clean install -Dmaven.test.skip=true`

# Create the database image:

Go to `docker/postgres` directory:

Create the image:
`docker image build -t db-student .`


# Create the backend image:

Go to `docker/backend` directory.

Copy the jar file of the Spring Boot backend application:
`cp ../../student-backend/target/student-backend-1.0.jar .`

Create the image:
`docker image build -t backend-student .`


# Create the frontend image:

Go to `docker/frontend` directory.

Create the image:
`docker image build -t frontend-student .`


## Running the containers

Create a docker network:
docker network create student-net

# Run the database container:
docker container run --network student-net --name studentdb-server --rm -d db-student

Optional: check the container logs:
docker container logs -f studentdb-server

# Run the backend container:
docker container run -p 8080:8080 --network student-net --name studentback-server --rm -d backend-student

Optional: check the container logs:
docker container logs -f studentback-server

## Other way of running the entire app, using docker.
Go to docker directory and run:
`docker-compose up`


