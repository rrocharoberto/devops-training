pipeline {
    agent any
    stages {
        stage ('SCM') {
            steps {
                git 'https://github.com/rrocharoberto/devops-training.git'
            }
        }
        stage ('Build backend') {
            steps {
                script {
                    dir('student-backend/') {
                        sh 'mvn clean package -DskipTests=true' 
                    }
                }
            }
        }
        stage ('Unit test backend') {
            steps {
                script {
                    dir('student-backend/') {
                        sh 'mvn test' 
                    }
                }
            }
        }
        // stage ('Sonar Analysis') {
        //     environment {
        //         scannerHome = tool 'Sonar_Scanner'
        //     }
        //     steps {
        //         withSonarQubeEnv('Sonar_Local') {
        //             sh "${scannerHome}/bin/sonar-scanner -e -Dsonar.projectKey=DeployBack -Dsonar.host.url=http://localhost:9000 -Dsonar.login=28b8bc4b5903cb217103e90cddef37e70685df6b -Dsonar.java.binaries=student-backend/target -Dsonar.coverage.exclusions=**/.mvn/**,**/src/test/**,**/model/**,**/Application.java"
        //         }
        //     }
        // }
        // stage ('Quality Gate') {
        //     steps {
        //         sleep 15
        //         timeout(time: 1, unit: 'MINUTES') {
        //             waitForQualityGate abortPipeline: true
        //         }
        //     }
        // }

        stage ('Prepare database') {
            steps {
                sh 'docker image build -t db-student:build_${BUILD_NUMBER} ./docker/postgres'
                sh 'docker stop student-db-test || true'
                sh 'docker network create student-net-test || true'
                sh 'docker container run -p 5433:5432 --network student-net --name student-db-test --rm -d db-student:build_${BUILD_NUMBER}'
            }
        }
        stage ('Deploy backend') {
            steps {
                sh 'cp ./student-backend/target/student-backend-1.0.jar ./docker/backend/'
                sh 'docker image build -t backend-student:build_${BUILD_NUMBER} ./docker/backend'
                sh 'docker container stop student-backend-test || true'
                sh 'docker container run -p 8081:8080 --network student-net --name student-backend-test --rm -d backend-student:build_${BUILD_NUMBER}'
            }
        }
        stage ('API test') {
            steps {
                script {
                    dir('student-api-test/') {
                        sh 'mvn clean verify -P test -Dbase.server.url=http://localhost:8081/student-backend' 
                    }
                }
            }
        }
        stage ('Deploy frontend') {
            steps {
                script {
                    dir('student-frontend/') {
                        sh 'npm install'
                        sh 'VERSION="$BUILD_NUMBER" npm run build:prod'
                    }
                    sh 'cp -r ./student-frontend/dist/ ./docker/frontend/'
                    sh 'docker image build -t frontend-student:build_${BUILD_NUMBER} ./docker/frontend/'
                    sh 'docker container stop student-frontend-test || true'
                    sh 'docker container run -p 4201:4200 --network student-net --name student-frontend-test --rm -d frontend-student:build_${BUILD_NUMBER}'
                }
            }
        }
        stage ('Functional tests') {
            steps {
                script {
                    dir('student-functional-test/') {
                        sh 'mvn help:active-profiles verify -P test -Dbase.server.url=http://localhost:4201/students'
                    }
                }
            }
        }
        stage('Deploy Prod') {
            steps {
                script {
                    dir('docker/') {
                        sh 'docker-compose -f docker-compose-jenkins.yaml build'
                        sh 'docker-compose -f docker-compose-jenkins.yaml up -d'
                    }
                }
            }
        }
        stage ('Health Check') {
            steps {
                sleep(5)
                script {
                    dir('student-api-test/') {
                        sh 'mvn help:active-profiles verify -P prod -Dbase.server.url=http://localhost:8082/student-backend' 
                    }
                }
                script {
                    dir('student-functional-test/') {
                        sh 'mvn help:active-profiles verify -P prod -Dbase.server.url=http://localhost:4202/students'
                    }
                }
            }
        }
    }
    post {
        always {
            junit allowEmptyResults: true, testResults: 'target/surefire-reports/*.xml, api-test/target/surefire-reports/*.xml, functional-test/target/surefire-reports/*.xml, functional-test/target/failsafe-reports/*.xml'
            // archiveArtifacts artifacts: './student-backend/target/student-backend-1.0.jar', onlyIfSuccessful: true
        }
        unsuccessful {
            emailext attachLog: true, body: 'See the attached log below.', subject: 'Student Build $BUILD_NUMBER has failed', to: 'rrocha.roberto+jenkins@gmail.com'
        }
        fixed {
            emailext attachLog: true, body: 'See the attached log below.', subject: 'Student Build is fine!!!', to: 'rrocha.roberto+jenkins@gmail.com'
        }
    }
}
