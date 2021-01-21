# devops-training - Production Instructions

This document show how to configure and execute the dev-ops project in production environment, with **Docker**.

Please refer to [the README.md](README.md) for the main information about the project.


# Build and run of the **production** environment (without Jenkins)

## 1 - Build the project

Run the command in the root directory:

`mvn clean install`


## 2 - Build the docker images and run the containers

Run these commands in the `docker` directory:

`docker-compose -f docker-compose-jenkins.yaml build`

`docker-compose -f docker-compose-jenkins.yaml up -d`


