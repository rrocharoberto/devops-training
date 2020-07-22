# devops-training
Set of projects used in dev-ops training with Jenkings.

The goal of this repository id to build and run three projects, each one in its own container:
1) Database using PostgreSQL
2) Backend in Spring Boot
3) Frontend in Angular

This environment will be used in a CI project using Jenkins for automation of the proccess..

# Instructions for building the images

## 1. Building the projects:

### 1.1. Build the BackEnd project:

`cd student-backend`

`mvn clean install`

### 1.2. Build the API-Test project:
`cd student-api-test`

`mvn clean install -Dmaven.test.skip=true`

## 2. Creating the docker images:

### 2.1. Create the database image:

Go to `docker/postgres` directory:

Create the image:

`docker image build -t db-student .`


### 2.2. Create the backend image:

Go to `docker/backend` directory.

Copy the jar file of the Spring Boot backend application:

`cp ../../student-backend/target/student-backend-1.0.jar .`

Create the image:

`docker image build -t backend-student .`


### 2.3. Create the frontend image:

Go to `docker/frontend` directory.

Create the image:

`docker image build -t frontend-student .`


# Running the containers

Create a docker network:

`docker network create student-net`

## 1. Run the database container:
`docker container run --network student-net --name studentdb-server --rm -d db-student`

Optional: check the container logs:

`docker container logs -f studentdb-server`

## 2. Run the backend container:
`docker container run -p 8080:8080 --network student-net --name studentback-server --rm -d backend-student`

Optional: check the container logs:

`docker container logs -f studentback-server`


## 3. Run the frontend container:
`docker container run -p 4200:4200 --network student-net --name studentfront-server --rm -d frontend-student`

Optional: check the container logs:

`docker container logs -f studentfront-server`


## (not yet implemented) Running the entire environment, using Docker Compose.
Go to docker directory and run:

`docker-compose up`



# Running in the development environment

To run the project in dev environment, execute the follow commands:

## 1. Configure the database

Execute the database script contents of file `script.sql` in PostgreSQL.

## 2. Configure the backend database properties

Configure the database properties in the file `application.properties` inside the project `student-backend`.

## 3. Build and run the backend project: 
Go to student-backend directory and run:

`mvn clean install`

`mvn spring-boot:run`

## 4. Run the frontend project: 
Go to student-frontend directory and run:

`ng serve --proxy-config proxy.config.js`



