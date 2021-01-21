# devops-training - Test Instructions

This document show how to configure and execute the dev-ops project in test environment, with **Docker**.

Please refer to [the README.md](README.md) for the main information about the project.


# Build and run of the **test** environment (without Jenkins)

## 1. Manually Building the projects

### 1.1 - Setup database docker image and container

Run the following commands in the root directory:

```
docker image build -t db-student:build_test ./docker/postgres

docker stop student-db-test

docker network create student-net-test

docker container run -p 5433:5432 --network student-net --name student-db-test --rm -d db-student:build_test
```

### 1.2 - Setup the Backend

### 1.2.1 - Build the BackEnd project

`cd student-backend`

`mvn clean install`

PS.: Optional: you can generate war file to deploy in a Tomcat instance:

`mvn -P warFile clean install`


### 1.2.2 - Create backend docker image and run the container

Run the following commands in the root directory:

```
cp ./student-backend/target/student-backend-1.0.jar ./docker/backend/

docker image build -t backend-student:build_test ./docker/backend

docker container stop student-backend-test

docker container run -p 8081:8080 --network student-net --name student-backend-test --rm -d backend-student:build_test
```

### 1.3 - Build and run the API-Test project

`cd student-api-test`

`mvn clean verify -P test -Dbase.server.url=http://localhost:8081/student-backend`


### 1.4 - Setup the Frontend

### 1.4.1 - Build the FrontEnd project

`cd student-frontend`

`VERSION="test" npm run build:prod`

### 1.4.2 - Create frontend docker image and run the container

```
cp -r ./student-frontend/dist/ ./docker/frontend/

docker image build -t frontend-student:build_test ./docker/frontend/

docker container stop student-frontend-test

docker container run -p 4201:4200 --network student-net --name student-frontend-test --rm -d frontend-student:build_test
```

### 1.5 - Build and run the Functional-Test project

`cd student-functional-test`

`mvn help:active-profiles verify -P test -Dbase.server.url=http://localhost:4201/students`


### 1.6 - Deploy production environment

`cd docker`

`docker-compose -f docker-compose-jenkins.yaml build`

`docker-compose -f docker-compose-jenkins.yaml up -d`

### 1.7 - Run the (very simple) Health Check

`cd student-api-test`

`mvn help:active-profiles verify -P prod -Dbase.server.url=http://localhost:8082/student-backend`

`cd student-functional-test/`

`mvn help:active-profiles verify -P prod -Dbase.server.url=http://localhost:4202/students`


## 2 - Accessing the application

Use the following URLs in your browser, depending on the environment:

### Test environment

Front: `http://localhost:4201/students`

Back: `http://localhost:8081/student-backend`

### Production environment

Front: `http://localhost:4202/students`

Back: `http://localhost:8082/student-backend`



## Specific tasks about running the containers

### If the containers are already running, stop all containers

`docker stop student-frontend`

`docker stop student-backend`

`docker stop student-db`


### Optional: check the container logs:

`docker container logs -f ${CONTAINER_NAME}`


