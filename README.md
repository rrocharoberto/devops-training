# devops-training
This repository contains artifacts to demonstrate the configuration and running of a dev-ops environment with **Jenkings** and **Docker**.

The goal of this repository is to provide the resources to build and run a basic Student Project, each one in its own container:
1) Database using PostgreSQL
2) Backend in Spring Boot
3) Frontend in Angular

Please refer to [Deployment diagram](docs/DeploymentEnvironment.png) for more information about the architecture of the project.

Please refer to [Jenkins Pipeline diagram](docs/JenkinsPipeline.png) for more information about the Pipeline of the project.


Some extra projects are used for testing:
1) API test in Java (using RestAssured).
2) Functional test in Java (using Selenium).

This environment will be used in a CI project using Jenkins for automation of the build proccess.




# Configure and run the CI process using Jenkins

## 1 - Pre-requisites

The following tools are required:
1) Jenkins
2) Docker

All jobs use only this Git repository in Source Code Management.

## 2 - Configuring the Jeninks pipeline

1) Access your Jenkins URL:

Example: `http://localhost:9090/` (in my case the Jenkins is running in my local machine).

2) Create a `New Item`. Fill the item name and choose `Pipeline` project type.

3) After creating the Jenkins project, in `Advanced Project Options`, select the folowing options:

    3.1) Definition: `Pipeline script from SCM`

    3.2) SCM: `Git`

    3.3) Repository URL: `https://github.com/rrocharoberto/devops-training.git`

    * The default file is already set: `Jenkinsfile`

4) Save the project.

5) In the Pipeline project just created, click in `Build Now` link and wait the end of the process.

6) Access the URLs of the running Student project:

Front: `http://localhost:4201/students`

Back: `http://localhost:8081/student-backend`


7) (Optional) Check the logs of the pipeline executed.


---

# (Optional 1) Running the production environment using Docker

## 1 - Build the project

Run the command in the `root` directory:

`mvn clean install`


## 2 - Build the docker images and run the containers

Run these commands in the `docker` directory:

`docker-compose -f docker-compose-jenkins.yaml build`

`docker-compose -f docker-compose-jenkins.yaml up -d`



---

# (Optional 2) Instructions for building the images

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

` cd student-functional-test/`

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



---

# Running in the development environment

To run the project in developent environment, execute the following commands:

## 1. Configure the database

Execute the database script contents of file `docker/postgres/script.sql` in PostgreSQL.

## 2. Configure the backend database properties

Configure the database properties in the file `src/resources/application.properties` inside the project `student-backend`.

## 3. Build and run the backend project: 
### For development environment ( for dev environment will generate Spring Boot jar file):
Go to `student-backend` directory and run:

`mvn clean install`

`mvn spring-boot:run`


## 4. Run the frontend project: 
Go to `student-frontend` directory and run:

`ng build`

`ng serve --proxy-config proxy.config.js`

## 5. Run the tests

Execute the test classes in the projects  `student-api-test` and `student-functional-test`.




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



