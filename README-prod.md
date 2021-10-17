# devops-training - Production Instructions

This document shows how to configure and execute the dev-ops project in **production** environment, with **Docker**.

Please refer to [the README.md](README.md) for the main information about the project.


# Build and run of the **production** environment (without Jenkins)

## 1 - Build the project

Run the command in the root directory:

`mvn clean install`


## 2 - Build the docker images and run the containers

Run these commands in the `docker` directory:

`docker-compose -f docker-compose-jenkins.yaml build`

`docker-compose -f docker-compose-jenkins.yaml up -d`

## 3 - Accessing the application

Use the following URLs in your browser to test the applications:

### Production environment

Front: `http://localhost:4202/students`

Back: `http://localhost:8082/student-backend`


