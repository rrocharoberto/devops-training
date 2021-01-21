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
3) Maven
4) Java 11

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

6) For test environment, access the URLs of the running Student project:

Front: `http://localhost:4201/students`

Back: `http://localhost:8081/student-backend`

7) For test environment, access the URLs of the running Student project:

Front: `http://localhost:4202/students`

Back: `http://localhost:8082/student-backend`


7) (Optional) Check the logs of the pipeline executed.



# Instructions for different environments

Please refer to [this insctuctions](README-jobs.md) about running separated jobs (with **Jenkings** and **Docker**).

Please refer to [this insctuctions](README-prod.md) about Production Environment (with **Docker**).

Please refer to [this insctuctions](README-test.md) about Test Environment (with **Docker**).

Please refer to [this insctuctions](README-dev.md) about Development Environment.


PS.: Let me know if you have some suggestions, improvements and other thoughts.

