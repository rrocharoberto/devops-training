# devops-training
Set of projects used in dev-ops training with Jenkings.

The goal of this repository is to build and run some projects, each one in its own container:
1) Database using PostgreSQL
2) Backend in Spring Boot
3) Frontend in Angular

Some extra projects are used for testing
1) API test in Java unit test.

This environment will be used in a CI project using Jenkins for automation of the build proccess.

# Instructions for building the images

## 1. Manually Building the projects:

### 1.1. Build the BackEnd project:

`cd student-backend`

`mvn clean install`

### 1.2. Build the API-Test project:
`cd student-api-test`

`mvn clean install -Dmaven.test.skip=true`

TODO: check empty Jar file.


### 1.3 Build the FrontEnd project:

`cd student-frontend`

`npm install`

`npm run build`


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

Copy the project files of the Angular application:

`cp -r ../../student-frontend/dist/ .`

Create the image:

`docker image build -t frontend-student .`




# Running the containers

## Running the entire environment, using Docker Compose.

### Optional pre-requisites: stop all containers

`docker stop student-frontend`

`docker stop student-backend`

`docker stop student-db`

## Run the containers

Go to `docker` directory and run:

`docker-compose -f docker-compose-jenkins.yaml up`


## Accessing the application

Use the following URL in your browser:

`http://localhost:4201/students`




# (optional) Running using Docker Separatedely

Instead of using docker compose, you can run the three containers separatedely.

## Create a docker network:

`docker network create student-net`

## 1. Run the database container:
`docker container run -p 5433:5432 --network student-net --name student-db --rm -d db-student`

Optional: check the container logs:

`docker container logs -f student-dbserver`

## 2. Run the backend container:
`docker container run -p 8081:8080 --network student-net --name student-backend --rm -d backend-student`

Optional: check the container logs:

`docker container logs -f student-backend`

## 3. Run the frontend container:
`docker container run -p 4201:4200 --network student-net --name student-frontend --rm -d frontend-student`

Optional: check the container logs:

`docker container logs -f student-frontend`



# Running in the development environment

To run the project in developent environment, execute the following commands:

## 1. Configure the database

Execute the database script contents of file `docker/postgres/script.sql` in PostgreSQL.

## 2. Configure the backend database properties

Configure the database properties in the file `src/resources/application.properties` inside the project `student-backend`.

## 3. Build and run the backend project: 
Go to `student-backend` directory and run:

### For development environment (will generate Spring Boot jar file):
`mvn clean install`

`mvn spring-boot:run`


### For docker environment (will generate war file):
`mvn -Denvironment=release clean install`



## 4. Run the frontend project: 
Go to `student-frontend` directory and run:

`ng build`

`ng serve --proxy-config proxy.config.js`

## 5. Run the API test project

### In development console
Go to `student-api-test` and run the command.

`mvn verify -Dbase.server.url=http://localhost:8080/student-backen`

PS.: the *port* depends on the environment the backend is running (8080 (dev) or 8081 (prod - docker)).


### (Optional) In development IDE
Go to `student-api-test` and run the unit test class `APITest`.




# Run the CI process using Jenkins

## Pre-requisites
The following tools are required:
1) Jenkins
2) Docker
3) Tomcat

All jobs use the same Git repository in Source Code Management:
`https://github.com/rrocharoberto/devops-training.git`

* It means: this git project

## Frontend configuration

In the build process, execute the following commands in shell config:

```
cd student-frontend
npm install
npm run build
docker image build -t frontend-student .
docker stop student-frontend || true
docker rm student-frontend || true
docker container run -p 81:80 --network student-net --name student-frontend --rm -d frontend-student
```

## Backend configuration
In the build process, configure the following Maven options:

`goals: clean package`

`POM: student-backend/pom.xml`

`Properties: environment=release`

In post-build actions, configure the deploy in local Tomcat instance, with the following options:

```
WAR/EAR files: student-backend/target/student-backend-1.0.war
Context path: student-backend
Containers: Tomcat 9.x Remote (with the specific credentials (eg: admin/admin) and Tomcat URL (http://localhost:8080)
```



