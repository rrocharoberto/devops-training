# devops-training - Jenkins Jobs Instructions

This document show how to configure and execute the dev-ops project using jobs environment, with **Jenkings** and **Docker**.

Please refer to [the README.md](README.md) for the main information about the project.


# Run the CI process using Jenkins

## 1 - Pre-requisites
The following tools are required:
1) Jenkins
2) Docker

All jobs use the same Git repository in Source Code Management:
`https://github.com/rrocharoberto/devops-training.git`

* It means: this git project


## 2 - Creating the jobs

For each project in the table below, it is necessary do create a new Item (job), using the `Free style` type.

For every project, it should be configured the fields SCM and that ones specified in the table. Notice, that some projects has scripts that are defined just below the table.

After creating all projects, the build process can be started executing the `Deploy Database` project. The `Build after` configuration will execute automaticaly all projects that have dependencies of current one.

All projects use the following SCM URL:

`https://github.com/rrocharoberto/devops-training.git`

| Project         | Build after     | Maven goals   | Pom.xml | Maven Properties | Execute shell command |
| --------------- |:---------------:| -------------:| -------:| ----------------:| ----------------:|
| Deploy Database | none            | none          | none    | none             | see below |
| Deploy Back     | Deploy Database | clean package | student-backend/pom.xml  | | see below |
| API Test        | Deploy Back     | verify        | student-api-test/pom.xml | base.server.url=http://localhost:8081/student-backend | none
| Deploy Front    | API Test        | none          | none                     | | see below
| Functional Test | Deploy Front    | verify        | student-functional-test/pom.xml | base.server.url=http://localhost:4201/students | none


These are the scripts to use in field `Execute shell command` mentioned in the table above:

### 2.1 - Deploy Database shell script:

```
docker image build -t db-student:build_test ./docker/postgres
docker stop student-db-test
docker network create student-net-test
docker container run -p 5433:5432 --network student-net --name student-db-test --rm -d db-student:build_test
```


### 2.2 - Deploy Back shell script:

```
cp ./student-backend/target/student-backend-1.0.jar ./docker/backend/
docker image build -t backend-student:build_test ./docker/backend
docker container stop student-backend-test || true
docker container run -p 8081:8080 --network student-net --name student-backend-test --rm -d backend-student:build_test
```

### 2.3 - Deploy Front shell script:

```
cd student-frontend
npm install
VERSION="test" npm run build:prod
cd ..
cp -r ./student-frontend/dist/ ./docker/frontend/
docker image build -t frontend-student:build_test ./docker/frontend/
docker container stop student-frontend-test || true
docker container run -p 4201:4200 --network student-net --name student-frontend-test --rm -d frontend-student:build_test
```

### 2.4 - Deploy Functional test shell script:

```
cd student-api-test
mvn help:active-profiles verify -P prod -Dbase.server.url=http://localhost:8081/student-backend

cd student-functional-test/
mvn help:active-profiles verify -P test -Dbase.server.url=http://localhost:4201/students
mvn help:active-profiles verify -P prod -Dbase.server.url=http://localhost:4201/students

```

<!--
This configuration is used when the Tomcat is used:

In post-build actions, configure the deploy in local Tomcat instance, with the following options:

```
WAR/EAR files: student-backend/target/student-backend-1.0.war
Context path: student-backend
Containers: Tomcat 9.x Remote (with the specific credentials (eg: admin/admin) and Tomcat URL (http://localhost:8080)
```
-->

